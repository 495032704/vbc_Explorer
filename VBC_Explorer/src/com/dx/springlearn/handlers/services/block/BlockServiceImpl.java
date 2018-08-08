package com.dx.springlearn.handlers.services.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dx.springlearn.handlers.enums.RPCMethodEnums;
import com.dx.springlearn.handlers.enums.RedisPublicKeyEnums;
import com.dx.springlearn.handlers.enums.ResultCodeEnums;
import com.dx.springlearn.handlers.paramsModel.BaseModel;
import com.dx.springlearn.handlers.paramsModel.ResultModel;
import com.dx.springlearn.handlers.paramsModel.RpcResultModel;
import com.dx.springlearn.handlers.services.IResponseHandel;
import com.dx.springlearn.handlers.services.transaction.ITransactionService;
import com.dx.springlearn.handlers.utils.GenerateID;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.dx.springlearn.handlers.utils.PropertiesUtils;
import com.dx.springlearn.handlers.utils.http.HttpUtils;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.EXPX;
import com.dx.springlearn.handlers.utils.redis.JedisEnum.NXXX;
import com.dx.springlearn.handlers.utils.redis.JedisUtils;
@Service("blockService")
public class BlockServiceImpl implements IResponseHandel,IBlockService {
	private Logger log=Logger.getLogger(BlockServiceImpl.class);
	@Autowired
	public ITransactionService transactionService;
	@Override
	public void getBlockCharts(ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		Long id=1l;
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_INFO.getName());
		par.setId(id);
		par.setParams(new Object[]{});
		String params=JsonUtils.objectToJson(par);
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
			Map<String,Object> rs=(Map<String, Object>) rpc.getResult();
			//总数
			Long blockTotals=Long.parseLong(rs.get("blocks").toString());
			//连接数
			Long blockConn=Long.parseLong(rs.get("connections").toString());
			//独立区块
			//todo
			Long forkBlock=Long.parseLong(getMempool().size()+"");
			rs=new HashMap<String, Object>();
			rs.put("Totals", blockTotals);
			rs.put("ConnNumber", blockConn);
			rs.put("forkNumber", forkBlock);
			model.setResult(rs);
		}else {
			model.setCode(ResultCodeEnums.RPC_ERROR.getIndex());
			if(rpc!=null) {
			model.setMsg(rpc.getError());
			}
			model.setSuccess(false);
		}
	}
	/**
	 * 获取孤立块
	 */
	private List<Map<String, Object>> getMempool()throws Exception {
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_RAWMEMPOOL.getName());
		par.setId(1L);
		par.setParams(new Object[]{});
		String params=JsonUtils.objectToJson(par);
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		List<Map<String, Object>> pool=(List<Map<String, Object>>) rpc.getResult();
		return pool;
	}
	
	/**
	 * 检查独立区块
	 * @param startHeight 开始的区块高度
	 * @param newHeight 最新的区块高度
	 */
	public void searchForkBlock(Long startHeight ,Long newHeight) {
		
	}
	
	
	@Override
	public void getBlockList(Long height,ResultModel model)throws Exception  {
		// TODO Auto-generated method stub
		if(height==0) {//由最新的区块开始
			height=getNewBlockHeight();
		}
		int count=Integer.parseInt(PropertiesUtils.getProperties("block.list.count"));
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>(count);
		Map<String, Object> block=null;
		for (int i=0;i<count;i++) {
			block=getBlock(height--,true);
			if(block!=null && block.containsKey("hash")) {
				result.add(block);
			}
		}
		model.setResult(result);
		if(result.isEmpty()) {
			model.setCode(ResultCodeEnums.NOTDATA.getIndex());
		}
	}
	/**
	 * 获取单个block 信息
	 * @param height
	 * @param block
	 */
	private Map<String, Object> getBlock(Long height,boolean isCache)throws Exception{
			boolean isExist=JedisUtils.containsKey(RedisPublicKeyEnums.BLOCK_.getName()+height.toString());
			Map<String, Object> block=null;
			if(isExist && isCache) {
				block=JsonUtils.jsonToObject(JedisUtils.getString(RedisPublicKeyEnums.BLOCK_.getName()+height),HashMap.class);
				return block;
			}
			Long id=0l;
			BaseModel par=new BaseModel();
			par.setMethod(RPCMethodEnums.GET_BLOCK_HASH.getName());
			par.setId(id++);
			par.setParams(new Object[]{height});
		   
			String params = JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
			if(rpc!=null && null==rpc.getError()) {//是否成功接收，error==null成功
				String lastHash=rpc.getResult().toString();
				par.setMethod(RPCMethodEnums.GET_BLOCK.getName());
				par.setId(id++);
				par.setParams(new Object[]{lastHash});
				params = JsonUtils.objectToJson(par);
				RpcResultModel rpchash=HttpUtils.curlPoolpost(params);
				block=(Map<String, Object>) rpchash.getResult();
				//set redis
				if(block!=null && block.containsKey("hash")) {
					JedisUtils.setString(RedisPublicKeyEnums.BLOCK_.getName()+height.toString(), JsonUtils.objectToJson(block));
				}
			}
			return block;
		
		
	}
	

	@Override
	public String getBlockByhash(String hash)throws Exception  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNewBlockHeight() throws Exception {
		// TODO Auto-generated method stub
		Boolean isExist=JedisUtils.containsKey(RedisPublicKeyEnums.REFRESH_LAST_BLOCK.getName());
		if(!isExist || JedisUtils.getString(RedisPublicKeyEnums.REFRESH_LAST_BLOCK.getName()).equals(null)){ 
			GenerateID gid=new GenerateID(Integer.valueOf(PropertiesUtils.getProperties("servers.number")));
			Long id=gid.nextId();			
			BaseModel par=new BaseModel();
			par.setMethod(RPCMethodEnums.GET_BLOCK_COUNT.getName());
			par.setId(id);
			par.setParams(new Object[]{});
			String params=JsonUtils.objectToJson(par);
			RpcResultModel rpc=HttpUtils.curlPoolpost(params);
			if(rpc.getError().equals(null)) {//是否成功接收，error==null成功
				Long newHeight=Long.valueOf(rpc.getResult().toString());
				//get hash
				par.setMethod(RPCMethodEnums.GET_BLOCK_HASH.getName());
				par.setParams(new Object[]{newHeight});
				params=JsonUtils.objectToJson(par);
				rpc=HttpUtils.curlPoolpost(params);
				String lastHash=null;
				if(rpc.getError().equals(null)) {//是否成功接收，error==null成功
					Map<String,Object> rs=JsonUtils.jsonToObject(rpc.getResult().toString(), HashMap.class);
					lastHash=rs.get("hash").toString();
				}
				if(newHeight!=0 || !lastHash.equals(null)) {
					//放到缓存中
					JedisUtils.setString(RedisPublicKeyEnums.LAST_BLOCK.getName(), newHeight+":"+lastHash);
					JedisUtils.setString(RedisPublicKeyEnums.REFRESH_LAST_BLOCK.getName(), newHeight.toString(), NXXX.NX, EXPX.EX, Long.valueOf(PropertiesUtils.getProperties("refresh.redis.last_block")));
					return newHeight;
				}
			}
		}		
		return Long.valueOf(JedisUtils.getString(RedisPublicKeyEnums.REFRESH_LAST_BLOCK.getName()));
	}

	@Override
	public void getBlockTransaction(String hash, ResultModel model) throws Exception {
		// TODO Auto-generated method stub
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_BLOCK.getName());
		par.setId(1l);
		par.setParams(new Object[]{hash});
		String params = JsonUtils.objectToJson(par);
		RpcResultModel rpchash=HttpUtils.curlPoolpost(params);
		Map<String,Object> block=(Map<String, Object>) rpchash.getResult();
		List<String> tx=(ArrayList<String>)block.get("tx");
		List<Map<String, Object>> rsList=new ArrayList<Map<String, Object>>(tx.size());
		Map<String, Object> ps;
		for (String tSr : tx) {
			ps=transactionService.getTransActionByTid(tSr);
			if(ps!=null) {
				rsList.add(ps);
			}
		}
		if(rsList.size()>0) {
			model.setResult(rsList);
		}
	}
	@Override
	public void beforeSendhandel(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handelFinish(String jsonstr) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void failHandel() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void errorHandel() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, Object> getBlockByHash(String hash,ResultModel model)throws Exception {
		// TODO Auto-generated method stub
		BaseModel par=new BaseModel();
		par.setMethod(RPCMethodEnums.GET_BLOCK.getName());
		par.setId(1l);
		par.setParams(new Object[]{hash});
		String params = JsonUtils.objectToJson(par);
		//
		RpcResultModel rpc=HttpUtils.curlPoolpost(params);
		Map<String,Object> rs=(Map<String, Object>) rpc.getResult();			
		return rs;
	}

}
