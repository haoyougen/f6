package com.f6.auth.service;

import java.util.List;
import java.util.Map;

import com.f6.auth.dao.AuthenDAO;
import com.f6.exceptions.BusinessException;
import com.f6.vo.DBParameter;

public class AuthenService  {
	private AuthenDAO authenDAO;
	
	 
	
	public Map<String, ?> queryOne(DBParameter dbparam) throws BusinessException {
		Map<String, ?> result;
		try {
			result = authenDAO.queryOne(dbparam);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}

	public List<Map<String, ?>> queryMore(DBParameter dbparam) throws BusinessException {
		List<Map<String, ?>> result;
		try {
			result = authenDAO.queryMore(dbparam);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}
	public AuthenDAO getAuthenDAO() {
		return authenDAO;
	}

	public void setAuthenDAO(AuthenDAO authenDAO) {
		this.authenDAO = authenDAO;
	}
	

}