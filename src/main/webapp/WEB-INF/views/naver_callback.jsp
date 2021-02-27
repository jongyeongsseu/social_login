<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Naver Login</title>
  </head>
  <body>
  <!-- 연동 해제 요청 URL 예시: https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&access_token=ACCESS_TOKEN -->

  <h3>${name}님 계정으로 로그인 되었습니다.</h3>
  <p>EMAIL: ${email}</p>
  <p>ACCESS TOKEN: ${accessToken}</p>
  <h1><a href="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=vEUuE2xME49hoGh40XXc&client_secret=nopoJ1sC5z&access_token=${accessToken}&service_provider=NAVER">연동 해제</a></h1>
  
  </body>
</html>