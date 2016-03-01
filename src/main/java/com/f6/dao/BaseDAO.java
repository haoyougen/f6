package com.f6.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.f6.exceptions.DAOException;
import com.f6.utils.F6SystemUtils;
import com.f6.utils.SystemConstans;
import com.f6.vo.DBParameter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	public Map<String, ? extends Object> queryMore(DBParameter param) throws DAOException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, ?>> dbresult = null;
		try {
			int page = param.getPage();
			int limit = param.getLimit();
			if (page > 0) {
				PageHelper.startPage(page, limit);
			}
			dbresult = sqlSessionTemplate.selectList("com.f6.daos." + param.getModule() + "VOMapper." + param.getAction(),
					param.getParameter());
			PageInfo pageinfo = new PageInfo(dbresult);

			result.put(SystemConstans.DB_RESULT_KEY_DATA, dbresult);
			Map<String, String> pageInfomap = F6SystemUtils.convertObj2Map(pageinfo);
			result.put(SystemConstans.DB_RESULT_KEY_PAGE, pageInfomap);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return result;
	}

	public Map change(DBParameter param) throws DAOException {
		Map<String, Object> result = new HashMap();
		Object dbreslut = null;
		String changeAction = param.getAction();
		if (F6SystemUtils.isStrNull(changeAction)) {
			throw new DAOException("No Action");
		}
		try {
			if (changeAction.indexOf(SystemConstans.CHANGE_ACTION_UPDATE)>=0) {
				dbreslut = sqlSessionTemplate.update("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
						param.getParameter());
			} else if (changeAction.indexOf(SystemConstans.CHANGE_ACTION_INSERT)>=0) {
				dbreslut = sqlSessionTemplate.insert("com.f6.daos." + param.getModule() + "Mapper." + param.getAction(),
						param.getParameter());
			} else if (changeAction.indexOf(SystemConstans.CHANGE_ACTION_DELETE)>=0) {
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
