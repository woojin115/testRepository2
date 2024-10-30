<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>
<%
	//공지글 정보 변수처리
	
	Notice n = (Notice)request.getAttribute("notice");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#update-area>table{
		border : 1px solid white;
	}
	#update-area input,#update-area textarea{
		width: 100%;
		box-sizing : border-box;
	}

</style>
</head>
<body>
	<%@ include file = "/views/common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 수정 페이지</h2>
		
		
		<form action="<%=contextPath%>/update.no" method="post">
			<!-- 해당 공지글 번호 hidden으로 숨겨서 전달하기 -->
			<input type="hidden" name="nno" value="<%=n.getNoticeNo() %>">
			<table id="update-area" align="center" border="1">
				<tr>
					<th width="70">제목</th>
					<td width="350"><input type="text" name="title" value="<%=n.getNoticeTitle()%>"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><%=n.getNoticeWriter() %></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><%=n.getCount() %></td>
				</tr>
				<tr>
					<th>작성일</th>
					<td><%=n.getCreateDate() %></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="5">
						<textarea name="content" rows="10" style="resize:none;"><%=n.getNoticeContent() %></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			
			<div align="center">
				<button type="submit" class="btn btn-primary">수정하기</button>
				<a href="<%=contextPath %>/list.no" class="btn btn-secondary">목록으로</a>
			</div>
		</form>
		
		<br><br>
	</div>
	
</body>
</html>