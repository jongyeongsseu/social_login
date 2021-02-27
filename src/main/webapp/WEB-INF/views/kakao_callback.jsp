<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kakao Login</title>
</head>
<body>

  <h3>${nickname} 계정으로 로그인 되었습니다.</h3>
  <p>EMAIL: ${account_email}</p>
  <p>ACCESS TOKEN: ${accessToken}</p>
  <h1><a href="https://developers.kakao.com/logout">로그아웃</a></h1>

</body>
</html>