<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

	#enroll-form table{
		border :1px solid white;	
	}
	#enroll-form input, #enroll-form textarea{
		width:100%;
		box-sizing : border-box;
	}
</style>

</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">공지사항 작성하기</h2>
		
		<form action="<%=contextPath %>/insert.no" method="post" id="enroll-form">
			<!-- 작성자 정보 요청시 전달 같이하기 -->
			<input type="hidden" name="userNo" value="<%=loginUser.getUserNo() %>">
			<table align="center">
				<tr>
					<th width="50">제목</th>
					<th width="350"><input type="text" name="title" required></th>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<td colspan='2'>
						<textarea name="content" rows='10' style='resize:none' required></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			
			<div align="center">
				<button type="submit">등록하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
				
			</div>
		
		
		</form>
		<br><br>
	</div>
</body>
</html>