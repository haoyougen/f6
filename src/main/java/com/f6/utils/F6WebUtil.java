package com.f6.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f6.auth.domain.UserVO;

public class F6WebUtil {
	private static Logger logger = LoggerFactory.getLogger(F6WebUtil.class);

	// 重定向请求
	public static void redirectRequest(String path, HttpServletResponse response) {
		try {
			response.sendRedirect(path);
		} catch (Exception e) {
			logger.error("重定向请求出错！", e);
			throw new RuntimeException(e);
		}
	}

	// 发送错误代码
	public static void sendError(int code, HttpServletResponse response) {
		try {
			response.sendError(code);
		} catch (Exception e) {
			logger.error("发送错误代码出错！", e);
			throw new RuntimeException(e);
		}
	}

	// 判断是否为 AJAX 请求
	public static boolean isAjax(HttpServletRequest request) {
		String headeraccept = request.getHeader("accept");
		String ajaxrequest = request.getHeader("X-Requested-With");

		return ((!F6SystemUtils.isStrNull(headeraccept) && headeraccept.indexOf("application/json") > -1)
				|| (!F6SystemUtils.isStrNull(ajaxrequest) && ajaxrequest.indexOf("XMLHttpRequest") > -1));
		// return request.getHeader("X-Requested-With") != null;
	}

	public static void sendJson4Error(String errorcode, String messagestr, HttpServletResponse response) {
		Map respons = buildResponseMap(errorcode, "", messagestr);
		String json = JSON.toJSONString(respons);
		response.setHeader("contentType", "text/html; charset=utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Map buildResponseMap(String successcode, Object data, String message) {
		Map response = new LinkedHashMap();
		response.put("responsecode", successcode);
		response.put("data", data);
		response.put("message", message);
		return response;
	}

	public static String getCurrentUser() {
		UserVO user = (UserVO) SecurityUtils.getSubject().getSession().getAttribute(SystemConstans.LOGIN_USER);
		if (user == null) {
			return SystemConstans.NULL_USER;
		}
		return user.getUserCode();

	}

	public static UserVO getCurrentUserObject() {
		UserVO user = (UserVO) SecurityUtils.getSubject().getSession().getAttribute(SystemConstans.LOGIN_USER);

		return user;

	}

	public static void main(String[] args) {

	}

	public static MultipartFile saveFiles(HttpServletRequest request, String filename) {
		// List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		multipartResolver.setDefaultEncoding("utf-8");
		// 判断 request 是否有文件上传,即多部分请求
		MultipartFile file = null;
		boolean isMultipart = multipartResolver.isMultipart(request);
		logger.debug("isMultipart:" + isMultipart);
		if (isMultipart) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				logger.debug("filenames:" + iter.next());
			}
			// 记录上传过程起始时的时间，用来计算上传时间
			int pre = (int) System.currentTimeMillis();
			// 取得上传文件
			// String filename = iter.next();
			logger.debug("========================filename======================" + filename);
			file = multiRequest.getFile("file");
			logger.debug("========================file======================" + file);

			int finaltime = (int) System.currentTimeMillis();
			System.out.println(finaltime - pre);

		}

		// }
		return file;
	}

	public static String getBodyString(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {

			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static void printHeader(ServletRequest request) {
		Enumeration<String> headernames = ((HttpServletRequest) request).getHeaderNames();
		while (headernames.hasMoreElements()) {
			String headname = headernames.nextElement();
			logger.info(headname + "----------" + ((HttpServletRequest) request).getHeader(headname));
		}

	}

	public static boolean isJsonRequest(ServletRequest request) {
		String contentType = ((HttpServletRequest) request).getHeader("content-type");
		if (contentType.indexOf("application/json") >= 0) {
			return true;
		}
		return false;
	}

	public static void writeString(HttpServletResponse response, String str) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getCorpInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String corpCode = "";

		try {
			ServletInputStream inputstream = request.getInputStream();
			List<String> strlist = IOUtils.readLines(inputstream, SystemConstans.ENCODING_UTF8);
			StringBuilder sb = new StringBuilder();
			for (String line : strlist) {
				sb.append(line);
			}

			logger.info("--------------------input parameter---------------------------");
			logger.info(sb.toString());
			// BtmuParamVO paramvo = (BtmuParamVO) JSON.parse(sb.toString());

			JSONObject jsonobject = JSON.parseObject(sb.toString());
			corpCode = (String) jsonobject.getString("corpCode");

			logger.info("--------------------get corpCode ---------------------------" + corpCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpCode;
	}

	public static String getRequestProperty(HttpServletRequest request, String propertyName) {
		// TODO Auto-generated method stub
		logger.info("--------------------get property Name ---------------------------" + propertyName);
		if (F6SystemUtils.isStrNull(propertyName)) {
			return "";
		}
		String propertyValue = "";

		try {
			ServletInputStream inputstream = request.getInputStream();
			List<String> strlist = IOUtils.readLines(inputstream, SystemConstans.ENCODING_UTF8);
			StringBuilder sb = new StringBuilder();
			for (String line : strlist) {
				sb.append(line);
			}

			logger.info("--------------------input parameter---------------------------");
			logger.info(sb.toString());
			// BtmuParamVO paramvo = (BtmuParamVO) JSON.parse(sb.toString());

			JSONObject jsonobject = JSON.parseObject(sb.toString());
			if (jsonobject != null)
				propertyValue = (String) jsonobject.getString(propertyName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("--------------------get property Name ---------------------------" + propertyName);
		return propertyValue;

	}

	public static void buildErrorResponse(HttpServletResponse response, String responsecode, String responsemsg) {
		try {
			response.setHeader("Content-Type", "application/json; charset=utf-8");
			Map responsemap = new LinkedHashMap();
			responsemap.put("responsecode", responsecode);
			responsemap.put("message", responsemsg);
			responsemap.put("data", "");
			String responsestr = JSON.toJSONString(responsemap);
			response.getWriter().print(responsestr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
