package com.kc.storage.adapter;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import com.kc.storage.model.UserInfo;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class UserInfoAdapter extends AbstractUserAdapterFederatedStorage {

	private final UserInfo user;
	private final ComponentModel model;
	private final String keycloakId;

	public UserInfoAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserInfo user) {
		super(session, realm, model);
		this.user = user;
		this.model = model;
		this.keycloakId = StorageId.keycloakId(model, user.getUsername());
		log.info("Created UserInfoAdapter for user: " + user.getUsrId() + " with companyCode: " + user.getCoId());
	}
	
	public UserInfo getUserInfo() {
		return user;
	}
	
	public String getCompanyCode() {
		return user.getCoId();
	}

	@Override
	public String getUsername() {
	    return user.getCoId() + "::" + user.getUsrId(); // e.g., CLA::demo
	}

	@Override
	public String getId() {
	    return StorageId.keycloakId(model, getUsername()); // persistable ID
	}
	
	@Override
	public void setUsername(String username) {
		user.setUsrId(username);
	}

	@Override
	public String getEmail() {
		return user.getUsrEml();
	}

	@Override
	public void setEmail(String email) {
		user.setUsrEml(email);
	}

	@Override
	public String getFirstName() {
		return user.getUsrNm();
	}

	@Override
	public void setFirstName(String firstName) {
		user.setUsrNm(firstName);
	}

	@Override
	public String getLastName() {
		return "";
	}

	@Override
	public void setLastName(String lastName) {
		// Do nothing as we don't store last name
	}

	@Override
	public boolean isEnabled() {
		return "Y".equals(user.getUseYnFlg());
	}
	
	@Override
	public String getFirstAttribute(String name) {
	    if ("company_code".equals(name)) {
	        return user.getCoId();
	    }
	    return super.getFirstAttribute(name);
	}
}