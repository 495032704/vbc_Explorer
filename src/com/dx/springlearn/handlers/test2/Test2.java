package com.dx.springlearn.handlers.test2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.springframework.expression.ParseException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dx.springlearn.handlers.controller.RegisterController;
import com.dx.springlearn.handlers.controller.UserController;
import com.dx.springlearn.handlers.dao.FundDao;
import com.dx.springlearn.handlers.dao.UserDao;
import com.dx.springlearn.handlers.daoImpl.FundDaoImpl;
import com.dx.springlearn.handlers.daoImpl.UserDaoImpl;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;
import com.dx.springlearn.handlers.serviceImpl.UserServiceImpl;
import com.dx.springlearn.handlers.utils.CheckBankCardUtil;
import com.dx.springlearn.handlers.utils.CheckIdCardUtil;
import com.dx.springlearn.handlers.utils.JSONObjectToMapUtil;
import com.dx.springlearn.handlers.utils.PhoneCodeUtil;
import com.dx.springlearn.handlers.utils.RamInviteUtil;
import com.dx.springlearn.handlers.utils.RandUtil;

public class Test2 {

	public static void main(String[] args) {
		//UserDao dao = new UserDaoImpl();
		//FundDao fd = new FundDaoImpl();
		//UserController con = new UserController();
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File com=fsv.getHomeDirectory().getAbsoluteFile();    //这便是读取桌面路径的方法了
		System.out.println(com.getPath()); 
		
	}
	
}