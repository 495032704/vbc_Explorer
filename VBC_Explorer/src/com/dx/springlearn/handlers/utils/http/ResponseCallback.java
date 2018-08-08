package com.dx.springlearn.handlers.utils.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dx.springlearn.handlers.services.IResponseHandel;
import com.dx.springlearn.handlers.utils.HashMapUtils;
import com.dx.springlearn.handlers.utils.JsonUtils;

import utils.redisUtils;


public class ResponseCallback implements FutureCallback<HttpResponse> {
	private static Logger log=Logger.getLogger(ResponseCallback.class);
	 private CountDownLatch latch;
	 
	 public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
	@Autowired
	public void completed(final HttpResponse response) {
         latch.countDown();
         System.out.println(" callback thread id is : " + Thread.currentThread().getId());
         try {
             String content = EntityUtils.toString(response.getEntity(), "UTF-8");
             System.out.println(" response content is : " + content);
             Map<String, Object> params=JsonUtils.jsonToObject(content, HashMap.class);
             Long id=Long.parseLong(params.get("id").toString());
             if(HashMapUtils.isContain(id)) {
            	 //get redis  包名.classname+method方法名 格式：id-->value
                 String[] vals=HashMapUtils.getValus(id);
                 String className=vals[1];
                 String method=vals[2];
                Class clazz =Class.forName(className); 
         	    IResponseHandel mo = (IResponseHandel) clazz.newInstance();
             	Method mt=clazz.getDeclaredMethod(method, String.class);
             	Object obj=mt.invoke(mo, content);
             	if(obj!=null) {
             		HashMapUtils.delVal(id);
             	}
             }
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	
	@Autowired
     public void failed(final Exception ex) {
         latch.countDown();
         log.info(" callback thread id is : " + Thread.currentThread().getId());
     }
	@Autowired
     public void cancelled() {
         latch.countDown();
         log.info(" callback thread id is : " + Thread.currentThread().getId());
     }

}
