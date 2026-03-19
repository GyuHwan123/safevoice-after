<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Safe Voice - 아이 안전 서비스</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/MainPageChild.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

</head>
<body>

<script>
  window.loginMemberId = '${loginMember.id}';
</script>

    <div class="container">
    <header class="top-header">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/image/logo.png" alt="Safe Voice 로고">
        </div>
        <div class="profile-icon">
            <img src="${pageContext.request.contextPath}/image/child1.png" alt="프로필">
        </div>
    </header>
  
      <div class="message">
        부모님이 걱정하시지 않게<br>먼저 연락해주세요!
      </div>
  
      <div class="avatars">
         <img src="${pageContext.request.contextPath}/image/Daddy.png" alt="아빠">
         <img src="${pageContext.request.contextPath}/image/Mom.png" alt="엄마">
      </div>
  
      <div class="divider"></div>
  
      <div class="profile-section">
        <div class="profile-image">
           <img src="${pageContext.request.contextPath}/image/Mom.png" alt="엄마">
        </div>
        <div class="profile-info">
          <h2>나엄마</h2>
          <p>마지막 알림</p>
          <p>2025년 4월 10일</p>
          <p>AM:10:42:33</p>
        </div>
      </div>
  
       <div class="sos-button">SOS</div>
      <div class="voice-upload">
      <form id="uploadForm" action="InputFile.do" method="post" enctype="multipart/form-data">
      <input type="hidden" name="memberId" value="${loginMember.id}" />
  		<label class="upload-label">
    		🎤 음성 파일 업로드
   	  <input type="file" name = "file" accept="audio/*" hidden
   	  		onchange="document.getElementById('uploadForm').submit()" />
  		</label>
   	 </form>
</div>

    <footer>
        <div class="bottom-navbar">
            <a href = "#"><img src="${pageContext.request.contextPath}/image/전화.png" /><span>전화</span></a>
            <a href = "GoMainPageChild.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
            <a href = "#"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
        </div>
    </footer>
  </div>
  <script src="${pageContext.request.contextPath}/js/MainPageChild.js"></script>

</body>
</html>