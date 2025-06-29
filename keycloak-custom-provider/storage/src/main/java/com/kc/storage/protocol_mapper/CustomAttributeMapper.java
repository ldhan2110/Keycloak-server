package com.kc.storage.protocol_mapper;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.IDToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kc.storage.adapter.UserInfoAdapter;
import com.kc.storage.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomAttributeMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();
	public static final String PROVIDER_ID = "oidc-hello-world-mapper";

	static {
//		configProperties.add(new ProviderConfigProperty(LOWER_BOUND, "Lower Bound", "Lower bound of lucky number.", ProviderConfigProperty.STRING_TYPE, 1));
//		configProperties.add(new ProviderConfigProperty(UPPER_BOUND, "Upper Bound", "Upper bound of lucky number.", ProviderConfigProperty.STRING_TYPE, 100));
		OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
		OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomAttributeMapper.class);
	}

	@Override
	public String getDisplayCategory() {
		return "Token mapper";
	}

	@Override
	public String getDisplayType() {
		return "Hello World Mapper";
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getHelpText() {
		return "Adds a hello world text to the claim";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return configProperties;
	}

	@Override
	protected void setClaim(IDToken token, ProtocolMapperModel mappingModel, UserSessionModel userSession, KeycloakSession keycloakSession, ClientSessionContext clientSessionCtx) {
		UserModel user = userSession.getUser();
        if (user instanceof UserInfoAdapter adapter) {
            try {
                UserInfo userInfo = adapter.getUserInfo();
                OIDCAttributeMapperHelper.mapClaim(token, mappingModel, OBJECT_MAPPER.writeValueAsString(userInfo));
            } catch (JsonProcessingException e) {
                log.error("Failed to write Blueprint profile as JSON string", e);
            }
        }
	}
}
