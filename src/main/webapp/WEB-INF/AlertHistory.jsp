<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>알림 내역</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AlertHistory.css" />
</head>
<body>
    <main class="frame">
    
        <section class="alert-header">
          <div class="back-button"> 
            <img src="${pageContext.request.contextPath}/image/뒤로가기.png" alt="뒤로가기" />
          </div>
      
          <h1 class="page-title">알림 내역</h1>
      
          <div class="header-right">
            <div class="profile-icon">
              <img src="${pageContext.request.contextPath}/image/Mom.png" alt="프로필">
            </div>
            <div class="ellipse"></div>
          </div>
        <%--   <div class="menu-icon">
            <img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴">
        </div> --%>
        </section>
      
        <section class="alert-list">

           <!-- <!--  예시
            <section class="alert-list">
                <article class="alert-item">
                  <div class="alert-content">
                    <div class="alert-date">2025년 4월 10일</div>
                    <div class="alert-time">AM:10:42:33</div>
                    <div class="alert-type">경고</div>
                  </div>
                  <div class="alert-icon">!</div>
                </article>
                예시

          알림 항목들이 JS로 들어올 자리
        </section> -->
      
      </main>
  
    
       <footer>
            <div class="bottom-navbar">
                <a href = "GoAlertHistory.do"><img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" /><span>알림</span></a>
                <a href = "GoMainPageAdult.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
                <a href = "GoMenuMain.do"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
            </div>
        </footer>
    </div>

<script src="${pageContext.request.contextPath}/js/AlterHistory.js"></script>

</body>
</html>