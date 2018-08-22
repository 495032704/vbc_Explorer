package com.dx.springlearn.handlers.utils;

import java.util.UUID;

public class RamInviteUtil {
	
	//产生随机验证码
    public static String getInvite(){
    	String uuid = UUID.randomUUID().toString(); 
    	
    	return uuid.replaceAll("-", "");
    }
	
}
