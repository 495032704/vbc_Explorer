package com.dx.springlearn.handlers.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class CheckBankCardUtil {
	/*
	 * 校验过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	/**
	 * 校验银行卡卡号
	 */
	public static boolean checkBankCard(String bankCard) {
		if (bankCard.length() < 15 || bankCard.length() > 19) {
			return false;
		}
		char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return bankCard.charAt(bankCard.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeBankCard
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
		if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
				|| !nonCheckCodeBankCard.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeBankCard.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	// 根据相应的银行卡进行验证
	public static JSONObject checkCard(String name, String idCard, String bankCard, String phone) {
		OutputStreamWriter out = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			String args = getCheckIdCardUrl(name, idCard, bankCard, phone);
			URL url = new URL(args);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);// 设置是否允许数据写入
			connection.setDoOutput(true);// 设置是否允许参数数据输出
			connection.setConnectTimeout(5000);// 设置链接响应时间
			connection.setReadTimeout(10000);// 设置参数读取时间
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			// 提交请求
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(args);
			out.flush();
			// 读取返回参数
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				StringBuilder append = result.append(temp);
				System.out.println(append);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(result.toString());
		return json;
	}

	private static final String QUERY_PATH = "http://api.id98.cn/api/v2/bankcard";
	private static final String APPKEY = "35a964846b0262002b6d888e8658764f";

	// 定义一个请求参数拼接方法
	public static String getCheckIdCardUrl(String name, String idCard, String bankCard, String phone) {
		return QUERY_PATH + "?appkey=" + APPKEY + "&name=" + name + "&idcardno=" + idCard + "&bankcardno=" + bankCard +
				"&tel=" + phone;
	}

}
