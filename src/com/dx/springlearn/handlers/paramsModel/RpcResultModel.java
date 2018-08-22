package com.dx.springlearn.handlers.paramsModel;

import java.io.Serializable;

public class RpcResultModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5491951581579905493L;
	private Long id;
	private String error;
	private Object result;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
