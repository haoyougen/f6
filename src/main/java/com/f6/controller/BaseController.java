package com.f6.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.f6.auth.domain.UserVO;
import com.f6.exceptions.AuthenticationException;
import com.f6.exceptions.BadParameterException;
import com.f6.exceptions.BusinessException;
import com.f6.service.CommonService;
import com.f6.utils.DispatherConstant;
import com.f6.utils.F6BusinessUtil;
import com.f6.utils.F6SystemUtils;
import com.f6.utils.F6WebUtil;
import com.f6.utils.PasswordHelper;
import com.f6.utils.SystemConstans;
import com.f6.vo.DBParameter;

public abstract class BaseController {
	private Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	private CommonService commonservice;

	public abstract void authenticate(HttpServletRequest requset, HttpServletResponse response)
			throws AuthenticationException;

	public abstract void dataValidate(HttpServletRequest requset, HttpServletResponse response) throws BadParameterException;

	public abstract void postProcess(HttpServletRequest requset, HttpServletResponse response);

	@RequestMapping(value = "query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map executeQuery(@RequestBody Map paramap, HttpServletRequest requset, HttpServletResponse response)
			throws AuthenticationException, BadParameterException, BusinessException {
		logger.debug("------------------execute----------------------------");
		baseValidate(paramap);
		dataValidate(requset, response);
		authenticate(requset, response);
		Map result = query(paramap, requset, response);
		postProcess(requset, response);
		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, result, "");
	}

	@RequestMapping(value = "mquery", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map executeMQuery(@RequestBody Map paramap, HttpServletRequest requset, HttpServletResponse response)
			throws AuthenticationException, BadParameterException, BusinessException {
		logger.debug("------------------execute----------------------------");
		baseValidate(paramap);
		dataValidate(requset, response);
		authenticate(requset, response);
		Map result = mquery(paramap, requset, response);
		postProcess(requset, response);
		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, result, "");
	}

	@RequestMapping(value = "change", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map executeChange(@RequestBody Map paramap, HttpServletRequest requset, HttpServletResponse response)
			throws AuthenticationException, BadParameterException, BusinessException {
		logger.debug("------------------execute----------------------------");
		baseValidate(paramap);
		dataValidate(requset, response);
		authenticate(requset, response);

		DBParameter dbparam = F6SystemUtils.buildDBParameter(paramap);
		Map result = commonservice.change(dbparam);

		postProcess(requset, response);
		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, result, "");
	}

	private Map query(Map paramap, HttpServletRequest requset, HttpServletResponse reponse) throws BusinessException {

		// handle the pagination query
		DBParameter dbparam = processPagination(paramap);
		Map<String, ? extends Object> result = commonservice.queryMore(dbparam);
		return result;
	}

	private Map mquery(Map paramap, HttpServletRequest request, HttpServletResponse reponse) throws BusinessException {

		// handle the pagination query

		DBParameter dbparam = processPagination(paramap);
		Map<String, ? extends Object> result = F6BusinessUtil.executeComplexLogc(request, dbparam);

		return result;
	}

	private Map mchange(Map paramap, HttpServletRequest request, HttpServletResponse reponse) throws BusinessException {

		DBParameter dbparam = F6SystemUtils.buildDBParameter(paramap);
		Map<String, ? extends Object> result = F6BusinessUtil.executeComplexLogc(request, dbparam);

		return result;
	}

	private DBParameter processPagination(Map paramap) {
		String pageno = (String) paramap.get(SystemConstans.REQUEST_PARAM_PAGENO);
		String limit = (String) paramap.get(SystemConstans.REQUEST_PARAM_PAGELIMIT);

		logger.info("pageno:" + pageno + "   limit:" + limit);
		DBParameter dbparam = F6SystemUtils.buildDBParameter(paramap);

		// setting for pagination

		if (!F6SystemUtils.isStrNull(pageno) && F6SystemUtils.isNum(pageno)) {
			int page = Integer.parseInt(pageno);
			if (page == 0) {
				page = 1;
			}
			dbparam.setPage(page);
			int limitno = 0;
			if (F6SystemUtils.isStrNull(limit) || !F6SystemUtils.isNum(limit)) {
				limitno = SystemConstans.REQUEST_PARAM_PAGELIMIT_DEFAULT;
			} else {
				limitno = Integer.parseInt(limit);
			}
			dbparam.setLimit(limitno);
		}
		return dbparam;
	}

	@RequestMapping(value = DispatherConstant.LOGOUT, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map logout(HttpServletRequest req, HttpServletResponse res) {
		logger.info("*************************logout successful****************************");
		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, "", "logout successful");
	}

