package com.f6.vo;

import java.util.Map;

public class DBParameter {
	private String module;
	private String action;

	private int page;
	private int limit;

	private Map<String, Object> parameter;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

}
