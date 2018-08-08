package com.dx.springlearn.handlers.controller.ipaddress;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dx.springlearn.handlers.services.ip.IpAddressService;
import com.dx.springlearn.handlers.utils.JsonUtils;
import com.dx.springlearn.handlers.utils.PropertiesUtils;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/ipAddress")
public class IpAddressController {
	private Logger log=Logger.getLogger(IpAddressController.class);
	@Autowired
	public IpAddressService ipAddressService;
	
	@RequestMapping(value="/getPeerArea")
	@ResponseBody
    public String getPeerAreaByIp()throws Exception{
		List list = ipAddressService.ipAddress();	
		Map<String, Integer> ipGroup=new HashMap<String, Integer>(list.size());
			 String cn=null;
			 Integer count=0;
			 String dbfile=PropertiesUtils.getProperties("ip.db");
			 DbSearcher ipdb=new DbSearcher(new DbConfig(),dbfile);
			 DataBlock fdata =null;
			 Map<String, String> addObj=null;
			for (Object object : list) {
				addObj=(Map<String, String>) object;
				cn=addObj.get("addr").split(":")[0];
				if(cn!=null && !cn.equals("")) {
					fdata = ipdb.binarySearch(cn);
					if(fdata!=null) {
						 cn=fdata.getRegion().split("\\|")[0];
						 if(ipGroup.containsKey(cn)) {
							 count=ipGroup.get(cn).intValue()+1;
							 ipGroup.put(cn, count);
						 }else {
							 ipGroup.put(cn, 1);
						 }	
					}
				}
			}
		return JsonUtils.objectToJson(ipGroup);
    }
}
