package com.kc.storage.provider;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.ibatis.session.SqlSession;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.AuthenticationFlowException;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;
import org.keycloak.storage.user.UserCountMethodsProvider;

import com.kc.storage.adapter.UserInfoAdapter;
import com.kc.storage.mappers.UserInfoMapper;
import com.kc.storage.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
@AllArgsConstructor
public class CustomerStorageProvider implements UserStorageProvider, UserRegistrationProvider, UserQueryProvider,
		UserLookupProvider, UserCountMethodsProvider, CredentialInputValidator {

	private ComponentModel componentModel;
	private KeycloakSession keycloakSession;
	private SqlSession sqlSession;

	@Override
	public void close() {
		sqlSession.close();
	}

	@Override
	public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult,
			Integer maxResults) {
		UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
		List<UserInfo> user = mapper.searchForUser(params.get(UserModel.SEARCH), firstResult, maxResults);
		if (user != null) {
			return user.stream().map(item -> new UserInfoAdapter(keycloakSession, realm, componentModel, item));
		}
		return null;
	}

	@Override
	public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult,
			Integer maxResults) {
		return null;
	}

	@Override
	public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
		return null;
	}

	@Override
	public UserModel getUserById(RealmModel realm, String id) {
		String username = StorageId.externalId(id);
		UserInfo user = getUserById(username);
		if (user != null) {
			return new UserInfoAdapter(keycloakSession, realm, componentModel, user);
		}
		log.error(String.format("User with account [%s] doesn't found", username));
		return null;
	}

	@Override
	public UserModel getUserByUsername(RealmModel realm, String username) {
		return getUserById(realm, username);
	}

	@Override
	public UserModel getUserByEmail(RealmModel realm, String email) {
		return null;
	}

	@Override
	public UserModel addUser(RealmModel realm, String username) {
		return null;
	}

	@Override
	public boolean removeUser(RealmModel realm, UserModel user) {
		return false;
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		return PasswordCredentialModel.TYPE.endsWith(credentialType);
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return supportsCredentialType(credentialType);
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
		if (!this.supportsCredentialType(credentialInput.getType())) {
			return false;
		}
		String id = user.getUsername();
		String pwd = credentialInput.getChallengeResponse();
		UserInfo account = getUserById(id);
		if (account == null)
			return false;
		return pwd.equals(account.getUsrPwd());
	}

	private UserInfo getUserById(String username) {
		UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
		UserInfo user = mapper.findUserById(username);
		if (user == null) {
			throw new AuthenticationFlowException(AuthenticationFlowError.INVALID_CLIENT_CREDENTIALS);
		}
		return user;
	}
}
