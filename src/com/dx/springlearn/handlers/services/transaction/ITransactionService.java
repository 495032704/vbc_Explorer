package com.dx.springlearn.handlers.services.transaction;

import java.util.List;
import java.util.Map;

import com.dx.springlearn.handlers.paramsModel.ResultModel;

public interface ITransactionService {
	/**
	 * 根据tid,获取交易信息(页面)
	 * @param tid
	 * @throws Exception
	 */
	public void getTxInfo(String tid,ResultModel model)throws Exception;
	
	/**
	 * 根据tid,获取交易信息
	 * @param tid
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTransActionByTid(String tid)throws Exception;
	/**
	 * 获取账号地址
	 * 
	 */
	public Map<String,Object> getAccountAddr(String account)throws Exception;
	
	/**
	 * 获取交易列表
	 * @return
	 * @throws Exception
	 */
	public void getTxList(Integer pageIndex,Integer pageSize,ResultModel model)throws Exception;
	
	/**
	 * 获取实时交易数量
	 * @return
	 * @throws Exception
	 */
	public void getTxCount(ResultModel model)throws Exception;
	
	/**
	 * 获取交易总金额
	 * @return
	 * @throws Exception
	 */
	public void getTxTotal(ResultModel model)throws Exception;
	/**
	 * 获取交易量、区块量曲折线
	 * @throws Exception
	 */
	public void getLineCharts(Long startTime,Long endTime,ResultModel model)throws Exception;
	/**
	 * 获取待确认交易
	 * @throws Exception
	 */
	public void getImmatureList(Integer pageIndex,Integer pageSize,ResultModel model)throws Exception;
	
}
