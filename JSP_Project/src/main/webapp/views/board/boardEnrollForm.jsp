<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#enroll-area input,#enroll-area textarea{
		width : 100%;
		box-sizing: border-box;
	}

</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시글 작성</h2>
		<br>
		
		
		<form action="${contextPath }/insert.bo" method="post" id="enroll-area" enctype="multipart/form-data">
			<!-- 작성자 정보 요청시 전달 같이하기 -->
			<input type="hidden" name="userNo" value="${loginUser.userNo }">
			
			<table border="1" align="center">
				<tr>
					<th width="70">카테고리</th>
					<td width="70">
						<!-- 카테고리 목록 띄워줄수 있도록 선택 상자 만들어주기 -->
						<select name="category">
							<!-- 카테고리 정보는 db에서 조회해온 뒤 반복문을 이용하여 option 태그에 넣어주기 -->
							<c:forEach items="${cList}" var="c">
								<option value="${c.categoryNo}">${c.categoryName}</option>
							</c:forEach>
						</select>
					</td>
					<th width="70">제목</th>
					<th width="350"><input type="text" name="title" required></th>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan='3'>
						<textarea name="content" rows='10' style='resize:none' required></textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<input type="file" name="uploadFile">
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