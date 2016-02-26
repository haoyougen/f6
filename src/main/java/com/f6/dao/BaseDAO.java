package com.f6.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.f6.exceptions.DAOException;
import com.f6.utils.SystemConstans;
import com.f6.vo.DBParameter;

public abstract class BaseDAO {
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	public Map<String, String> queryOne(DBParameter param) throws DAOException {
		Map<String, String> result = null;
		try {
			result = sqlSessionTemplate.selectOne("com.f6.daos." + param.getModule() + "VOMapper." + param.getAction(),
					param.getParameter());
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return result;

	}

	public List<Map<String, ?>> queryMore(DBParameter param) throws DAOException {
		List<Map<String, ?>> result = null;
		try {
			result = sqlSessionTemplate.selectList("com.f6.daos." + param.getModule() + "VOMapper." + param.getAction(),
					param.getParameter(),new RowBounds(2, 3));
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return result;
	}

	public Map change(DBParameter param, String changeAction) throws DAOException {
		Map<String, Object> result = new HashMap();
		Object dbreslut = null;
		try {
			if (changeAction.equals(SystemConstans.CHANGE_ACTION_UPDATE)) {
				dbreslut = sqlSessionTemplate.update("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
						param.getParameter());
			} else if (changeAction.equals(SystemConstans.CHANGE_ACTION_INSERT)) {
				dbreslut = sqlSessionTemplate.insert("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
						param.getParameter());
			} else if (changeAction.equals(SystemConstans.CHANGE_ACTION_DELETE)) {
				dbreslut = sqlSessionTemplate.delete("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
						param.getParameter());
			}

		} catch (Exception e) {
			throw new DAOException(e);
		}
		result.put("dbresult", dbreslut);
		return result;
	}

}
