package com.kc.storage.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import com.kc.storage.utils.SessionFactory;


public class CustomerStorageProviderFactory implements UserStorageProviderFactory<CustomerStorageProvider> {
		
	private static final String  STORAGE_ID = "custom_usr_storage";
	
	@Override
	public CustomerStorageProvider create(KeycloakSession session, ComponentModel model) {
		return new CustomerStorageProvider(model, session, SessionFactory.getSqlSession());
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return STORAGE_ID;
	}

}
