package com.f6.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.f6.exceptions.BusinessException;
import com.f6.utils.F6SystemUtils;
import com.f6.utils.F6WebUtil;
import com.f6.utils.SystemConstans;

public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception ex) {
		logger.info("MySimpleMappingExceptionResolver resolveException =>" + ex);
		ex.printStackTrace();
		Map<String, Object> model = new HashMap<String, Object>();
		// 根据不同错误转向不同页面
		if (F6WebUtil.isAjax(request)) {
			logger.info("MySimpleMappingExceptionResolver => This is a Ajax Request");
			String errormsg=ex.getMessage();
			if(errormsg.length()>50){
			    errormsg = "系统错误";  
			}
			 
			if(F6SystemUtils.isStrNull(errormsg)){
				errormsg = "系统错误";
			}
			String errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			if (ex instanceof BusinessException) {
				errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			} else if (ex instanceof ParameterException) {
				errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			} else if (ex instanceof AuthenticationException) {
				errormsg = "用户名密码错误";
				errorcode = SystemConstans.RESPONSE_LABEL_NOAUTH;
			} else if (ex instanceof UnknownSessionException) {
				errormsg = "会话过期";
				errorcode = SystemConstans.RESPONSE_LABEL_NOLOGIN;
			} else {
				errormsg = "系统错误";
				errorcode = SystemConstans.RESPONSE_LABEL_ERROR;
			}
			Map map = F6WebUtil.buildResponseMap(errorcode, null, errormsg);
			return new ModelAndView("error", map);
		} else {
			logger.info("MySimpleMappingExceptionResolver => This is a Normal Request");
			model.put("ex", ex);
			if (ex instanceof BusinessException) {
				return new ModelAndView("error-business", model);
			} else if (ex instanceof ParameterException) {
				return new ModelAndView("error-parameter", model);
			} else {
				return new ModelAndView("error", model);
			}
		}
		// return super.resolveException(arg0, arg1, arg2, arg3);
	}
}
