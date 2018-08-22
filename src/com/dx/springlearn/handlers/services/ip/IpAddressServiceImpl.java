package com.dx.springlearn.handlers.services.ip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Service(value="IpAddressService")
public class IpAddressServiceImpl implements IpAddressService{

	@Override
	public List ipAddress()throws Exception{
		ArrayList list=null;
		if(JedisUtils.containsKey(RedisPublicKeyEnums.PEER_LIST.getName())) {
			list=JedisUtils.getObject(RedisPublicKeyEnums.PEER_LIST.getName());
		}else {
			Long id=1l;
			BaseModel par=new BaseModel();
			par.setMethod(RPCMethodEnums.GET_PEER_INFO.getName());
			par.setId(id);
			par.setParams(new Object[]{});
			String params = JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
				if(rpc!=null && null==rpc.getError()) {
					list=(ArrayList) rpc.getResult();
					if(list!=null && list.size()>0) {
						//在缓存中，存10分钟
						JedisUtils.setObject(RedisPublicKeyEnums.PEER_LIST.getName(), list, NXXX.NX, EXPX.EX, Long.parseLong(PropertiesUtils.getProperties("refresh.redis.peer_ip")));
					}
				}
					
		}
		return list;
		
	}
}