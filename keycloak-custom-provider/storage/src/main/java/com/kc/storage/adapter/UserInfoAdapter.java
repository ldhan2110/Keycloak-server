package com.kc.storage.adapter;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import com.kc.storage.model.UserInfo;

public class UserInfoAdapter extends AbstractUserAdapterFederatedStorage {

	private final UserInfo user;
	private final String keycloakId;

	public UserInfoAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserInfo user) {
		super(session, realm, model);
		this.user = user;
		this.keycloakId = StorageId.keycloakId(model, user.getUsrId());
	}

	@Override
	public String getId() {
		return keycloakId;
	}

	@Override
	public String getUsername() {
		return user.getUsrId();
	}

	@Override
	public void setUsername(String username) {
		user.setUsrId(username);
	}

	@Override
	public String getEmail() {
		return user.getEmail();
	}
}
