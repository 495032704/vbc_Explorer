package com.dx.springlearn.handlers.utils;

import java.util.Random;

public class RandUtil {
   public static String getRandNum() {
	   String randNum = new Random().nextInt()+"";	   
	   if(randNum.length()!=6) {
		   return getRandNum();
	   }
	   return randNum;
   }
}  
