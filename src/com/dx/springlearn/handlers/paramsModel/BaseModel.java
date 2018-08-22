package com.dx.springlearn.handlers.paramsModel;

import java.io.Serializable;

public class BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private Object[] params;
	private Long id;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
