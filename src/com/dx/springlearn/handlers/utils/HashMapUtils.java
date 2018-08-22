package com.dx.springlearn.handlers.utils;

import java.util.concurrent.ConcurrentHashMap;

public class HashMapUtils {
	//存储async request ID--》value [value格式：className：method：时间毫秒]
	static ConcurrentHashMap<Long, String> requestList=new ConcurrentHashMap<Long, String>();
	public static void addRequst(Long id,String value) {
		requestList.put(id, value);
		if(requestList.values().size()/16==0) {
			delByTime();
		}
	}
	public static boolean isContain(Long id) {
		return requestList.containsKey(id);
	}
	public static String[] getValus(Long id) {
		String val=requestList.get(id);
		String[] split=val.split(":");
		return split;
	}
	public static String getVal(Long id) {
		String val=requestList.get(id);
		return val;
	}
	public static void setVal(Long id,String val) {
		requestList.put(id, val);
	}
	public static void setVal(Long id,String className,String methodName) {
		StringBuilder valStr=new StringBuilder();
		valStr.append(id);
		valStr.append(":");
		valStr.append(className);
		valStr.append(":");
		valStr.append(methodName);
		valStr.append(":");
		valStr.append(System.currentTimeMillis());
		requestList.put(id, valStr.toString());
	}
	public static void delVal(Long id) {
		requestList.remove(id);
	}
	public static void delByTime() {
		String[] split=null;
		Long current=System.currentTimeMillis();
		for (String val : requestList.values()) {
			 split=val.split(":");
			 if((Long.parseLong(split[3])-current)/120 >=1) {//超过五分钟
				 requestList.remove(Long.parseLong(split[0]));
			 }
		}
	}
	public static void main(String[] args) {
		 ConcurrentHashMap<Long, String> requestList=new ConcurrentHashMap<Long, String>(1024);
		 requestList.put(10000l, "ddddd");
		 System.out.println(requestList.values().size());
	}
}
