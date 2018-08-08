package com.dx.springlearn.handlers.controller.tx;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.springlearn.handlers.paramsModel.ResultModel;
import com.dx.springlearn.handlers.services.block.IBlockService;
import com.dx.springlearn.handlers.services.transaction.ITransactionService;
import com.dx.springlearn.handlers.utils.DateUtil;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/tx")
public class TransactionController {
	@Autowired
	public ITransactionService transactionService;
	
	@RequestMapping("/getTxList")
	@ResponseBody
	public String getList(Integer pageIndex,Integer pageSize) {
		ResultModel model=new ResultModel(true);
		try {
			transactionService.getTxList(pageIndex, pageSize, model);
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
	@RequestMapping("/getImmatureList")
	@ResponseBody
	public String getImmatureList(Integer pageIndex,Integer pageSize){
		ResultModel model=new ResultModel(true);
		try {
			transactionService.getImmatureList(pageIndex, pageSize, model);
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
	@RequestMapping("/getTxCount")
	@ResponseBody
	public String getRealCount() {
		ResultModel model=new ResultModel(true);
		try {
			transactionService.getTxCount(model);
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
	@RequestMapping("/getTxLineChart")
	@ResponseBody
	public String getLine(String selType) {
		ResultModel model=new ResultModel(true);
		try {
			Long startTime=0l;
			Long endTime=System.currentTimeMillis();
			Long val=0l;
			switch (selType) {
			case "1H"://1小时
				val=1*60*60*1000L;
				break;
			case "1D"://一天
				val=24*60*60*1000L;
				break;
			case "1W"://一周
				val=7*24*60*60*1000L;
				break;
			case "1M"://一月
				val=DateUtil.getMonthBefore(DateUtil.getCurrentTime().getTime(),1).getTime();
				break;
			case "1Y"://一年
				Date d= DateUtil.getCurrentTime().getTime();
				int y=d.getYear()-1;
				d.setYear(y);
				val=d.getTime();
				break;
			default://ALL
				break;
			}
			transactionService.getLineCharts(startTime, endTime, model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res=null;
		try {
			res=JsonUtils.objectToJson(model);
		} catch (Exception e) {
	 		
	 	}
		return res;
	}
	
	@RequestMapping("/getTxInfo")
	@ResponseBody
	public String getTxInfo(String tid) {
		ResultModel model=new ResultModel(true);
		try {
			transactionService.getTxInfo(tid,model);
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
	@RequestMapping("/getTxImmature")
	public String getTxImmature(){
		ResultModel model = new ResultModel(true); 
         
		return null;
	}
}
