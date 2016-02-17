package com.f6.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	// public static void StatusCascade(StatusObject object) {
	//
	// }
}
