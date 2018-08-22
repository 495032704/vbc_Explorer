<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  			手机号码：${sessionScope.user.phoneNumber }    <a style="color: blue">修改<a/><br/>
  			登录密码：******         <a style="color: blue" href="aaas">修改</a><br/>
  			支付密码：******       <a style="color: blue">设置</a><br/>
  			银行卡号：       <a style="color: blue">绑定</a><br/>
  			实名认证：       <a style="color: blue">认证</a><br/>
</body>
</html>