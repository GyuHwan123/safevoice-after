<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>비밀번호 수정</title>
  <!-- CSS 파일 연결 -->
  <link rel="stylesheet" href="<c:url value='/css/FindingPassword.css'/>"/>
  <!-- favicon.ico 404 방지 -->
  <link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<body>
  <!-- JSP 컨텍스트패스를 JS에서 사용하기 위해 전역 변수 선언 -->
  <script>
    const contextPath = '${pageContext.request.contextPath}';
  </script>
  
  <!-- 1) 아이디 입력받는 폼 영역 -->
  <div id="findContainer" class="mobile-container">
    <!-- 로고 & 타이틀 -->
    <img src="${pageContext.request.contextPath}/image/Safe_Voice.png"
         alt="Safe Voice 로고" class="logo">
    <h3 class="title">비밀번호 찾기</h3>
    <form id="findForm">
      <input type="text" id="username" placeholder="아이디를 입력하세요" required/>
      <button type="submit">아이디 확인</button>
      <p id="findMsg" class="message"></p>
    </form>
    <!-- 로그인 · 회원가입 -->
    <div class="small-text">
      <a href="GoLogin.do">로그인</a>
      <a href="GoSignIn.do">회원가입</a> 
    </div>
  </div>

  <!-- 2) 비밀번호 수정 폼 영역 (아이디는 hidden으로 전달) -->
  <div id="resetContainer" class="mobile-container hidden">
    <img src="${pageContext.request.contextPath}/image/Safe_Voice.png" alt="Safe Voice 로고" class="logo">
    <h3 class="title">비밀번호 수정</h3>
    <form id="resetForm" action="${contextPath}/ChangePassword.do" method="GET">
      <input type="hidden" id="id" name="id"/> <!-- 여기서 id 값이 넘어갑니다 -->
      <input type="password" id="pw1" name="updatePw" placeholder="새 비밀번호 (영문+숫자 포함, 8자 이상)" required/>
      <p id="pw1-msg" class="message"></p>

      <input type="password" id="pw2" name="confirmPassword" placeholder="비밀번호 확인" required/>
      <p id="pw2-msg" class="message"></p>

      <button type="submit">비밀번호 변경</button>
      <p id="resetMsg" class="message"></p>
    </form>
    <!-- 로그인 · 회원가입 -->
    <div class="small-text">
      <a href="GoLogin.do">로그인</a>
      <a href="GoSignIn.do">회원가입</a>
    </div>
  </div>

  <!-- JS 파일 로드 -->
  <script src="<c:url value='/js/FindingPassword.js'/>"></script>
</body>
</html>

