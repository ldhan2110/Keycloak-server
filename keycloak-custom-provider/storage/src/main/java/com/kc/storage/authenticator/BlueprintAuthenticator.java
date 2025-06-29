package com.kc.storage.authenticator;

import org.apache.ibatis.session.SqlSession;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.managers.AuthenticationManager;
import com.kc.storage.adapter.UserInfoAdapter;
import com.kc.storage.mappers.UserInfoMapper;
import com.kc.storage.model.UserInfo;
import com.kc.storage.provider.CustomerStorageProviderFactory;
import com.kc.storage.utils.SessionFactory;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class BlueprintAuthenticator implements Authenticator {
	private SqlSession sqlSession = SessionFactory.getSqlSession();

	private ComponentModel getStorageProviderModel(RealmModel realm) {
		// Tìm component model của custom storage provider
		return realm.getComponentsStream()
			.filter(component -> CustomerStorageProviderFactory.STORAGE_ID.equals(component.getProviderId()))
			.findFirst()
			.orElse(null);
	}

	@Override
	public void close() {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}

	@Override
	public void authenticate(AuthenticationFlowContext context) {
		if (context.getUser() != null) {
			context.success();
			return;
		}
		context.challenge(context.form().createForm("login.ftl"));
	}

	@Override
	public void action(AuthenticationFlowContext context) {
		MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
		String username = formData.getFirst(AuthenticationManager.FORM_USERNAME);
		String password = formData.getFirst(CredentialRepresentation.PASSWORD);
		String companyCode = formData.getFirst("company_code");

		if (username == null || username.isEmpty()) {
			failWithError(context, "Vui lòng nhập tên đăng nhập", username);
			return;
		}

		if (password == null || password.isEmpty()) {
			failWithError(context, "Vui lòng nhập mật khẩu", username);
			return;
		}

		if (companyCode == null || companyCode.isEmpty()) {
			failWithError(context, "Vui lòng nhập mã công ty", username);
			return;
		}

		try {
			// Tìm user trong database
			UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
			UserInfo userInfo = mapper.findUserByUsernameAndCompanyCode(username, companyCode);
			
			if (userInfo == null) {
				failWithError(context, "Tên đăng nhập hoặc mật khẩu không đúng", username);
				return;
			}

			// Kiểm tra mật khẩu
			if (!password.equals(userInfo.getUsrPwd())) {
				failWithError(context, "Tên đăng nhập hoặc mật khẩu không đúng", username);
				return;
			}

			// Lấy ComponentModel của storage provider
			ComponentModel storageModel = getStorageProviderModel(context.getRealm());
			if (storageModel == null) {
				failWithError(context, "Chưa cấu hình User Storage Provider", username);
				return;
			}

			// Tạo UserModel từ UserInfo
			String effectiveUsername = companyCode + "::" + username;
	        context.getAuthenticationSession().setAuthNote("effectiveUsername", effectiveUsername);
	        context.getAuthenticationSession().setAuthNote("password", password);
	        UserModel user = context.getSession().users().getUserByUsername(context.getRealm(), effectiveUsername);
			context.getAuthenticationSession().setAuthNote("effectiveUsername", effectiveUsername);
			
			// Đảm bảo user được set vào context trước khi success
			context.setUser(user);
			context.success();
			
		} catch (Exception e) {
			failWithError(context, "Lỗi hệ thống: " + e.getMessage(), username);
		}
	}

	private void failWithError(AuthenticationFlowContext context, String error, String username) {
		Response challenge = context.form()
			.setError(error)
			.setAttribute("username", username)
			.createForm("login.ftl");
		context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challenge);
	}

	@Override
	public boolean requiresUser() {
		return false;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		return true;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
	}
}