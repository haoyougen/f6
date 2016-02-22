package com.f6.filters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f6.auth.domain.UserVO;
import com.f6.exceptions.BusinessException;
import com.f6.service.CommonService;
import com.f6.utils.F6BusinessUtil;
import com.f6.utils.F6HttpRequestWrapper;
import com.f6.utils.F6SystemUtils;
import com.f6.utils.F6WebUtil;
import com.f6.utils.PasswordHelper;
import com.f6.utils.SystemConstans;
import com.f6.vo.DBParameter;

public class MainFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(MainFilter.class);
	private CommonService authenService;
	private List<String> exclusiveurl;

	/**
	 * Default constructor.
	 */
	public MainFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		logger.info("---------------Welcome to access F6 API--------------------------");
		
		try {
			F6HttpRequestWrapper f6request = new F6HttpRequestWrapper((HttpServletRequest) request);
			String functionid = F6WebUtil.getRequestProperty(f6request, SystemConstans.PARAM_FUNCTION_ID);
			// check function id
			if (F6SystemUtils.isStrNull(functionid)) {
				F6WebUtil.buildErrorResponse((HttpServletResponse) response,SystemConstans.RESPONSE_LABEL_NOAUTH,SystemConstans.ERROR_BAD_REQUEST);
				return;
			}
			//
			String contextpath = ((HttpServletRequest) request).getContextPath();
			String requesturl = ((HttpServletRequest) request).getRequestURI();

			String requestURL = requesturl.substring(contextpath.length());
			logger.info("您正在访问："+requestURL);
			if (!exclusiveurl.contains(requestURL)) {
				boolean isauthorized = accessAuthenticate((HttpServletRequest) request, functionid);
				if (!isauthorized) {
					F6WebUtil.buildErrorResponse((HttpServletResponse) response,SystemConstans.RESPONSE_LABEL_NOAUTH,SystemConstans.ERROR_NOT_AUTH);
					return;
				}
			}else{
				logger.info(requestURL+"已存在白名单");
			}
			logger.info(contextpath + "===" + requestURL);

			chain.doFilter(f6request, response);
		} catch (Exception e) {
			F6WebUtil.buildErrorResponse((HttpServletResponse) response,SystemConstans.RESPONSE_LABEL_ERROR,SystemConstans.SYSTEM_ERROR);
		}
	}

	private boolean accessAuthenticate(HttpServletRequest request, String functionid) throws BusinessException {
		String userid = isTokenValid(request);
		if (!F6SystemUtils.isStrNull(userid)) {
			boolean ispermitted = isAccessPermitted(userid,functionid);
			return ispermitted;
		}

		return false;
	}

	private boolean isAccessPermitted(String identificationCode, String functionid) throws BusinessException {
		Map<String, String> parametermap = new HashMap<String, String>();
		parametermap.put("identificationCode", identificationCode);

		DBParameter dbparam = F6SystemUtils.buildDBParameter("UserRoleVO", "selectRolesByUserID", parametermap);

		List<Map<String, ?>> roleList = authenService.queryMore(dbparam);
		if (roleList == null || roleList.size() == 0) {
			return false;
		}
		Map parametermap4permission = new HashMap();
		parametermap4permission.put("roleList", roleList);

		DBParameter dbparam4permissoin = F6SystemUtils.buildDBParameter("RoleVO", "selectPermissionsByRoles", parametermap4permission);

		List<Map<String, ?>> permissionList = authenService.queryMore(dbparam4permissoin);
		if (permissionList == null || permissionList.size() == 0) {
			return false;
		} else {
			Iterator<Map<String, ?>> it = permissionList.iterator();
			while (it.hasNext()) {
				Map<String, ?> permap = it.next();

				if (permap.containsValue(functionid)) {
					return true;
				}
			}

		}

		return false;
	}

	private String isTokenValid(HttpServletRequest request) throws BusinessException {
		String token = (String) request.getHeader(SystemConstans.PARAM_TOKEN);
		if (F6SystemUtils.isStrNull(token)) {
			return null;
		}
		String decodedToken = PasswordHelper.decodeStr(token);

		String[] decoded = F6BusinessUtil.tokenAnalysis(decodedToken);
		String identificationCode = decoded[0];
		String requstedToken = decoded[1];

		Map<String, String> parametermap = new HashMap<String, String>();
		parametermap.put("identificationCode", identificationCode);

		DBParameter dbparam = F6SystemUtils.buildDBParameter("UserVO", "selectByIdentificationID", parametermap);

		UserVO user = (UserVO) authenService.queryOne(dbparam);
		if (user == null) {
			return null;
		}
		String pwd = user.getUserPassword();
		String ip = request.getRemoteAddr();
		String source = identificationCode + pwd + ip;
		String newToken = PasswordHelper.generateToken(source);
		if (!requstedToken.equals(newToken)) {
			return null;
		}
		return identificationCode;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public CommonService getAuthenService() {
		return authenService;
	}

	public void setAuthenService(CommonService authenService) {
		this.authenService = authenService;
	}

	public List<String> getExclusiveurl() {
		return exclusiveurl;
	}

	public void setExclusiveurl(List<String> exclusiveurl) {
		this.exclusiveurl = exclusiveurl;
	}
}