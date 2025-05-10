package com.kc.storage.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements UserModel{
	private String coId;
	private String usrId;
	private String usrNm;
	private String usrEml;
	private String useYnFlg;
	private String usrPwd;
	private long creDt;
	private long updDt;
	
	@Override
	public Stream<RoleModel> getRealmRoleMappingsStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Stream<RoleModel> getClientRoleMappingsStream(ClientModel app) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasRole(RoleModel role) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void grantRole(RoleModel role) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Stream<RoleModel> getRoleMappingsStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteRoleMapping(RoleModel role) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getId() {
		return this.usrId;
	}
	@Override
	public String getUsername() {
		return this.usrNm;
	}
	@Override
	public void setUsername(String username) {
		this.usrNm = username;
	}
	@Override
	public Long getCreatedTimestamp() {
		return this.creDt;
	}
	@Override
	public void setCreatedTimestamp(Long timestamp) {
		this.creDt = timestamp;
	}
	@Override
	public boolean isEnabled() {
		return "Y".equals(useYnFlg);
	}
	@Override
	public void setEnabled(boolean enabled) {
		this.useYnFlg = enabled ? "Y" : "N";
	}
	@Override
	public void setSingleAttribute(String name, String value) {
		// TODO Auto-generated method stub
	}
	@Override
	public void setAttribute(String name, List<String> values) {
		// TODO Auto-generated method stub
	}
	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
	}
	@Override
	public String getFirstAttribute(String name) {
		return null;
	}
	@Override
	public Stream<String> getAttributeStream(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, List<String>> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Stream<String> getRequiredActionsStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addRequiredAction(String action) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeRequiredAction(String action) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getEmail() {
		return this.usrEml;
	}
	@Override
	public void setEmail(String email) {
		this.usrEml = email;
	}
	@Override
	public boolean isEmailVerified() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setEmailVerified(boolean verified) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Stream<GroupModel> getGroupsStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void joinGroup(GroupModel group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void leaveGroup(GroupModel group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isMemberOf(GroupModel group) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getFederationLink() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setFederationLink(String link) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getServiceAccountClientLink() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setServiceAccountClientLink(String clientInternalId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SubjectCredentialManager credentialManager() {
		// TODO Auto-generated method stub
		return null;
	}
}
