<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="RepeatAlert.do" method="post">
		<label>알림 주기 (분):</label> <select name="minutes">
			<option value="1">1분</option>
			<option value="3">3분</option>
			<option value="5">5분</option>
			<option value="10">10분</option>
		</select>
		<button type="submit">시작</button>
	</form>
</body>
</html>