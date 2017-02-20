package com.hard.utils;

import javax.servlet.http.HttpSession;

public class NameGenerator {
	public static String getName(HttpSession session) {
		String str = "org.apache.catalina.session.StandardSessionFacade@";
		String subStr = session.toString().substring(str.length());
		
		return subStr;
	}
}