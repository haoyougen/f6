package com.f6.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f6.vo.DBParameter;

public class F6SystemUtils {
	private static Logger logger = LoggerFactory.getLogger(F6SystemUtils.class);

	public static boolean isStrNull(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNum(String str) {
		boolean isnum = str.matches("\\d+");
		return isnum;
	}

	public static String decode(String s) {
		return StringUtils.newStringUtf8(Base64.decodeBase64(s));
	}

	public static String uRLDecoder(String s) {
		String result = "";
		try {
			result = URLDecoder.decode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String encode(String s) {
		return Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
	}

	public List<Map<String, String>> buildParameterList(List<?> list) {
		List<Map<String, String>> resultlist = null;
		try {
			resultlist = new ArrayList<Map<String, String>>();
			Field[] fields = null;

			for (Object o : list) {
				logger.info("类：" + o.getClass().getName());
				fields = o.getClass().getDeclaredFields();
				Map<String, String> map = new HashMap<String, String>();
				for (Field field : fields) {

					field.setAccessible(true);
					String proName = field.getName();
					Object proValue = field.get(o);
					map.put(proName.toUpperCase(), String.valueOf(proValue));
					logger.info("key：" + proName + "-------value:" + proValue);
				}
				resultlist.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultlist;
	}

	public static void main(String[] args) {
		// UserVO user;
		// Map<String, String> usermap = new HashMap<String, String>();
		// usermap.put("userCode", "okok");
		// usermap.put("userName", "myname");
		// user = (UserVO) parseMap2Obj(usermap, UserVO.class.getName());
		// System.out.println(user.toString());

		System.out.println(isNum("000"));
		System.out.println(Integer.parseInt("000"));

	}

	public static boolean isPhoneNo(String input) {
		Pattern pattern = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	public static boolean isID(String input) {
		Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	public static String buildURL(String url, Map<String, String> param) {
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("?");
		Set<String> keyset = param.keySet();
		Iterator<String> it = keyset.iterator();
		for (; it.hasNext();) {
			String key = it.next();
			String val = param.get(key);
			sb.append(key + "=" + val + "&");
		}
		return sb.toString();
	}

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

	public static Object parseMap2Obj(Map<String, ?> dbresult, String classname) {
		Object obj = null;
		try {

			Class clazz = Class.forName(classname);

			obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldname = field.getName();
				String fieldvalue = (String) dbresult.get(fieldname);
				if (!F6SystemUtils.isStrNull(fieldvalue)) {
					PropertyDescriptor pd;
					pd = new PropertyDescriptor(field.getName(), clazz);
					// 获得set方法
					Method method = pd.getWriteMethod();
					method.invoke(obj, fieldvalue);
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return obj;
	}

	public static DBParameter buildDBParameter(String module, String action, Map param) {
		DBParameter dbparam = new DBParameter();
		dbparam.setModule(module);// "UserVO"
		dbparam.setAction(action);// "selectByPhoneNo"
		dbparam.setParameter(param);
		return dbparam;
	}

	public static Map<String, String> convertObj2Map(Object obj) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Class clazz = obj.getClass();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if("serialVersionUID".equals(fieldName)){
					continue;
				}
				String fieldType = field.getGenericType().toString();
				logger.info("fieldType"+fieldType);
				if (fieldType.equals("class java.lang.String")||fieldType.equals("int")||fieldType.equals("long")||fieldType.equals("boolean")) {
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
					Method readMethod = pd.getReadMethod();
					String fieldValue = String.valueOf(readMethod.invoke(obj));
					map.put(fieldName, fieldValue);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
