package com.f6.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.f6.vo.DBParameter;

public class F6BusinessUtil {

	public static Set<String> changeList2Set(List<String> list) {
		if (list == null) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		for (String str : list) {
			set.add(str);
		}

		return set;

	}

	public static String[] tokenAnalysis(String decodedToken) {
		// TODO Auto-generated method stub
		String[] result = new String[2];
		int mark = decodedToken.indexOf(SystemConstans.SEPERATOR);

		String userid = decodedToken.substring(0, mark);
		String requstedToken = decodedToken.substring(mark);
		result[0] = userid;
		result[1] = requstedToken;
		return result;
	}

	public static Map<String, ? extends Object> executeComplexLogc(HttpServletRequest request, DBParameter dbparam) {
		Map<String, ? extends Object> result = null;
		String module = (String) dbparam.getModule();
		String action = (String) dbparam.getAction();

		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getServletContext());
		String beanname = module + "Service";
		Object obj = appContext.getBean(beanname);
		try {
			Method method = obj.getClass().getDeclaredMethod("action", DBParameter.class);
			result = (Map<String, ? extends Object>) method.invoke(obj, dbparam);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// public static void StatusCascade(StatusObject object) {
	//
	// }
}
