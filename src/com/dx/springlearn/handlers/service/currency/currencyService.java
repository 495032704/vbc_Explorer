package com.dx.springlearn.handlers.service.currency;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.paramsModel.ResultModel;
public interface currencyService {
	
   public void currencyCount(ResultModel model);
   
   public void blockSize(ResultModel model);
   
   public void blockTime(ResultModel model)throws Exception;

}
