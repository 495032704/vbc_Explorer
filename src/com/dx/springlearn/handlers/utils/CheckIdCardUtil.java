package com.dx.springlearn.handlers.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class CheckIdCardUtil {

	/**
	 * 我国公民的身份证号码特点如下 1.长度18位 2.第1-17号只能为数字 3.第18位只能是数字或者x 4.第7-14位表示特有人的年月日信息
	 * 请实现身份证号码合法性判断的函数，函数返回值： 1.如果身份证合法返回0 2.如果身份证长度不合法返回1 3.如果第1-17位含有非数字的字符返回2
	 * 4.如果第18位不是数字也不是x返回3 5.如果身份证号的出生日期非法返回4
	 * 
	 * @since 0.0.1
	 */
	public static boolean checkIdCard(String id) {
		String str = "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
				+ "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))" + "[0-9]{3}[0-9x]{1}";
		Pattern pattern = Pattern.compile(str);
		return pattern.matcher(id).matches() ? true : false;
	}

	// 根据姓名和身份证进行验证
	public static JSONObject checkCard(String name, String cardno) {
		OutputStreamWriter out = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			String args = getCheckIdCardUrl(name, cardno);
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

	private static final String QUERY_PATH = "http://api.id98.cn/api/idcard";
	private static final String APPKEY = "35a964846b0262002b6d888e8658764f";

	// 定义一个请求参数拼接方法
	public static String getCheckIdCardUrl(String name, String cardno) {
		return QUERY_PATH + "?appkey=" + APPKEY + "&name=" + name + "&cardno=" + cardno;
	}

}
