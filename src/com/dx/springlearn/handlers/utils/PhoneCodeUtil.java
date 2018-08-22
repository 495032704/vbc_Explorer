package com.dx.springlearn.handlers.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class PhoneCodeUtil {

	/*
	 * //设置验证码有效时间 public String phoneCode(HttpServletRequest req) {
	 * req.getSession().setAttribute("phonecode", phonecode); HttpSession s =
	 * (HttpSession) req.getSession().getAttribute("phonecode");
	 * s.setMaxInactiveInterval(60*10); phoneCodeReal= (String)
	 * req.getSession().getAttribute("phonecode"); return phoneCodeReal; }
	 */

	private static final String QUERY_PATH = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
	// private static final String QUERY_PATH="https://api.miaodiyun.com";
	private static final String ACCOUNT_SID = "f7f8f0e10fff4441a95a21b87b4759ea";
	private static final String AUTH_TOKEN = "99a93966dd7d4189a1ac928b37277675";

	// 根据相应的手机号发送验证码
	public static String test2(String phone) {
		String rod = smsCode();
		String timestamp = getTimestamp();
		String sig = getMD5(ACCOUNT_SID, AUTH_TOKEN, timestamp);
		String tamp = "【睿银科技】尊敬的用户，您的验证码为" + rod + "，如非本人操作请忽略此短信。";//必须和秒滴模板完全一致
		OutputStreamWriter out = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(QUERY_PATH);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);// 设置是否允许数据写入
			connection.setDoOutput(true);// 设置是否允许参数数据输出
			connection.setConnectTimeout(5000);// 设置链接响应时间
			connection.setReadTimeout(10000);// 设置参数读取时间
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			// 提交请求
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			String args = getQueryArgs(ACCOUNT_SID, tamp, phone, timestamp, sig, "JSON");
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
		String respCode = json.getString("respCode");
		String defaultRespCode = "00000";
		if (defaultRespCode.equals(respCode)) {
			return rod;
		} else {
			return defaultRespCode;
		}
	}

	// 定义一个请求参数拼接方法
	public static String getQueryArgs(String accountSid, String smsContent, String to, String timestamp, String sig,
			String respDataType) {
		return "accountSid=" + accountSid + "&smsContent=" + smsContent + "&to=" + to + "&timestamp=" + timestamp
				+ "&sig=" + sig + "&respDataType=" + respDataType;
	}

	// 获取时间戳
	public static String getTimestamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	// sing签名
	public static String getMD5(String sid, String token, String timestamp) {

		StringBuilder result = new StringBuilder();
		String source = sid + token + timestamp;
		// 获取某个类的实例
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 要进行加密的东西
			byte[] bytes = digest.digest(source.getBytes());
			for (byte b : bytes) {
				String hex = Integer.toHexString(b & 0xff);
				if (hex.length() == 1) {
					result.append("0" + hex);
				} else {
					result.append(hex);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	// 产生随机验证码
	public static String smsCode() {
		String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
//        phonecode = getCoookie(req, response, random);
//        System.out.println(phonecode+"3333333333");
//		return phonecode;
		return random;
	}

	public static String getCoookie(HttpServletRequest request, HttpServletResponse response, String random) {
		return random;
		// 存

		/*
		 * Cookie userCookie=new Cookie("random",random); userCookie.setMaxAge(60);
		 * //存活期为:一个月= 30*24*60*60 此为1分钟 userCookie.setPath("/");
		 * response.addCookie(userCookie); //取 String phoneCode = ""; Cookie[] cookies =
		 * request.getCookies();
		 * 
		 * for(Cookie cookie : cookies){ System.out.println("00000000000");
		 * System.out.println("cookiename="+cookie.getName());
		 * System.out.println("cookie="+cookie);
		 * System.out.println("cookievalue="+cookie.getValue());
		 * if(cookie.getName().equals("JSESSIONID")){ phoneCode = cookie.getValue();
		 * System.out.println(phoneCode+"11111111111"); } }
		 * System.out.println(phoneCode+"2222222222"); return phoneCode;
		 */
	}

}
