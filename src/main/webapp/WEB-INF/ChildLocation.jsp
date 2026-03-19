<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
  <title>위치 확인</title>

 <!-- Kakao Maps SDK -->
  <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=1031f8c6ed74b6212ade7b9f349ba68d&autoload=false"></script>

  <!-- CSS 연결 -->
  <link rel="stylesheet" href="<c:url value='/css/ChildLocation.css'/>"/>
  
  <!-- Favicon 처리 -->
  <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
</head>
<body>
  <div class="sign-container">
    <!-- 1. 상단 검색 바 -->
    <div class="search-bar">
      <div class="menu-icon">☰</div>
      <input type="text" placeholder="주소 검색"/>
      <div class="search-icon">🔍</div>
    </div>

    <!-- 2. 위치 타입 토글 -->
    <div class="type-toggle">
      <button id="btnChild" class="active">아이 위치</button>
      <button id="btnInst">기관 위치</button>
    </div>

    <!-- 3. 지도 표시 영역 -->
    <div id="map"></div>

    <!-- 4. 스카이뷰 전환 -->
    <button id="skyToggle" class="sky-toggle" type="button"></button>

    <!-- 5. 하단 내비게이션 -->
    <div class="bottom-nav">
      <div class="nav-item active" id="locateBtn">
        <div class="icon">📍</div>
        <div class="label">위치 확인</div>
      </div>
      <div class="nav-item" id="alertBtn">
        <div class="icon">🔔</div>
        <div class="label">알림 확인</div>
      </div>
      <div class="nav-item" id="menuBtn">
        <div class="icon">☰</div>
        <div class="label">메뉴</div>
      </div>
    </div>
  </div>

  <!-- 6. 알림 팝업 엘리먼트 (초기엔 hidden) -->
  <div id="notificationPopup" class="notification-popup hidden">
    <div class="popup-header">알림</div>
    <ul id="notificationList" class="popup-list"></ul>
    <button id="closePopupBtn" class="popup-close">닫기</button>
  </div>
  
  <!-- JS 로드 -->
  <script>
    // JSP에서 전달된 값을 window 객체에 저장하여 중복 선언을 방지
    window.contextPath = '${pageContext.request.contextPath}';  // contextPath 값 JSP에서 전달
    window.rawKey = '${serviceKey}';  // 서비스 키도 JSP에서 전달받기
  </script>
  <script src="<c:url value='/js/ChildLocation.js'/>"></script>

</body>
</html>


