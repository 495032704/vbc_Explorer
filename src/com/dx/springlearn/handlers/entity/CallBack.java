package com.dx.springlearn.handlers.entity;

import java.io.Serializable;

public class CallBack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String callBack;

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	@Override
	public String toString() {
		return "CallBack [callBack=" + callBack + "]";
	}

	public CallBack(String callBack) {
		super();
		this.callBack = callBack;
	}

	public CallBack() {
		super();
		// TODO Auto-generated constructor stub
	}

}
