<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>자녀 관리</title>

  <script>
    const contextPath = '<%= request.getContextPath() %>';
  </script>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ChildConfig.css" />
  <script src="${pageContext.request.contextPath}/js/ChildConfig.js" defer></script>
  <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js" defer></script>
</head>

<body>
  <div class="container">
    <!-- 헤더 -->
    <header>
      <img src="${pageContext.request.contextPath}/image/뒤로가기.png" alt="뒤로가기" class="back-button" />
      <h1>자녀 관리</h1>
      <img src="${pageContext.request.contextPath}/image/Mom.png" alt="부모 프로필" class="parent-icon" />
    </header>

    <!-- 자녀 리스트 -->
    <section class="child-list" id="childList">
  <c:forEach var="child" items="${childrenList}">
    <div class="child-item">
      <img src="${pageContext.request.contextPath}/image/프로필.png" class="child-avatar" />
      <div class="child-info">
        <div class="child-name">${child.name}</div>
        <div class="child-date">최근 조회 날짜<br>정보 없음</div>
      </div>
      <div class="child-actions">
        <form action="DeleteChild.do" method="post" class="delete-form">
            <input type="hidden" name="childId" value="${child.id}" />
            <button type="submit" class="delete-btn"> 
              <img src="${pageContext.request.contextPath}/image/휴지통.png" alt="삭제" class="delete-btn" />
            	  </button>
              </form>
        <img src="${pageContext.request.contextPath}/image/연필.png" alt="수정" class="edit-btn" />
        <img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" class="drag-handle" />
      </div>
    </div>
  </c:forEach>
</section>

    <!-- 자녀 추가 버튼 -->
    <a href="GoRequestConnection.do" class="add-child">
  <img src="${pageContext.request.contextPath}/image/추가.png" alt="추가" />
  <span>자녀 추가하기</span>
</a>

    <!-- 하단 메뉴 -->
    <footer>
      <div class="bottom-navbar">
        <a href="GoAlertHistory.do"><img src="${pageContext.request.contextPath}/image/알림1.png" alt="알림" /><span>알림</span></a>
        <a href="GoMainPageAdult.do"><img src="${pageContext.request.contextPath}/image/홈.png" alt="홈" /><span>홈</span></a>
        <a href="GoMenuMain.do"><img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" /><span>메뉴</span></a>
      </div>
    </footer>
  </div>

  <!-- ✅ 자녀 추가 후 화면에 표시 -->
<c:if test="${not empty sessionScope.addedChildId}">
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const list = document.querySelector(".child-list");
      const today = new Date().toISOString().slice(0, 10);
      const addedChildId = "${sessionScope.addedChildId}";

      if (list && addedChildId) {
        const newChild = document.createElement("div");
        newChild.className = "child-item";

        newChild.innerHTML = `
          <img src="${pageContext.request.contextPath}/image/프로필.png" class="child-avatar" />
          <div class="child-info">
            <div class="child-name">${addedChildId}</div>
            <div class="child-date">최근 조회 날짜<br>${today}</div>
          </div>
          <div class="child-actions">
            <form action="DeleteChild.do" method="post" class="delete-form">
              <input type="hidden" name="childId" value="${addedChildId}" />
              <button type="submit" class="delete-btn" style="background:none; border:none; padding:0; cursor:pointer;">
                <img src="${pageContext.request.contextPath}/image/휴지통.png" alt="삭제" class="delete-btn" />
              </button>
            </form>
            <img src="${pageContext.request.contextPath}/image/연필.png" alt="수정" class="edit-btn" />
            <img src="${pageContext.request.contextPath}/image/메뉴.png" alt="메뉴" class="drag-handle" />
          </div>
        `;

        list.appendChild(newChild);
      }
    });
  </script>

  <c:remove var="addedChildId" scope="session" />
</c:if>

</body>
</html>