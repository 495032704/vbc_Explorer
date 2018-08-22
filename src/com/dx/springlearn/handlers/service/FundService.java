package com.dx.springlearn.handlers.service;

import org.springframework.stereotype.Service;

@Service
public interface FundService {

	// 根据user_id查询支付密码
	public String findPayPass(Integer id);

	// 绑定账户
	public String account(Integer id, String bankCard);

}
