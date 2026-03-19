<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>자녀 이메일 인증</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/RequestConnection.css">
  <script src="${pageContext.request.contextPath}/js/RequestConnection.js"></script>

  <!-- ✅ 서버에서 전달된 메시지 알림창으로 띄우기 -->
  <c:if test="${not empty errorMsg}">
    <script>
      alert('${errorMsg}');
    </script>
  </c:if>

  <c:if test="${not empty successMsg}">
    <script>
      alert('${successMsg}');
    </script>
  </c:if>
</head>
<body>

  <div class="form-container">
    <h2>자녀 이메일 인증</h2>

    <!-- ✅ 1단계: 자녀 아이디 + 이메일 입력 -->
    <form action="RequestConnection.do" method="post">
      <label for="targetId">자녀 아이디</label>
      <input type="text" id="targetId" name="targetId" value="${sessionScope.receiverId}" 
      <c:if test="${not empty sessionScope.realCode}">readonly</c:if>
      required />

      <label for="targetEmail">자녀 이메일</label>
      <input type="email" id="targetEmail" name="targetEmail"  value="${sessionScope.targetEmail}"
      <c:if test="${not empty sessionScope.realCode}">readonly</c:if>
       required />

      <input type="submit" value="이메일로 인증번호 보내기" />
    </form>

    <!-- ✅ 2단계: 인증번호 입력창 (세션에 realCode가 있을 때만 노출) -->
    <c:if test="${not empty sessionScope.realCode}">

  <div class="info-message">📩 인증번호가 이메일로 전송되었습니다.</div>

  <form action="VerifyCode.do" method="post">
    <label for="inputCode">인증번호 입력</label>
    <input type="text" id="inputCode" name="inputCode" required />
    <input type="submit" value="확인" />
  </form>
</c:if>

  </div>

</body>
</html>
