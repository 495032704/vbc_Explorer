package com.dx.springlearn.handlers.utils;

import org.json.JSONObject;

public class JSONObjectToMapUtil {
	
	public static String jsonObjectToMapUtil(JSONObject str) {
		String object1 = (String) str.get("code");
		String object2 = (String) str.get("isok");
		String object3 = (String) str.get("err");
		if(object1=="1" && object2=="1" && object3=="0") {
			return "1";
		}
		return "0";
	}

	public static String jsonObjectToMapUtil2(JSONObject str) {
		String object1 = (String) str.get("code");
		String object2 = (String) str.get("isok");
		String object3 = (String) str.get("desc");
		if(object1=="1" && object2=="1" && object3=="持卡人认证成功") {
			return "1";
		}
		return "0";
	}
	
}