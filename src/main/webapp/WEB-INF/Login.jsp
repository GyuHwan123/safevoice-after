<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Safe Voice - 로그인</title>
  
  <!-- CSS/JS 경로 -->
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/css/Login.css">
  <script src="${pageContext.request.contextPath}/js/Login.js" defer></script>
  
<!-- 비밀번호 변경 성공 alert -->
<c:if test="${not empty sessionScope.successMsg}"> 
    <script>
        alert('${sessionScope.successMsg}');
    </script>
    <c:remove var="successMsg" scope="session" />
</c:if>

<!-- 로그아웃 후 alert -->
<c:if test="${not empty sessionScope.logoutMsg}">
    <script>
        alert('${sessionScope.logoutMsg}');
    </script>
    <c:remove var="logoutMsg" scope="session" />
</c:if>

<!-- 로그인 에러 alert -->
<c:if test="${not empty sessionScope.error}">
    <script>
        alert('${fn:escapeXml(sessionScope.error)}');
    </script>
</c:if>

</head>
<body>
  <div class="container">
    <!-- 다크 모드 토글 -->
    <div class="mode-toggle">
      <button id="toggleMode">🌙</button>
    </div>
    <!-- 로고 & 타이틀 -->
    <img src="${pageContext.request.contextPath}/image/Safe_Voice.png"
         alt="Safe Voice 로고" class="logo">
         <h3 class="title">로그인</h3>
    <p class="subtitle">
      당신의 아이를 지키는 작은 관심<br><br>
      - AI 음성 분석 기반 학교폭력 조기감지 서비스 -
    </p>
<!-- 로그인 폼 -->
     <form id="loginForm"
        action="Login.do"
        method="post">

    <input class="input-field"
    type="text"
           name="id"
           placeholder="아이디"
           value="${fn:escapeXml(param.id)}" />
    <br><br>

    <input class="input-field"
type="password"
           name="pw"
           placeholder="비밀번호" />
    <br>
    <div class="login-options">
      <label>
        <input type="checkbox" name="saveId" />
        아이디 저장
      </label>
      <label>
        <input type="checkbox" name="autoLogin" />
        자동 로그인
      </label>
    </div>
    <button id="loginBtn" type="submit" class="submit-button">로그인</button>
  </form>
<!-- 소셜 로그인 구분선 + 안내 텍스트 -->
<div class="social-divider">
  <span>소셜로 로그인</span>
</div>
<!-- 소셜 로그인 -->
<div class="social-login">
<div class="social-login">
  <!-- 구글 로그인 -->
  
  <a href="GoogleLogin.do" class="gsi-material-button" title="구글로 로그인">
      <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" xmlns:xlink="http://www.w3.org/1999/xlink" style="display: block;">
        <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
        <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
        <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
        <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
        <path fill="none" d="M0 0h48v48H0z"></path>
      </svg>
  </a>

  <!-- 네이버 로그인 -->
  <form action="${pageContext.request.contextPath}/auth/social" method="get" style="display:inline-block;">
    <input type="hidden" name="provider" value="naver"/>
    <button type="submit" class="naver-login-btn" title="네이버로 로그인">
      <img src="${pageContext.request.contextPath}/image/naver-4.png" alt="네이버 로그인" class="naver-icon"/>
    </button>
  </form>
</div>
    <!-- 회원가입 · 비밀번호 찾기 -->
    <div class="small-text">
      <a href="GoSignIn.do">회원가입</a> |
      <a href="GoFindingPassword.do">비밀번호 찾기</a>
    </div>
  </div>

  <!-- 로그인 성공/실패 알림창 -->
  <div id="successModal" class="modal">
    <div class="modal-content">
      <p>✅ 로그인 성공!</p>
      <button id="closeModalBtn">확인</button>
    </div>
  </div>
  <div id="errorModal" class="modal">
    <div class="modal-content">
      <p>❌ 로그인 실패!</p>
      <button id="closeErrorModalBtn">확인</button>
    </div>
  </div>

  <!--  JSP 페이지에서 서버가 로그인(또는 다른 처리)을 마친 뒤에
?result=success 혹은 ?result=error 를 URL 파라미터로 던져주면,
그 값을 보고 알림 창을 띄워주는 로직 -->
  <c:if test="${param.result == 'success'}">
    <script>
      document.getElementById('successModal').style.display = 'flex';
    </script>
  </c:if>
  <c:if test="${param.result == 'error'}">
    <script>
      document.getElementById('errorModal').style.display = 'flex';
    </script>
  </c:if>
</body>
</html>
