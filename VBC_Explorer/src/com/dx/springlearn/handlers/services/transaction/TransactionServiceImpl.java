package com.dx.springlearn.handlers.services.transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.enums.RPCMethodEnums;
import com.dx.springlearn.handlers.enums.RedisPublicKeyEnums;
import com.dx.springlearn.handlers.paramsModel.BaseModel;
import com.dx.springlearn.handlers.paramsModel.ResultModel;
import com.dx.springlearn.handlers.paramsModel.RpcResultModel;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.dx.springlearn.handlers.utils.PropertiesUtils;
import com.dx.springlearn.handlers.utils.http.HttpUtils;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.EXPX;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.NXXX;
import com.dx.springlearn.handlers.utils.redis.JedisUtils;

@Service("transactionService")
public class TransactionServiceImpl implements ITransactionService {
	
	@Override
	public Map<String, Object> getTransActionByTid(String tid) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> tmap=null;
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_TRANSACTION_TID.getName());
		par.setId(1l);
		par.setParams(new Object[]{tid});
		String params=JsonUtils.objectToJson(par);
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
			tmap=(Map<String, Object>) rpc.getResult();
			System.out.println(tmap);
		}
		return tmap;
	}
    
	@Override
	public Map<String, Object> getAccountAddr(String account) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> tmap=null;
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_ACCOUNT_ADDR.getName());
		par.setId(1l);
		par.setParams(new Object[]{account});
		String params=JsonUtils.objectToJson(par);
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
			tmap=(Map<String, Object>) rpc.getResult();
		}
		return tmap;
	}

	@Override
	public void getTxList(Integer pageIndex,Integer pageSize,ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result=getList();
		Set<Map<String,Object>> reList=null;		
		if(result!=null && result.size()>0) {
			if(pageSize==null || pageSize==0) {
				pageSize=Integer.valueOf(PropertiesUtils.getProperties("block.list.count"));
			}
			Integer total=0;
			if(result.size()/pageSize==1) {
				total=result.size()/pageSize;
			}else {
				total=(result.size()/pageSize)+1;
			}
			reList=new HashSet<Map<String,Object>>(pageSize);
			int i=result.size()-1;
			if(pageIndex>1) {
				if(result.size()-1 > pageIndex*pageSize) {
					i=result.size()-1-pageSize;
				}
			}
			int c=0;
			for (;i>=0;i--) {
				if(c>=pageSize) {
					break;
				}
				reList.add(result.get(i));
				c++;
			}
			Map<String, Object> obj=new HashMap<String,Object>();
			obj.put("pageSize", pageSize);
			obj.put("pageIndex", pageIndex);
			obj.put("total", total);
			obj.put("data", reList);
			model.setResult(obj);
		}
	}
	public void getImmatureList(Integer pageIndex,Integer pageSize,ResultModel model) throws Exception{
		List<Map<String,Object>> result=getImmature();
		Set<Map<String,Object>> reList = null;
		if(result!=null && result.size()>0){
			if(pageSize==null || pageSize==0) {
				pageSize=Integer.valueOf(PropertiesUtils.getProperties("block.list.count"));
			}
			Integer total=0;
			if(result.size()/pageSize==1) {
				total=result.size()/pageSize;
			}else {
				total=(result.size()/pageSize)+1;
			}
			reList=new HashSet<Map<String,Object>>(pageSize);
			int i=result.size()-1;
			if(pageIndex>1) {
				if(result.size()-1 > pageIndex*pageSize) {
					i=result.size()-1-pageSize;
				}
			}
			int c=0;
			for (;i>=0;i--) {
				if(c>=pageSize) {
					break;
				}
				reList.add(result.get(i));
				c++;
			}
			Map<String, Object> obj=new HashMap<String,Object>();
			obj.put("pageSize", pageSize);
			obj.put("pageIndex", pageIndex);
			obj.put("total", total);
			obj.put("data", reList);
			model.setResult(obj);
		}
	}
	/**
	 * get 所有交易数据
	 * @return
	 * @throws Exception
	 */
	private List<Map<String,Object>> getList()throws Exception{
		List<Map<String, Object>> result=null;
		if(JedisUtils.containsKey(RedisPublicKeyEnums.TX_LIST.getName())) {
			result=JedisUtils.getObject(RedisPublicKeyEnums.TX_LIST.getName());
		}else {
			BaseModel par=new BaseModel();
			par.setMethod(RPCMethodEnums.GET_TX_LISTS.getName());
			par.setId(1l);
			par.setParams(new Object[]{});
			String params=JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
			if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
				result=(List<Map<String, Object>>) rpc.getResult();
				if(result!=null && result.size()>0) {
					JedisUtils.setObject(RedisPublicKeyEnums.TX_LIST.getName(), result, NXXX.NX, EXPX.EX, Long.valueOf(PropertiesUtils.getProperties("refresh.redis.last_block")));
				}
			}
		}
		return result;
	}
	
	/**
	 * @author windows
	 *  获取所有待确认的交易
	 */
	
	private List<Map<String,Object>> getImmature() throws Exception{
		List<Map<String,Object>> result = null;
		if(JedisUtils.containsKey(RedisPublicKeyEnums.RAWMEM_POOL.getName())) {
			result=JedisUtils.getObject(RedisPublicKeyEnums.RAWMEM_POOL.getName());
		}else{
			BaseModel par = new BaseModel();
			par.setMethod(RPCMethodEnums.GET_RAWMEMPOOL.getName());
			par.setId(1l);
			par.setParams(new Object[]{});
			String params = JsonUtils.objectToJson(par);
			RpcResultModel rpc = HttpUtils.curlPoolpost(params);
			if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
				result=(List<Map<String, Object>>) rpc.getResult();
				if(rpc!=null && null == rpc.getError()){
					JedisUtils.setObject(RedisPublicKeyEnums.RAWMEM_POOL.getName(), result, NXXX.NX, EXPX.EX, Long.valueOf(PropertiesUtils.getProperties("refresh.redis.last_block")));
				}
			}
		}
		return result;
	}

	@Override
	public void getTxCount(ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_TX_LISTS.getName());
		par.setId(1l);
		par.setParams(new Object[]{});
		String params=JsonUtils.objectToJson(par);
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
			List<Map<String, Object>> result=(List<Map<String, Object>>) rpc.getResult();
			if(result!=null && result.size()>0) {
				model.setResult(result.size());
			}
		}
	}

	@Override
	public void getTxTotal(ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result=getList();
		
	}

	@Override
	public void getLineCharts(Long startTime,Long endTime,ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result=getList();
		List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : result) {
			Long time=Long.valueOf(map.get("time").toString());
			System.out.println(time);
			if(time>=startTime && time<=endTime) {
				data.add(map);
			}
		}
		if(data.size()>0) {
			model.setResult(data);
		}
	}

	@Override
	public void getTxInfo(String tid,ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> info=getTransActionByTid(tid);
		if (info!=null) {
			List<Map<String,Object>> vint=(List<Map<String, Object>>) info.get("vin");
			System.out.println(vint);
			List<Map<String,Object>> vintList=new ArrayList<Map<String,Object>>(vint.size());
			System.out.println(vintList);
			Map<String,Object> input=null;
			Map<String,Object> inputObj=null;
			for (Map<String, Object> map : vint) {
				if(map.containsKey("txid")) {
					input=getTransActionByTid(map.get("txid").toString());
					if(input!=null) {
						inputObj=new HashMap<String,Object>();
						inputObj.put("txid", input.get("txid"));
						inputObj.put("blockhash", input.get("blockhash"));
						List<Map<String,Object>> in=(List<Map<String, Object>>) input.get("vout");
						Long val=0l;
						for (Map<String, Object> map2 : in) {
							Double d=(Double)map2.get("value");
							val=val+d.longValue();
							Map<String, Object> obj=(Map<String, Object>) map2.get("scriptPubKey");
							if(obj.containsKey("addresses")) {
								inputObj.put("address", obj.get("addresses"));
							}
						}
						inputObj.put("value", val);
						vintList.add(inputObj);
					}
				}
			}
			if(vintList.size()>0) {
				info.put("vin", vintList);
			}else {
				info.remove("vin");
			}
			List<Map<String,Object>> vout=(List<Map<String, Object>>) info.get("vout");
			List<Map<String,Object>> outList=new ArrayList<Map<String,Object>>(vint.size());
			Map<String, Object> out=null;
			for (Map<String, Object> map : vout) {
				if(map.containsKey("value")) {
					out=new HashMap<>();
					out.put("value", map.get("value"));
					Map<String, Object> obj=(Map<String, Object>) map.get("scriptPubKey");
					if(obj.containsKey("addresses")) {
						out.put("address", obj.get("addresses"));
					}
					outList.add(out);	
				}
			}
			if(outList.size()>0) {
				info.put("vout", outList);
			}else {
				info.remove("vout");
			}
			model.setResult(info);
		}
	}

}
