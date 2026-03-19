<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>

  <!-- 1. 모바일 퍼스트 CSS 로드 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/SignIn.css">

  <!-- 2. 전역 contextPath 정의 -->
  <script>window.path='${pageContext.request.contextPath}';</script>

  <!-- 3. 카카오 우편번호 API 로드 -->
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link rel="shortcut icon" href="">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">

</head>
<body>
  <div class="sign-container">
    <!-- 다크 모드 토글 버튼 (카드 안 우측 상단) -->
    <div class="mode-toggle">
      <button id="toggleMode">🌙</button>
    </div>

    <!-- 로고 & 환영 문구 -->
    <div class="logo">
      <img src="${pageContext.request.contextPath}/image/Safe_Voice.png" alt="Safe Voice 로고">
      <h5>회원가입</h5>
      <p>Safe Voice에 오신 것을 환영합니다!</p>
    </div>

    <!-- 회원가입 폼 -->
    <form id="signForm"
          action="${pageContext.request.contextPath}/SignIn.do"
          method="post">

      <!-- 아이디 -->
	<div class="form-group">
  	<label for="username">아이디</label>
  	<input type="text"
    	   id="username"
       	   name="id"
           class="input-field"
           placeholder="영문+숫자 4~10자"
           required>
    <div id="username-msg" class="message"></div>
	</div>

      <!-- 비밀번호 -->
      <div class="form-group">
        <label for="pw1">비밀번호</label>
        <input type="password"
               id="pw1"
               name="pw"
               class="input-field"
               placeholder="PW를 입력하세요"
               required>
        <div id="pw1-msg" class="message"></div>
      </div>
      <div class="form-group">
        <label for="pw2">비밀번호 확인</label>
        <input type="password"
               id="pw2"
               class="input-field"
               placeholder="PW를 재입력하세요"
               required>
        <div id="pw2-msg" class="message"></div>
      </div>

      <!-- 이메일 -->
      <div class="form-group">
        <label for="email-id">이메일</label>
        <div class="option-group">
          <input type="text"
                 id="email-id"
                 class="input-field"
                 placeholder="아이디 부분"
                 autocomplete="username"
                 required>
          @
          <input type="text"
                 id="domain-txt"
                 class="input-field"
                 value="naver.com"
                 disabled
                 required>
          <select id="domain-list" class="input-field">
            <option value="gmail.com">gmail.com</option>
            <option value="naver.com" selected>naver.com</option>
            <option value="kakao.com">kakao.com</option>
            <option value="type">직접 입력</option>
          </select>
        </div>
        <!-- 서버로 보낼 전체 이메일 -->
        <input type="hidden"
               id="email-full"
               name="email"
               value="">
      </div>

      <!-- 이름 -->
      <div class="form-group">
        <label for="name">이름</label>
        <input type="text"
               id="name"
               name="name"
               class="input-field"
               placeholder="이름을 입력하세요"
               required>
      </div>

      <!-- 생년월일 -->
      <div class="form-group">
        <label for="birth">생년월일</label>
        <input type="date"
               id="birth"
               name="birthDate"
               class="input-field"
               required>
      </div>

<!-- 주소 입력 폼 그룹 -->
<div class="form-group">
  <label for="postcode">우편번호 & 주소</label>
  <div class="option-group" style="display:flex; gap:8px; align-items:center;">
    <input type="text"
           id="postcode"
           name="postcode"
           class="input-field"
           placeholder="우편번호"
           readonly
           required>
    <button type="button"
            id="btnPostcode"
            class="btn-submit">
      우편번호 찾기
    </button>
  </div>
  <input type="text"
         id="address"
         name="address"
         class="input-field"
         placeholder="주소"
         readonly
         required>
  <input type="text"
         id="detailAddress"
         name="detailAddress"
         class="input-field"
         placeholder="상세주소"
         required>
</div>

<!-- (embed 방식) 레이어 팝업용 HTML -->
<div id="postcodeLayer" class="postcode-layer">
  <div id="postcodeContainer" class="postcode-wrap">
    <!-- 닫기 버튼 구현 -->
    <button type="button"
            id="closePostcodeLayer"
            class="postcode-close-btn">
      ✕
    </button>
    <!-- 우편번호 검색 UI가 embed 됩니다 -->
  </div>
