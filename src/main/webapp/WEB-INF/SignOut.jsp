<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<%
    String id = (String) session.getAttribute("loginId");
%>

<form action="SignOut.do" method="post" onsubmit="return confirmDelete()">
    <input type="hidden" name="id" value="${loginUser.id}">
    <label>비밀번호 확인</label>
    <input type="password" name="pw">
    <button type="submit">회원 탈퇴</button>
</form> 
    <script>
    function confirmDelete() {
        return confirm("정말 탈퇴하시겠습니까? \n탈퇴 시 모든 정보가 삭제됩니다!");
    }
</script>
    
</body>
</html>