	@RequestMapping(value = DispatherConstant.REG, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map reg(@RequestBody Map<String, String> param, RedirectAttributes attrs, HttpServletRequest request)
			throws AuthenticationException, BusinessException {
		String userName = param.get("userName");
		String userPassword = param.get("userPassword");
		String userPassword1 = param.get("userPassword1");
		String userPassword2 = param.get("userPassword2");

		if (F6SystemUtils.isStrNull(userName) || F6SystemUtils.isStrNull(userPassword)) {
			throw new AuthenticationException("用户名密码不能为空");
		}

		if (F6SystemUtils.isStrNull(userPassword1) || F6SystemUtils.isStrNull(userPassword2)
				|| !userPassword1.equals(userPassword2)) {
			throw new AuthenticationException("两次密码输入不一致");
		}

		param.put("userPassword", PasswordHelper.encryptString(userPassword1));

		DBParameter dbparameter = F6SystemUtils.buildDBParameter(param);
		Map dbresult = commonservice.change(dbparameter);
		dbresult.put("userPassword", "");
		dbresult.put("userPassword1", "");
		dbresult.put("userPassword2", "");

		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, dbresult, "reg successful");
	}

	@RequestMapping(value = DispatherConstant.LOGIN, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map login(@RequestBody Map<String, String> param, RedirectAttributes attrs, HttpServletRequest request)
			throws AuthenticationException, BusinessException {
		String username = param.get("username");
		logger.info(username + "------------------Login-----------------------------");
		String password = param.get("userPassword");

		if (F6SystemUtils.isStrNull(username) || F6SystemUtils.isStrNull(password)) {
			return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_NOAUTH, "", SystemConstans.ERROR_USER_PWD);
		}

		UserVO resultvo = null;
		String encryptedpwd = PasswordHelper.encryptString(password);

		Map<String, String> parametermap = new HashMap<String, String>();
		parametermap.put("identificationId", username);
		// "User", "selectByIdentificationID",
		DBParameter dbparam = F6SystemUtils.buildDBParameter(parametermap);
		dbparam.setModule("User");
		dbparam.setAction("selectByIdentificationID");

		Map<String, ?> dbresult = commonservice.queryOne(dbparam);

		// login failed : no user or wrong pwd
		if (dbresult == null || dbresult.size() == 0) {
			return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_NOAUTH, "", SystemConstans.ERROR_NO_USER);
		} else {
			String dbpwd = (String) dbresult.get("userPassword");
			logger.info("pwd:" + encryptedpwd + "--" + dbpwd + "---" + encryptedpwd.equals(dbpwd));
			if (F6SystemUtils.isStrNull(dbpwd) || !encryptedpwd.equals(dbpwd)) {
				return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_NOAUTH, "", SystemConstans.ERROR_USER_PWD);
			}

		}
		resultvo = (UserVO) F6SystemUtils.parseMap2Obj(dbresult, UserVO.class.getName());
		resultvo.setUserPassword("");

		// generate token
		refreshToken(request, username, resultvo, encryptedpwd);
		// update current user in session
		F6WebUtil.setCurrentUser(request, username);

		return F6WebUtil.buildResponseMap(SystemConstans.RESPONSE_LABEL_SUCCESS, resultvo, "");
	}

	private void refreshToken(HttpServletRequest request, String username, UserVO resultvo, String encryptedpwd)
			throws BusinessException {
		String requestIP = request.getRemoteAddr();
		String token = PasswordHelper.generateToken(username, encryptedpwd, requestIP);
		String tokenEncoded = PasswordHelper.encodeStr(username + SystemConstans.SEPERATOR + token);
		resultvo.setToken(tokenEncoded);

		// update token in db
		Map<String, String> tokenparam = new HashMap<String, String>();
		tokenparam.put("identificationId", username);
		tokenparam.put("token", tokenEncoded);
		DBParameter dbparameter = F6SystemUtils.buildDBParameter(tokenparam);
		// /"TokenVO", "updateToken",
		dbparameter.setModule("Token");
		dbparameter.setAction("updateToken");
		commonservice.change(dbparameter);
	}

	private void baseValidate(Map paramap) throws BadParameterException {
		String functionid = (String) paramap.get(SystemConstans.PARAM_FUNCTION_ID);
		logger.info("*******************BaseController=>******functionid****************************" + functionid);
		if (F6SystemUtils.isStrNull(functionid)) {
			throw new BadParameterException("");
		}
	}
}
