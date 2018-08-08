package com.dx.springlearn.handlers;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import utils.redisUtils;

@Controller
@RequestMapping("/hello")
public class HelloWord {
		
	     @RequestMapping("/hello")
	     public void hello() throws IOException{
	    	redisUtils redis = new redisUtils();
	    	redisUtils.getJedis();
	     }
	     
}

