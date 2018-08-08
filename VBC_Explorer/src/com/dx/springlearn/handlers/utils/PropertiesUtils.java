package com.dx.springlearn.handlers.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static Logger log=Logger.getLogger(PropertiesUtils.class);
	static Properties prop =null;
	static {
		prop = new Properties(); 
		try {
			InputStream stream = PropertiesUtils.class.getClassLoader().getResourceAsStream("resources/config/system.properties");
			prop.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("load File:system.properties error >>"+e.getMessage());
			log.error(e);
		}  
		
	}
	public static String getProperties(String key) {
		String val=null;
		if(prop!=null) {
			val=prop.getProperty(key);
		}	
		return val;
	}
	
}
