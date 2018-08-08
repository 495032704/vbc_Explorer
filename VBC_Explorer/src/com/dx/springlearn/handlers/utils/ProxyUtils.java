package com.dx.springlearn.handlers.utils;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jca.context.SpringContextResourceAdapter;

import com.dx.springlearn.handlers.services.IResponseHandel;


public class ProxyUtils {
    public Object invoke(Object proxy, Object method, Object...args)throws Throwable {
    	Class clazz =Class.forName(proxy.toString()); 
	    IResponseHandel mo = (IResponseHandel) clazz.newInstance();
    	Method mt=clazz.getDeclaredMethod(method.toString(), String.class);
    	
    	mt.invoke(mo, args);
    	
        System.out.println("====after====");
        return null;
    }
    public static void main(String[] args) {
		try {
			new ProxyUtils().invoke("com.dx.springlearn.handlers.services.block.BlockServiceImpl", "test", "");
			System.out.println(ProxyUtils.class.getCanonicalName());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
	
}
