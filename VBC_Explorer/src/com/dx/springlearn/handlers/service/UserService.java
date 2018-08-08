package com.dx.springlearn.handlers.service;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.entity.User;
@Service
public interface UserService {
   public void add(User user);
}