</div>


      <!-- 전화번호 -->
      <div class="form-group phone-group">
        <label for="phone1">전화번호</label>
        <div class="phone-inputs">
          <input type="text"
                 id="phone1"
                 class="input-field"
                 maxlength="3"
                 placeholder="010"
                 required>
          <input type="text"
                 id="phone2"
                 class="input-field"
                 maxlength="4"
                 placeholder="1234"
                 required>
          <input type="text"
                 id="phone3"
                 class="input-field"
                 maxlength="4"
                 placeholder="5678"
                 required>
        </div>
        <!-- 결합된 전화번호 전송용 -->
        <input type="hidden"
               id="tel"
               name="phone"
               value="">
      </div>
      <div id="MyNum" class="message"></div>

      <!-- 성별 -->
      <div class="form-group">
        <label for="gender">성별</label>
        <select id="gender"
                name="gender"
                class="input-field"
                required>
          <option value="">선택하세요</option>
          <option value="W">여성</option>
          <option value="M">남성</option>
        </select>
      </div>

      <!-- 회원(부모/자녀) 구분 -->
      <div class="form-group">
        <label>구분</label>
        <label>
          <input type="radio"
                 name="memType"
                 value="P">
          부모님
        </label>
        <label>
          <input type="radio"
                 name="memType"
                 value="C">
          자녀
        </label>
      </div>

      <!-- 약관동의 -->
      <div class="form-group consent-group">
        <label class="consent-label">
          <input type="checkbox"
                 id="agreeAll"
                 class="consent-checkbox">
        </label>
        <a href="#"
           id="openTerms"
           class="terms-link">
          개인정보 활용 및 서비스 이용약관
        </a>
        <span class="consent-text">에 동의합니다.</span>
        <div id="consent-msg"
             class="message red"
             style="display:none;">
          약관 전문을 확인하고 동의해주셔야 합니다.
        </div>
      </div>

      <!-- 제출 버튼 -->
      <div class="form-group">
        <button type="submit"
                class="btn-submit">
          회원가입
        </button>
      </div>
    </form>
 <!-- 회원가입 환영메세지 출력 코드
<div id="welcomeModal" class="modal" style="display:none;">
  <div class="modal-content" style="max-width:300px; margin:20% auto; padding:1rem;">
    <p id="welcomeMessage" style="margin-bottom:1rem; text-align:center; font-weight:600;"></p>
    <button id="welcomeBtnOk" class="btn-submit">확인</button>
  </div>
</div>
    <!-- 모달 팝업: 약관 전문
    <div id="termsModal" class="modal">
      <div class="modal-content terms-content">
        <div class="terms-header">
          <h5>개인정보 활용 및 서비스 이용약관</h5>
          <button id="closeTerms" class="close-btn">✕</button>
        </div>
        <div class="terms-body">
          Safe Voice(이하 '회사')는 이용자의 개인정보 보호와 원활한 서비스 제공을 위해 『개인정보 처리방침』 및 『서비스 이용약관』을 아래와 같이 수립·공개합니다. 
  <p class="clause-title">제1장 총칙</p>
  <p class="provision">제1조(목적)</p>
  본 방침은 회사가 제공하는 음성 분석 기반 학교폭력 조기감지 서비스(이하 '서비스') 이용과정에서 수집되는 개인정보의 처리방침을 규정함을 목적으로 합니다. 
  <p class="provision">제2조(용어 정의)</p>
1. '개인정보'란 생존하는 개인에 관한 정보로서, 해당 정보만으로는 특정 개인을 식별할 수 없더라도 다른 정보와 쉽게 결합하여 알아볼 수 있는 정보를 말합니다. 
<br>
2. '이용자'란 본 방침에 따라 서비스를 이용하는 회원 및 비회원을 말합니다. 
<br>
3. '회원'이란 회사에 개인정보를 제공하여 등록 절차를 완료한 자로서, 회사가 제공하는 서비스를 계속적으로 이용할 수 있는 자를 말합니다. 
  <p class="clause-title">제2장 개인정보 처리방침</p>
  <p class="provision">제3조(수집하는 개인정보 항목)</p>
  1. 회원가입 시 수집 항목
  <br>
