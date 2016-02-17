package com.f6.vo;

import java.util.Date;

public class PriviledgeVO {
	private String resCode;

	private String roleCode;

	private String permission;

	private String resType;

	private String resContent;

	private String mem;

	private String urCreate;

	private Date dateCreate;

	private String urAlter;

	private Date dateAlter;

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

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem == null ? null : mem.trim();
	}

	public String getUrCreate() {
		return urCreate;
	}

	public void setUrCreate(String urCreate) {
		this.urCreate = urCreate == null ? null : urCreate.trim();
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getUrAlter() {
		return urAlter;
	}

	public void setUrAlter(String urAlter) {
		this.urAlter = urAlter == null ? null : urAlter.trim();
	}

	public Date getDateAlter() {
		return dateAlter;
	}

	public void setDateAlter(Date dateAlter) {
		this.dateAlter = dateAlter;
	}
}