package com.f6.auth.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.f6.exceptions.DAOException;
import com.f6.vo.DBParameter;

public class AuthenDAO  {
 
	protected SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String, String> queryOne(DBParameter param) throws DAOException {
		Map<String, String> result = null;
		try {
			result = sqlSessionTemplate.selectOne("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
					param.getParameter());
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return result;

	}

 
	public List<Map<String, ?>> queryMore(DBParameter param) throws DAOException {
		List<Map<String, ?>> result = null;
		try {
			result = sqlSessionTemplate.selectList("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
					param.getParameter());
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return result;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
