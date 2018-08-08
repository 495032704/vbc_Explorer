package com.dx.springlearn.handlers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;

@Controller
@RequestMapping("/User")
public class UserController {
	@Autowired
	private UserService us;
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(User user) {
		System.out.println(user);
		us.add(user);
		return "添加成功";
	}
}