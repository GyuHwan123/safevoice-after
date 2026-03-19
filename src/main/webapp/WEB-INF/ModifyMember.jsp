<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원정보 수정</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>

  <!-- 1. 모바일 퍼스트 CSS 로드 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/SignIn.css">

  <!-- 2. 전역 contextPath 정의 -->
  <script>window.path='${pageContext.request.contextPath}';</script>

  <!-- 3. 카카오 우편번호 API 로드 -->
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ModifyMember.css">
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script>
    const contextPath = '${pageContext.request.contextPath}';
  </script>
  <script defer src="${pageContext.request.contextPath}/js/ModifyMember.js"></script>
</head>
<body>

<div class="wrapper">
  <div class="sign-container">
    <div class="form-header-bar">
      <div class="back-button">
        <img src="${pageContext.request.contextPath}/image/뒤로가기.png" alt="뒤로가기">
      </div>
      <h1>회원 정보</h1>
    </div>

    <form id="modifyForm" action="UpdateMember.do" method="post">
      <div class="section-card">
        <div class="card-title">계정 정보</div>

        <div class="info-row">
          <div class="label">아이디</div>
          <div class="value">
			<input type="text" name="id" placeholder="${form.member_id}" readonly autocomplete="username"/>
          </div>
        </div>

        <div class="info-row">
          <div class="label">이메일</div>
          <div class="value">
          	<div>
			<input type="text" id="email-id" placeholder="${form.email}" readonly/>
            </div>
            <!-- <span>@</span>
            <select id="domain-list">
              <option value="gmail.com">gmail.com</option>
              <option value="naver.com" selected>naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="type">직접입력</option>
            </select> -->
            <%-- <input type="text" id="domain-txt" value="${fn:substringAfter(form.email,'@')}">
            <input type="hidden" id="email-full" name="email" value="${form.email}">
          </div>
        </div> --%>


        <br>
		<div class="info-row">
          <div class="label">기존 비밀번호</div>
          <div class="value">
           <input type="password" id="current-pw" name="currentPw" placeholder="기존 비밀번호" >
            <div id="pw1-msg" class="message"></div>
          </div>
        </div>
        
        <div class="info-row">
          <div class="label">비밀번호 변경</div>
          <div class="value">
         <input type="password" id="pw1" name="pw" placeholder="새 비밀번호"  placeholder="새 비밀번호">
            <div id="pw1-msg" class="message"></div>
          </div>
        </div>

        <div class="info-row">
          <div class="label">비밀번호 확인</div>
          <div class="value">
		  <input type="password" id="pw2" placeholder="비밀번호 확인" >
            <div id="pw2-msg" class="message"></div>
          </div>
        </div>

        <div class="info-row">
          <div class="label">주소</div>
          <div class="value">
            <input type="text" id="postcode" name="postcode" placeholder="우편번호" readonly />
            <button type="button" id="btnPostcode">우편번호 찾기</button>
            <input type="text" id="address" name="address" placeholder="기본 주소" readonly />
            <input type="text" id="detailAddress" name="detailAddress" placeholder="상세 주소" />
          </div>
        </div>
      </div>

      <!-- 카카오 우편번호 검색 embed 레이어 -->
      <div id="postcodeLayer">
        <div id="postcodeContainer">
        </div>
        <button type="button" id="closePostcodeLayer">✕</button>
      </div>
      </div>

      <div class="btnArea type2">
        <button type="submit" class="btnSubmit">수정</button>
        <a href="${pageContext.request.contextPath}/GoMenuMain.do" class="btnEm">취소</a>
      </div>
    </form>

    <footer>
      <div class="bottom-navbar">
        <a href="${pageContext.request.contextPath}/GoAlertHistory.do">
          <img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" />
          <span>알림</span>
        </a>
        <a href="${pageContext.request.contextPath}/GoMainPageAdult.do">
          <img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" />
          <span>홈</span>
        </a>
        <a href="${pageContext.request.contextPath}/GoMenuMain.do">
          <img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" />
          <span>메뉴</span>
        </a>
      </div>
    </footer>
  </div>
</div>

</body>
</html>
