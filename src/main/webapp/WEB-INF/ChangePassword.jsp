<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<% if (request.getAttribute("errorMsg") != null) { %>
    <script>
        alert('<%= request.getAttribute("errorMsg") %>');
    </script>
<% } %>

<form action="ChangePassword.do" method="post">
    <input type="hidden" name="id" value="${id}">
    
    <label>새 비밀번호</label>
    <input type="password" name="newPw"><br>
    
    <label>비밀번호 확인</label>
    <input type="password" name="confirmPw"><br>
    
    <button type="submit">비밀번호 변경</button>
</form>
</body>
</html>