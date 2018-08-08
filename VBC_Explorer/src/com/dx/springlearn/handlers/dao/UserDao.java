package com.dx.springlearn.handlers.dao;

import org.springframework.stereotype.Repository;

import com.dx.springlearn.handlers.entity.User;
@Repository
public interface UserDao {
   //注册用户
	public void insert(User user);
}