:회원 아이디, 이메일 주소, 비밀번호, 이름, 생년월일, 전화번호, 우편번호, 주소, 상세 주소, 성별, 회원 구분(부모/자녀) 
 <p class="provision">제4조(게인정보 수집 방법)</p>
 홈페이지, 모바일 애플리케이션, 팝업 동의창 등을 통해 수집합니다. 
 <p class="provision">제5조(개인정보 수집 및 이용 목적)</p>
1. 회원관리: 회원제 서비스 제공 및 관리, 본인 식별·인증 
<br>
2. 서비스 제공: 서비스 이행 
<br>
3. 민원 처리: 고객 문의 응대, 불만 처리 
<br>
4. 통계 분석: 서비스 이용 현황 파악 및 개선 
 <p class="provision">제6조(개인정보 보유 및 이용기간)</p>
 - 회원 탈퇴 시 지체 없이 파기하되, 관계 법령에 따라 보존할 필요가 있는 경우 해당 기간 동안 보관 후 파기합니다. 
 <p class="provision">제7조(개인정보의 파기 절차 및 방법)</p>
 - 전자적 파일: 복구 불가능한 방법으로 영구 삭제 
<p class="clause-title">제2장 개인정보 처리방침</p>
 <p class="provision">제8조(이용 계약의 성립)</p>
1. 이용계약은 이용자가 본 약관에 동의하고 회원가입을 완료한 시점에 성립합니다. 
<br>
2. 회사는 다음 각 호의 신청에 대해 승낙을 거부할 수 있습니다. 
<br>
1) 타인의 명의를 도용한 경우 
<br>
2) 신청 내용이 허위·부정확·누락된 경우 
<br>
3) 기타 회사 정책에 위배되는 경우 
 <p class="provision">제9조(서비스의 제공 및 변경)</p>
1. 회사는 다음과 같은 서비스를 제공합니다. 
<br>
- 실시간 음성 분석 기반 학교폭력 조기감지 
<br>
- 분석 결과 리포트 및 알림 
<br>
- 고객지원 및 상담 
<br>
2. 회사는 서비스 개선을 위해 변경·중단할 수 있으며, 이 경우 사전 공지합니다. 
 <p class="provision">제10조(이용자의 의무)</p>
 1. 이용자는 관계 법령과 본 약관 및 회사가 고지하는 이용 지침을 준수해야 합니다. 
<br>
2. 이용자는 타인의 개인정보 및 계정을 도용해서는 안 됩니다. 
  <p class="provision">제11조(서비스의 이용제한 및 계약 해지)</p>
1. 회사는 이용자가 본 약관을 위반하거나 부정행위를 하는 경우 서비스 이용을 제한하거나 계약을 해지할 수 있습니다. 
<br>
2. 이용자는 언제든지 회원 탈퇴를 요청할 수 있으며, 회사는 지체 없이 처리합니다. 
 <p class="provision">제12조(면책조항)</p>
 1. 천재지변 등 불가항력으로 인한 서비스 중단에 대해 책임을 지지 않습니다. 
<br>
2. 이용자의 귀책사유로 인한 서비스 장애에 대해 책임을 지지 않습니다. 
  <p class="clause-title">부칙</p>
  이 약관은 2025년 5월 1일부터 시행합니다. 
        </div>
        <div class="terms-footer">
          <button id="agreeBtn" class="btn-submit">동의</button>
          <button id="cancelBtn" class="btn-cancel">취소</button>
        </div>
      </div>
    </div>
-->

    <!-- 하단 링크 -->
    <div class="sign-footer">
      이미 계정이 있으신가요?
      <a href="GoLogin.do">로그인</a>
    </div>
    <!-- SignIn.js 연결 -->
<script type="module" defer src="${pageContext.request.contextPath}/js/SignIn.js"></script>
    
</body>
</html>
