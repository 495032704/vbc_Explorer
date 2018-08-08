package com.dx.springlearn.handlers.controller.currency;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.springlearn.handlers.paramsModel.ResultModel;
import com.dx.springlearn.handlers.service.currency.currencyService;
import com.dx.springlearn.handlers.services.block.IBlockService;
import com.dx.springlearn.handlers.utils.JsonUtils;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/currency")
public class CurrencyController {
	@Autowired
	public currencyService currencyService;
	@RequestMapping("/getBalance")
	@ResponseBody
	public String getBalance() {
		ResultModel model=new ResultModel(true);
		try {
			currencyService.currencyCount(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res=null;
		try {
			res=JsonUtils.objectToJson(model);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	@RequestMapping("/getBlocksize")
	@ResponseBody
	public String getBlocksize(){
		ResultModel model=new ResultModel(true);
		try{
		    currencyService.blockSize(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		String res = null;
		try {
			res=JsonUtils.objectToJson(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping("/getBlockTime")
	@ResponseBody
	public String getBlockTime(){
		ResultModel model=new ResultModel(true);
		try{
			currencyService.blockTime(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		String res = null;
		try{
			res = JsonUtils.objectToJson(model);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
}
