<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
   <form action="User/add" method="post">
      phone:<input type="text" name="phoneNumber" maxlength="11"><input type="button" value="获取验证码" onclick="HQYZM()"><br>
      password:<input type="password" name="password"><br>
      verifyPassword:<input type="password" name="verifyPassword"><br>
      phoneCode:<input type="text" name="phoneCode"><br>
      inviteCode:<input type="text" name="inviter"><br>
      <input type="submit">
   </form>
</body>
</html>