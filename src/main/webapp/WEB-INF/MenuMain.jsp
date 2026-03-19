<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Safe Voice - 메뉴</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MenuMain.css">
  
</head>

<body>
  <div class="menu-container">
    <header>
    
<img src="${pageContext.request.contextPath}/image/뒤로가기.png" alt="뒤로가기" class="back-button" />
     
      <div class="header-title">메뉴</div>
      <img class="profile-img" src="${pageContext.request.contextPath}/image/Mom.png" alt="프로필">
    </header>

    <div class="user-info">
  <img id="user-photo" src="${pageContext.request.contextPath}/image/Mom.png" alt="프로필">
  <input type="file" id="file-input" style="display:none;" accept="image/*">

  <div class="email" id="user-email">userEmail@example.com</div>
  <div class="name" id="user-name">나엄마</div>

 
</div>

    <ul class="menu-list">
        <li class="menu-item">
          <a href="GoModifyMember.do"><i class="fas fa-user"></i> 계정 정보</a>
        </li>
        <li class="menu-item">
          <a href="GoAlertConfig.do"><i class="fas fa-bell"></i> 알림 설정</a>
        </li>
        <li class="menu-item">
          <a href="#"><i class="fas fa-bullhorn"></i> 공지사항</a>
        </li>
        <li class="menu-item">
          <a href="ViewChildList.do"><i class="fas fa-child"></i> 자녀 관리</a>
        </li>
        <li class="menu-item">
          <a href="#"><i class="fas fa-file-alt"></i> 이용약관</a>
        </li>
        <li class="menu-item">
          <a href="#"><i class="fas fa-shield-alt"></i> 개인정보 처리방침</a>
        </li>
        <li class="menu-item">
          <a href="#"><i class="fas fa-info-circle"></i> 프로그램 정보</a>
        </li>
      </ul>
      


       
		
		<div class="menu-footer">
		  <a href="Logout.do">로그아웃</a> | <a href="SignOut.do">회원탈퇴</a>
		</div>
      <footer>
        <div class="bottom-navbar">
            <a href = "GoAlertHistory.do"><img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" /><span>알림</span></a>
            <a href = "GoMainPageAdult.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
            <a href = "GoMenuMain.do"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
        </div>
    </footer>
</div>
 <script src="${pageContext.request.contextPath}/js/MenuMain.js"></script>

</body>
</html>

