<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<script src="<%= request.getContextPath() %>/resources/js/main.js"></script>
  <meta charset="UTF-8">
  <title>웹 푸시 알림 테스트</title>
  
    <!-- 🔐 contextPath & VAPID key 전달 -->
  <script>
    window.contextPath = "<%= request.getContextPath() %>";
    window.vapidPublicKey = "BBDwxhxTwL6k00-0sCIUW4mZhDlJt2R9jjAd2msSkh_52GZg8LeVKFcNBc7r__UOuI4_3RzrMvSdAjFIyZ0uEjI";
  </script>
  
</head>
<body>
  <h1>🔔 웹 푸시 알림 연습!</h1>
  <form action="RepeatAlert.do" method="post">
  <label>알림 주기 (분):</label>
  <select name="minutes">
    <option value="1">1분</option>
    <option value="3">3분</option>
    <option value="5">5분</option>
    <option value="10">10분</option>
  </select>
  <button type="submit">🔁 반복 시작</button>
</form>
  <button type="button" id="testPushBtn">💥 알림 테스트 보내기</button>

</body>
</html>
