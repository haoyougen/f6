package com.f6.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.f6.controller.BaseController;
import com.f6.exceptions.BusinessException;
import com.f6.utils.F6SystemUtils;
import com.f6.utils.F6WebUtil;
import com.f6.utils.SystemConstans;

public class F6SimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(F6SimpleMappingExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) {
		logger.info("1.MySimpleMappingExceptionResolver resolveException =>" + ex);
		Map<String, Object> model = new HashMap<String, Object>();
		String errormsg = ex.getMessage();
		logger.info("Error message =======>" + errormsg);
		// 根据不同错误转向不同页面
		if (F6WebUtil.isAjax(request)) {
			logger.info("MySimpleMappingExceptionResolver => This is a Ajax Request");

			if (errormsg.length() > 50) {
				errormsg = "系统错误";
			}

			if (F6SystemUtils.isStrNull(errormsg)) {
				errormsg = "系统错误";
			}
			String errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			if (ex instanceof BusinessException) {
				errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			} else if (ex instanceof ParameterException) {
				errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			} else if (ex instanceof UnknownSessionException) {
				errormsg = "会话过期";
				errorcode = SystemConstans.RESPONSE_LABEL_NOLOGIN;
			}
			Map map = F6WebUtil.buildResponseMap(errorcode, null, errormsg);
			F6WebUtil.sendJson4Error(errorcode, errormsg, response);
			 return null;
			//return new ModelAndView("error", map);
		} else {
			logger.info("MySimpleMappingExceptionResolver => This is a Normal Request");
			String errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			// Map map = F6WebUtil.buildResponseMap(errorcode, null, "系统错误");
			// F6WebUtil.sendJson4Error(errorcode, "系统错误", response);
			// model.put("ex", ex);
			// if (ex instanceof BusinessException) {
			// return new ModelAndView("error-business", model);
			// } else if (ex instanceof ParameterException) {
			// return new ModelAndView("error-parameter", model);
			// } else {
			// F6WebUtil.buildErrorResponse(response, errorcode, "系统错误");
			Map map = F6WebUtil.buildResponseMap(errorcode, null, errormsg);
			return new ModelAndView("error", map);
			// return null;
			// }
		}
		// return super.resolveException(arg0, arg1, arg2, arg3);
	}
}
