package com.dx.springlearn.handlers.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface FundDao {
	
	//根据user_id查询支付密码
	public String findPayPass(Integer id);
	
	//绑定账户
	public String account(Integer id, String bankCard);

}
