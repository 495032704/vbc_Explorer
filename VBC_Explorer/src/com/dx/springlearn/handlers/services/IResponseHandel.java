package com.dx.springlearn.handlers.services;

import java.util.Map;

public interface IResponseHandel {
	/**
	 * 發送前處理
	 */
	public void beforeSendhandel(Map<String,Object> params);
	/** 正常处理
	 */
	public void handelFinish(String jsonstr);
	/**
	 * 失败处理
	 */
	public void failHandel();
	/**
	 * error处理
	 */
	public void errorHandel();
}
