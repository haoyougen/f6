package com.f6.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f6.dao.CommonDAO;
import com.f6.exceptions.BusinessException;
import com.f6.vo.DBParameter;

@Service("commonservice")
public class CommonService {
	@Autowired
	private CommonDAO dao;

	public Map<String, ?> queryOne(DBParameter dbparam) throws BusinessException {
		Map<String, ?> result;
		try {
			result = dao.queryOne(dbparam);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}

	public  Map<String, ? extends Object> queryMore(DBParameter dbparam) throws BusinessException {
		 Map<String, ? extends Object> result;
		try {
			result = dao.queryMore(dbparam);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}

	public Map change(DBParameter dbparam, String changeAction) throws BusinessException {
		Map<String, Object> result;
		try {
			result = dao.change(dbparam, changeAction);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}

}
