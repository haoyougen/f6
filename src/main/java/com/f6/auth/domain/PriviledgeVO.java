package com.f6.auth.domain;

import org.apache.shiro.authz.Permission;

public class PriviledgeVO  {
	private String resCode;

	private String roleCode;

	private String permission;

	private String resType;

	private String resContent;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode == null ? null : resCode.trim();
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode == null ? null : roleCode.trim();
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType == null ? null : resType.trim();
	}

	public String getResContent() {
		return resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent == null ? null : resContent.trim();
	}

	 

}