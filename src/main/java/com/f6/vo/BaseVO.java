package com.f6.vo;

import java.io.Serializable;
import java.util.Date;

import com.f6.utils.DateUtils;

public class BaseVO implements Serializable {

	protected String corpCode;
	

	private String mem;

	private String urCreate;

	private Date dateCreate;

	private String urAlter;

	private Date dateAlter;

	public BaseVO() {
		String currentuser = ""; 
		this.urCreate = currentuser;
		this.urAlter = currentuser;
		this.dateCreate = DateUtils.stringToDate(DateUtils.getCurrentDate());
		this.dateAlter = DateUtils.stringToDate(DateUtils.getCurrentDate());

	}
	public BaseVO(String currentuser) {
	 	this.urCreate = currentuser;
		this.urAlter = currentuser;
		this.dateCreate = DateUtils.stringToDate(DateUtils.getCurrentDate());
		this.dateAlter = DateUtils.stringToDate(DateUtils.getCurrentDate());

	}
	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
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
