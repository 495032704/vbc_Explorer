package com.dx.springlearn.handlers.utils;

import javax.servlet.http.HttpServletRequest;

import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;
import com.dx.springlearn.handlers.serviceImpl.UserServiceImpl;

public class UpdateUserSessionUtil {
		
	public static void updateSession(HttpServletRequest req) {
		User u = (User)req.getSession().getAttribute("user");
		UserService us = new UserServiceImpl();
		User user = us.findUserinformation(u.getPhoneNumber());
		req.getSession().removeAttribute("user");
		req.getSession().setAttribute("user", user);
	}
	
}
