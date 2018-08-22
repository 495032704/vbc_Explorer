package com.dx.springlearn.handlers.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.springlearn.handlers.entity.CallBack;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;
import com.dx.springlearn.handlers.serviceImpl.UserServiceImpl;
import com.dx.springlearn.handlers.utils.BooleanUtils;
import com.dx.springlearn.handlers.utils.MD5Util;
import com.dx.springlearn.handlers.utils.PhoneCodeUtil;
import com.dx.springlearn.handlers.utils.RamInviteUtil;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/User")
@ResponseBody
public class RegisterController {

	// @Autowired
	private UserService us = new UserServiceImpl();
	
	// @Autowired
	private HttpServletRequest requset;

	private String status = "0";

	private String phoneYZM = "";
	
	private CallBack callBack = new CallBack("000");
	
	//private String callBack = "000";

	// 添加用户
	@RequestMapping("/add")
	public String add(User user, String verifyPassword, String phoneCode) {

		//验证参数是否为空
		if (BooleanUtils.isBlank(user.getPhoneNumber())) {
			callBack.setCallBack("003");
			return callBack.getCallBack();
		} else if (BooleanUtils.isBlank(user.getPassword())) {
			callBack.setCallBack("009");
			return callBack.getCallBack();
		} else if (BooleanUtils.isBlank(verifyPassword)) {
			callBack.setCallBack("010");
			return callBack.getCallBack();
		} else if (BooleanUtils.isBlank(user.getInviter())) {
			callBack.setCallBack("013");
			return callBack.getCallBack();
		} else if (BooleanUtils.isBlank(phoneCode)) {
			callBack.setCallBack("006");
			return callBack.getCallBack();
		}

		//验证手机验证码
		String s = registerTest2(phoneCode, requset);
		if (s == "005") {
			callBack.setCallBack("005");
			return callBack.getCallBack();
		}

		//验证两次密码是否一致
		if (BooleanUtils.isBlank(verifyPassword) || !verifyPassword.equals(user.getPassword())) {
			callBack.setCallBack("008");
			return callBack.getCallBack();
		}

		// 邀请人判断
		String findInvite = findInvite(user.getInviter());
		if (BooleanUtils.isBlank(findInvite)) {
			callBack.setCallBack("012");
			return callBack.getCallBack();
		}

		// 密码加密
		String MD5pass = MD5Util.MD5(user.getPassword());
		user.setPassword(MD5pass);
		
		// 获取系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		user.setRegisterTime(time);
		
		// 生成邀请码
		String randNum = RamInviteUtil.getInvite();
		user.setInviteCode(randNum);

		//注册
		us.add(user);
		callBack.setCallBack("018");
		return callBack.getCallBack();
	}

	// 手机验证码校验
	@RequestMapping("/checkPhoneCode")
	public String registerTest2(String phoneCode, HttpServletRequest req) {
		String phoneCodeByServer = (String) req.getSession().getAttribute("phoneCode");
		if(phoneCodeByServer=="") {
			callBack.setCallBack("022");
			return callBack.getCallBack();
		}
		if (phoneCode == "" || phoneCode == null || !phoneCode.equals(phoneCodeByServer)) {
			callBack.setCallBack("005");
			return callBack.getCallBack();
		}
		callBack.setCallBack("004");
		return callBack.getCallBack();
	}

	// 验证手机号是否存在
	@RequestMapping("/phoneCode")
	@ResponseBody
	public String findPhone(String phoneNumber, HttpServletRequest req) {
		System.out.println("进来了---------"+phoneNumber);
		status = us.findPhone(phoneNumber);
		if ("1" == status) {
			callBack.setCallBack("002");
			return callBack.getCallBack();
		}
		phoneYZM = PhoneCodeUtil.test2(phoneNumber);
		HttpSession session = req.getSession();
		session.setAttribute("phoneCode", phoneYZM);
		//TimerTask实现5分钟后从session中删除checkCode
		final Timer timer=new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		        session.removeAttribute("phoneCode");
		        System.out.println("checkCode删除成功");
		        timer.cancel();
		    }
		},5*60*1000);
		callBack.setCallBack("016");
		return callBack.getCallBack();
	}

	// 验证邀请人是否存在
	// @RequestMapping("/add")
	@ResponseBody
	public String findInvite(String inviteCode) {
		status = us.findInvite(inviteCode);
		if ("1" == status) {
			return "1";
		}
		return "0";
	}

}