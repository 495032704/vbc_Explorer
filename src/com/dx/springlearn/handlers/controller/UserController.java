package com.dx.springlearn.handlers.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.springlearn.handlers.entity.CallBack;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.FundService;
import com.dx.springlearn.handlers.service.UserService;
import com.dx.springlearn.handlers.serviceImpl.FundServiceImpl;
import com.dx.springlearn.handlers.serviceImpl.UserServiceImpl;
import com.dx.springlearn.handlers.utils.BooleanUtils;
import com.dx.springlearn.handlers.utils.CheckBankCardUtil;
import com.dx.springlearn.handlers.utils.CheckIdCardUtil;
import com.dx.springlearn.handlers.utils.JSONObjectToMapUtil;
import com.dx.springlearn.handlers.utils.MD5Util;
import com.dx.springlearn.handlers.utils.UpdateUserSessionUtil;

@Controller
@RequestMapping("/User")
public class UserController {
	
	//private String callBack = "000";
	
	private CallBack callBack = new CallBack("000");
	
	private UserService us = new UserServiceImpl();
	
	private FundService fs = new FundServiceImpl();
	
	private RegisterController rs = new RegisterController();
	
	// 判断手机号是否可以修改
	@RequestMapping("/checkPhoneUpdate")
	@ResponseBody
	public String checkPhoneUpdate(String phoneNumber, HttpServletRequest req) {
		if(BooleanUtils.isBlank(phoneNumber)) {
			callBack.setCallBack("003");
			return callBack.getCallBack();
		}
		String r2 = rs.findPhone(phoneNumber, req);
		if(r2=="002") {
			callBack.setCallBack("002");
			return callBack.getCallBack();
		}
		callBack.setCallBack("016");
		return callBack.getCallBack();
	}
	
	/*//修改手机号
	@RequestMapping("/updatePhoneNumber")
	@ResponseBody
	public String updatePhoneNumber(String phoneNumber, HttpServletRequest req) {
		User user = (User)req.getSession().getAttribute("user");
		String s = us.updatePhone(phoneNumber, user.getUserId());
		if(s == "0") {
			callBack.setCallBack("031");
			return callBack.getCallBack();
		}
		callBack.setCallBack("032");
			return callBack.getCallBack();
	}*/
	
	// 修改登录密码
	@RequestMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(String oldPassword, String newPassword, String verifyPassword, HttpServletRequest req) {
		User user = (User)req.getSession().getAttribute("user");
		String MD5Pass = MD5Util.MD5(oldPassword);
		if(BooleanUtils.isBlank(newPassword) || BooleanUtils.isBlank(verifyPassword) || BooleanUtils.isBlank(oldPassword)) {
			callBack.setCallBack("022");
			return callBack.getCallBack();
		}
		if (!verifyPassword.equals(newPassword) || !MD5Pass.equals(user.getPassword())) {
			callBack.setCallBack("023");
			return callBack.getCallBack();
		}
		newPassword = MD5Util.MD5(newPassword);
		us.updatePassword(newPassword, 1);
		UpdateUserSessionUtil.updateSession(req);
		callBack.setCallBack("024");
		return callBack.getCallBack();
	}
	
	// 设置|修改支付密码
	@RequestMapping("/updatePayPass")
	@ResponseBody
	public String updatePayPass(HttpServletRequest req, String oldPayPass, String newPayPass, String verifyPayPass) {
		User user = (User)req.getSession().getAttribute("user");
		String payPass = fs.findPayPass(user.getUserId());
		String MD5Pass = MD5Util.MD5(oldPayPass);
		if(BooleanUtils.isBlank(oldPayPass) || BooleanUtils.isBlank(verifyPayPass) || BooleanUtils.isBlank(newPayPass)) {
			callBack.setCallBack("022");
			return callBack.getCallBack();
		}
		if (!verifyPayPass.equals(newPayPass) || !MD5Pass.equals(payPass)) {
			callBack.setCallBack("023");
			return callBack.getCallBack();
		}
		newPayPass = MD5Util.MD5(newPayPass);
		us.updatePayPass(newPayPass, user.getUserId());
		callBack.setCallBack("025");
		return callBack.getCallBack();
	}
	
	// 绑定|修改银行卡
	@ResponseBody
	@RequestMapping("/updateBankCard")
	public String updateBankCard(String name, String idCard, String bankCard, String phone, HttpServletRequest req) {
		User user = (User)req.getSession().getAttribute("user");
		boolean isOK = CheckBankCardUtil.checkBankCard("bankCard");
		if(!isOK) {
			callBack.setCallBack("026");
			return callBack.getCallBack();
		}
		JSONObject queryArgs = CheckBankCardUtil.checkCard(name, idCard, bankCard, phone);
		String s = JSONObjectToMapUtil.jsonObjectToMapUtil2(queryArgs);
		if(s=="1") {
			fs.account(user.getUserId(), bankCard);
			callBack.setCallBack("027");
			return callBack.getCallBack();
		}
		callBack.setCallBack("026");
		return callBack.getCallBack();
	}
	
	// 认证|修改真实身份
	@ResponseBody
	@RequestMapping("/updateRealId")
	public String updateRealId(String realName, String idCard, HttpServletRequest req) {
		User user = (User)req.getSession().getAttribute("user");
		boolean isOK = CheckIdCardUtil.checkIdCard(idCard);
		if(!isOK) {
			callBack.setCallBack("028");
			return callBack.getCallBack();
		}
		JSONObject queryArgs = CheckIdCardUtil.checkCard("realName", "idCard");
		String s = JSONObjectToMapUtil.jsonObjectToMapUtil(queryArgs);
		if(s=="1") {
			us.updateRealId(realName, idCard, user.getUserId());
			callBack.setCallBack("029");
			return callBack.getCallBack();
		}
		callBack.setCallBack("030");
		return callBack.getCallBack();
	}
	
}
