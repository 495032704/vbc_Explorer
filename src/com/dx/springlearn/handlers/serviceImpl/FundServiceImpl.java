package com.dx.springlearn.handlers.serviceImpl;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.dao.FundDao;
import com.dx.springlearn.handlers.daoImpl.FundDaoImpl;
import com.dx.springlearn.handlers.service.FundService;

@Service
public class FundServiceImpl implements FundService {
	
	private FundDao dao = new FundDaoImpl();

	@Override
	public String findPayPass(Integer id) {
		String payPass = dao.findPayPass(id);
		return payPass;
	}

	@Override
	public String account(Integer id, String bankCard) {
		String status = dao.account(id, bankCard);
		return status;
	}

}
