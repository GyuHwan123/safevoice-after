<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>알림 설정</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AlertConfig.css">
  <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
</head>
<body>

  <header>
<img src="${pageContext.request.contextPath}/image/뒤로가기.png" alt="뒤로가기" class="back-button" />
    <div class="title">알림 설정</div>
<img src="${pageContext.request.contextPath}/image/Mom.png" alt="부모 프로필" class="parent-icon" />  
</header>

<script>
  const contextPath = "${pageContext.request.contextPath}";
  window.memberId = "${sessionScope.loginMember.id}";
</script>

  <div class="section">

    <!-- SOS 알림 -->
    <div class="section-title">
      SOS 알림 반복 설정
      <div class="sos-setting">
        <span class="sos-setting-label">설정 안 함</span>
        <button class="setting-btn"><i class="fas fa-cog"></i></button>
        <ul class="setting-menu">
          <li>설정 안 함</li>
          <li>1분 마다</li>
          <li>3분 마다</li>
          <li>5분 마다</li>
        </ul>
      </div>
    </div>

<!-- SOS 알림 -->
<div class="sub-toggle">
  <span>소리</span>
  <label class="toggle-switch">
    <input type="checkbox" id="sos-sound" class="setting-toggle" checked />
    <span class="slider"></span>
  </label>
</div>

<div class="sub-toggle">
  <span>진동</span>
  <label class="toggle-switch">
    <input type="checkbox" id="sos-vibration" class="setting-toggle" checked />
    <span class="slider"></span>
  </label>
</div>

<!-- 경고 알림 -->
<hr>
<div class="sub-toggle">
  <span>경고 알림 설정</span>
  <label class="toggle-switch">
    <input type="checkbox" id="warn-enable" class="setting-toggle" checked />
    <span class="slider"></span>
  </label>
</div>

<div class="sub-toggle">
  <span>소리</span>
  <label class="toggle-switch">
    <input type="checkbox" id="warn-sound" class="setting-toggle" checked />
    <span class="slider"></span>
  </label>
</div>

<div class="sub-toggle">
  <span>진동</span>
  <label class="toggle-switch">
    <input type="checkbox" id="warn-vibration" class="setting-toggle" checked />
    <span class="slider"></span>
  </label>
</div>

<!-- 주의 알림 -->
<hr>
<div class="sub-toggle">
  <span>주의 알림 설정</span>
  <label class="toggle-switch">
    <input type="checkbox" id="caution-enable" class="setting-toggle" />
    <span class="slider"></span>
  </label>
</div>


  </div>

     <footer>
      <div class="bottom-navbar">
          <a href = "GoAlertHistory.do"><img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" /><span>알림</span></a>
          <a href = "GoMainPageAdult.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
          <a href = "GoMenuMain.do"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
      </div>
  </footer>
</div>

   <script src="/SafeVoice/js/AlertConfig.js" defer></script>
</body>
</html>
