package com.dx.springlearn.handlers.paramsModel;

import java.io.Serializable;

public class ResultModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private boolean isSuccess;
	private String msg;
	private Object result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public ResultModel() {
		
	}
	public ResultModel(boolean isSuc) {
		this.isSuccess=isSuc;
		if(isSuc) {
			this.code=1;
			this.msg="success";
		}
	}
}
