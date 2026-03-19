<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Safe Voice - 아이 안전 서비스</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MainPageAdult.css">
    <script>const contextPath = '${pageContext.request.contextPath}';</script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
</head>
<body>
<div class="popup-overlay" id="schoolAlertPopup">
  <div class="popup">
    <div class="profile-circle" id="popup-profile">
  	<img src="${pageContext.request.contextPath}/image/child2.png" alt="자녀 프로필">
	</div>

    <!-- ✅ 여기가 고쳐진 부분 -->
    <h2 id="alert-title">학교폭력<br>위험감지</h2>
    <p id="alert-desc">자녀의 통화에서 위험이 감지되었습니다</p>
    <p class="level">위험 수준 : <span id="alert-level" class="warning">경고</span></p>

    <div class="alert-actions"><br>
      <button onclick="location.href='#'" class="icon-button">
        <i class="fas fa-map-marker-alt"></i>
        <span>위치확인</span>
      </button>
      <button onclick="location.href='#'" class="icon-button">
        <i class="fas fa-phone-alt"></i>
        <span>전화걸기</span>
      </button>
      <button  id="confirm-alert-btn" onclick="location.href='GoAlertHistory.do'" class="icon-button">
        <i class="fas fa-bell"></i>
        <span>알림확인</span>
      </button>
    </div>
  </div>
</div>

      </div>    <div class="container">
        <header>
        	<a href="GoMainPageAdult.do">
            <div class="logo">
                <img src="${pageContext.request.contextPath}/image/Safe_Voice.png" alt="Safe Voice 로고">
            </div>
          	</a>       
          	
            <div class="header-right">
            <div class="profile-icon">
                <img src="${pageContext.request.contextPath}/image/Mom.png" alt="프로필">
            </div>
           <%--  <div class="menu-icon">
                <img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴">
            </div> --%>
        </div>
        </header>

        <section class="main-title">
            <h1>아이가 하교할 시간이에요<br>통화 한 번 어떠세요?</h1>
            <div class="child-profiles">
                <div class="child-profile">
                    <img src="${pageContext.request.contextPath}/image/child1.png" alt="아이 1">
                    <span class="notification">1</span>
                </div>
                <div class="child-profile">
                    <img src="${pageContext.request.contextPath}/image/child2.png" alt="아이 2">
                    <span class="notification">1</span>
                </div>
                <div class="child-profile">
                    <img src="${pageContext.request.contextPath}/image/child3.png" alt="아이 3">
                </div>
            </div>
            <hr>
        </section>

        <hr class="divider">

        <section class="content">
            <!-- 왼쪽: 아이 정보 -->
            <div class="profile-section">
              <div class="profile-image">
                <img src="${pageContext.request.contextPath}/image/child1.png" alt="나첫째">
              </div>
              <div class="profile-info">
                <h2>나첫째</h2>
                <p>마지막 알림</p>
                <p class="time">2025년 4월 10일</p>
                <p class="time">AM:10:42:33</p>
              </div>
            </div>
           <section class="action-buttons">
  <a href="#" class="action-button">
    <div class="button-icon">
      <i class="fas fa-map-marker-alt"></i>
    </div>
    <span>위치확인</span>
  </a>

  <a href="tel:010-1234-5678" class="action-button">
    <div class="button-icon">
      <i class="fas fa-phone-alt"></i>
    </div>
    <span>전화걸기</span>
  </a>

  <a href="GoAlertHistory.do" class="action-button">
    <div class="button-icon">
      <i class="fas fa-bell"></i>
      <span class="badge"></span>
    </div>
    <span>알림내역확인</span>
  </a>
</section>

          
            <!-- 오른쪽: 날씨 + 알림 -->
            <div class="right-section">
              <div class="weather-section">
                <h3>아이 날씨</h3>
                <div class="weather-icon">
                 <img src="${pageContext.request.contextPath}/image/sunny.png" alt="날씨">
              </div>
              </div>
              
              
          	<a href="#">
              <div class="alerts-section">
                <div class="alerts-container">
  <a href="GoAlertHistory.do" class="alert alert-sos">
  <span class="alert-icon">!</span>
  <span class="alert-text">SOS 알림</span>
  <span id="sos-count" class="alert-count">0회</span>
</a>
<a href="GoAlertHistory.do" class="alert alert-warning">
  <span class="alert-icon">!</span>
  <span class="alert-text">경고 알림</span>
  <span id="danger-count" class="alert-count">0회</span>
</a>
<a href="GoAlertHistory.do" class="alert alert-caution">
  <span class="alert-icon">!</span>
  <span class="alert-text">주의 알림</span>
  <span id="caution-count" class="alert-count">0회</span>
</a>

</div>

              </div>
            </div>
          </section>
          
          <!-- 하단 버튼 영역 -->
         
       <footer>
            <div class="bottom-navbar">
                <a href = "GoAlertHistory.do"><img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" /><span>알림</span></a>
                <a href = "GoMainPageAdult.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
                <a href = "GoMenuMain.do"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
            </div>
        </footer>
    </div>

<script src="${pageContext.request.contextPath}/js/MainPageAdult.js"></script>
</body>
</html>