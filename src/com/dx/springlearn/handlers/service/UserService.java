package com.dx.springlearn.handlers.service;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.entity.User;
@Service
public interface UserService {

	//注册用户
	public void add(User user);
	
	//查询手机号
	public String findPhone(String phone);
	
	//查询邀请码
	public String findInvite(String invite);

	//查询密码
	public String findPassByPhone(String phoneNumber);
	
	//登录验证
	public String findUserIsNull(String phoneNumber, String password);
	
	//查询用户全部信息
	public User findUserinformation(String phoneNumber);

	// 修改手机号
	public String updatePhone(String newPhone, Integer id);
	
	// 修改登录密码
	public String updatePassword(String newPassword, Integer id);
	
	// 设置|修改支付密码
	public String updatePayPass(String newPayPass, Integer id);
	
	// 认证|修改真实身份
	public String updateRealId(String realName, String idCard, Integer id);
	
}
