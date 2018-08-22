package com.dx.springlearn.handlers.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dx.springlearn.handlers.entity.CallBack;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;
import com.dx.springlearn.handlers.serviceImpl.UserServiceImpl;
import com.dx.springlearn.handlers.utils.BooleanUtils;
import com.dx.springlearn.handlers.utils.MD5Util;

@Controller
@RequestMapping("/User")
public class LoginController {
	
	//@Autowired
	private UserService us = new UserServiceImpl();
	
	private CallBack callBack = new CallBack("000");
	
	//private String callBack = "000";
	
	@RequestMapping("/login")
	public String login(String phoneNumber, String password, HttpServletRequest req) {
		//String MD5Pass = MD5Util.MD5(password);
		if(BooleanUtils.isBlank(phoneNumber)) {
			callBack.setCallBack("003");
			return callBack.getCallBack();
		}
		if(BooleanUtils.isBlank(password)) {
			callBack.setCallBack("009");
			return callBack.getCallBack();
		}
		String status = us.findUserIsNull(phoneNumber, password);
		if(""==status || "0"==status) {
			callBack.setCallBack("019");
			return callBack.getCallBack();
		}
		status = "0";
		User user = us.findUserinformation(phoneNumber);
		HttpSession session = req.getSession();
		if(user==null) {
			callBack.setCallBack("003");
			return callBack.getCallBack();
		}
		session.setAttribute("user", user);
		callBack.setCallBack("020");
		return callBack.getCallBack();
	}
	
	@RequestMapping("/logout")
	public String loginout(HttpServletRequest req) {
		req.getSession().removeAttribute("user");
		//req.getSession().invalidate();
		callBack.setCallBack("021");
		return callBack.getCallBack();
	}
	
}
