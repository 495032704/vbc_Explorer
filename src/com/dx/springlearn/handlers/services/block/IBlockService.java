package com.dx.springlearn.handlers.services.block;


import java.util.Map;

import com.dx.springlearn.handlers.paramsModel.ResultModel;


public interface IBlockService {
	/**
	 * get 区块统计数据
	 * @return
	 */
	public void getBlockCharts(ResultModel model)throws Exception ;
	/**
	 * 获取区块列表（由最高高度往下递推）
	 * @return
	 */
	public void getBlockList(Long height,ResultModel model)throws Exception ;
	
	/**
	 * 根据hash 获取区块详细信息
	 * @param hash
	 * @return
	 */
	public String getBlockByhash(String hash)throws Exception ;
	/**
	 * 获取最新区块
	 * @return
	 * @throws Exception
	 */
	public Long getNewBlockHeight()throws Exception;
	
	/**
	 * 获取区块交易数据
	 * @return
	 */
	public void getBlockTransaction(String hash,ResultModel model)throws Exception ;
	
	/**
	 * 根据区块高度查询区块信息
	 *
	 */
	public Map<String, Object> getBlockByHash(String hash,ResultModel model)throws Exception;
}
