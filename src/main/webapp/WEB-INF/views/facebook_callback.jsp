<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Facebook Login</title>
</head>
<body>

  <h3>${name} 계정으로 로그인 되었습니다.</h3>
  <img src="https://graph.facebook.com/${id}/picture?type=large&width=100&height=100">
  <p>EMAIL: ${email}</p>
  <p>ACCESS TOKEN: ${accessToken}</p>

</body>
</html>