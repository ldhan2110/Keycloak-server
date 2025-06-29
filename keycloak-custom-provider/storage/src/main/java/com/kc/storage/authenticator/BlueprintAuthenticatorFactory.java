package com.kc.storage.authenticator;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class BlueprintAuthenticatorFactory implements AuthenticatorFactory {
	public static final String ID = "company-code-authenticator";
	private static final String PROVIDER_ID = "company-code-form";

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public Authenticator create(KeycloakSession session) {
		return new BlueprintAuthenticator();
	}

	@Override
	public String getDisplayType() {
		return "Company Code Authentication";
	}

	@Override
	public String getReferenceCategory() {
		return "company-code";
	}

	@Override
	public boolean isConfigurable() {
		return false;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return new Requirement[] { Requirement.REQUIRED };
	}

	@Override
	public String getHelpText() {
		return "Validates the username, password and company code of the user.";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return null;
	}

	@Override
	public boolean isUserSetupAllowed() {
		return false;
	}

	@Override
	public void init(Scope config) {
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
	}

	@Override
	public void close() {
	}
}
