package com.dx.springlearn.handlers.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.dao.UserDao;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao ud;
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		ud.insert(user);
	}
     
}
