package com.dx.springlearn.handlers.service.currency;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dx.springlearn.handlers.enums.RPCMethodEnums;
import com.dx.springlearn.handlers.enums.RedisPublicKeyEnums;
import com.dx.springlearn.handlers.enums.ResultCodeEnums;
import com.dx.springlearn.handlers.paramsModel.BaseModel;
import com.dx.springlearn.handlers.paramsModel.ResultModel;
import com.dx.springlearn.handlers.paramsModel.RpcResultModel;
import com.dx.springlearn.handlers.utils.GenerateID;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.dx.springlearn.handlers.utils.PropertiesUtils;
import com.dx.springlearn.handlers.utils.http.HttpUtils;
import com.dx.springlearn.handlers.utils.redis.JedisUtils;
@Service(value="currencyService")
public class currencyServiceImpl implements currencyService{
    //获取当前VBC币数量
	@Override
	public void currencyCount(ResultModel model){
		Long id=1l;
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_INFO.getName());
		par.setId(id);
		par.setParams(new Object[]{});
		try {
			String params = JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
			if(rpc!=null && null==rpc.getError()) {
				Map<String,Object> rs=(Map<String, Object>) rpc.getResult();
				String balance = rs.get("balance").toString().trim();
				String blocks = rs.get("blocks").toString().trim();
				String difficulty = rs.get("difficulty").toString().trim();
				rs = new HashMap<String,Object>();
				rs.put("balance", balance);
				rs.put("blocks", blocks);
				rs.put("difficulty", difficulty);
				model.setResult(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
	}
	
	//获取区块平均大小
	@Override
	public void blockSize(ResultModel model) {		
			Long id=1l;		
			BaseModel par = new BaseModel();
			par.setMethod(RPCMethodEnums.GET_MINING_INFO.getName());
			par.setId(id);
			par.setParams(new Object[]{});
			try{
				String params = JsonUtils.objectToJson(par);
				RpcResultModel rpc=HttpUtils.curlPoolpost(params);
				if(rpc!=null && null==rpc.getError()) {
					Map<String,Object> rs=(Map<String, Object>) rpc.getResult();
					String blocks = rs.get("blocks").toString().trim();
					String currentblocksize = rs.get("currentblocksize").toString().trim();
					String a = currentblocksize+"KB";
					String networkhashps = rs.get("networkhashps").toString().trim();
					rs = new HashMap<String,Object>();
					rs.put("AveragerBlocksize", a);
					rs.put("networkhashps", networkhashps);
					model.setResult(rs);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
    //获取区块时间
	@Override
	public void blockTime(ResultModel model) throws Exception {
			Long id = 1l;
			BaseModel par=new BaseModel();
			par.setMethod(RPCMethodEnums.GET_BLOCK_COUNT.getName());
			par.setId(id);
			par.setParams(new Object[]{});
			String params = JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
			if(rpc!=null && null==rpc.getError()){
				//最新区块高度
				Long blocks=Long.valueOf(rpc.getResult().toString());
				par.setMethod(RPCMethodEnums.GET_BLOCK_HASH.getName());
				par.setId(id);
				//最新区块哈希
				par.setParams(new Object[]{blocks});
				String params1 = JsonUtils.objectToJson(par);
				RpcResultModel rpc1=HttpUtils.curlPoolpost(params1);
				if(rpc1!=null && null == rpc1.getError()){
					Object blocksHash = rpc1.getResult();				
					//最新区块时间
					par.setMethod(RPCMethodEnums.GET_BLOCK.getName());
					par.setId(id);
					par.setParams(new Object[]{blocksHash});
					String params2 = JsonUtils.objectToJson(par);
					RpcResultModel rpc2 = HttpUtils.curlPoolpost(params2);
					if(rpc2!=null && null==rpc2.getError()){
						Map<String,Object> rs2=(Map<String, Object>) rpc2.getResult();
						String time = rs2.get("time").toString().trim();
						int a =Integer.parseInt(time);
						int b = a-1513440015;	
						double BlocksTime =(b/blocks);
						 HashMap<String,Object> map = new HashMap<String,Object>();
						map.put("BlocksTime", BlocksTime);
						model.setResult(map);
					}
				}
}
}
}