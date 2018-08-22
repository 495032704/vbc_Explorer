package com.dx.springlearn.handlers.serviceImpl;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.dao.UserDao;
import com.dx.springlearn.handlers.daoImpl.UserDaoImpl;
import com.dx.springlearn.handlers.entity.User;
import com.dx.springlearn.handlers.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
    //@Autowired
    private UserDao ud = new UserDaoImpl();
    
	@Override
	public void add(User user) {
		ud.insert(user);
	}
	
	@Override
	public String findPhone(String phone) {
		String status = ud.findPhone(phone);
		return status;
	}
	
	@Override
	public String findInvite(String invite) {
		String status = ud.findInvite(invite);
		return status;
	}

	@Override
	public String findPassByPhone(String phoneNumber) {
		String status = ud.findPassByPhone(phoneNumber);
		return status;
	}

	@Override
	public String findUserIsNull(String phoneNumber, String password) {
		String status = ud.findUserIsNull(phoneNumber, password);
		return status;
	}

	@Override
	public User findUserinformation(String phoneNumber) {
		User user = ud.findUserinformation(phoneNumber);
		return user;
	}

	@Override
	public String updatePhone(String newPhone, Integer id) {
		String status = ud.updatePhone(newPhone, id);
		return status;
	}

	@Override
	public String updatePassword(String newPassword, Integer id) {
		String status = ud.updatePassword(newPassword, id);
		return status;
	}

	@Override
	public String updatePayPass(String newPayPass, Integer id) {
		String status = ud.updatePayPass(newPayPass, id);
		return status;
	}

	@Override
	public String updateRealId(String realName, String idCard, Integer id) {
		String status = ud.updateRealId(realName, idCard, id);
		return status;
	}
     
}
