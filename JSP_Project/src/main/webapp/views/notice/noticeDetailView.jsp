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
</head>
<body>
	<%@ include file = "/views/common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 상세보기 페이지</h2>
		
		<table id="detail-area" align="center" border="1">
			<tr>
				<th width="70">제목</th>
				<td width="350"><%=n.getNoticeTitle() %></td>
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
					<p style="height:150px"><%=n.getNoticeContent() %></p>
				</td>
			</tr>
		</table>
		<br><br>
		
		<div align="center">
			<a href="<%=contextPath %>/list.no" class="btn btn-secondary">목록으로</a>
			
			<%--수정 삭제는 관리자만 할 수 있도록 처리하기 --%>
			<%--null일 수 있는 대상에 대한 접근은 null값 비교처리가 선행되어야한다. --%>
			<%if(loginUser!=null && loginUser.getUserId().equals("admin")){ %>
				<a href="<%=contextPath %>/update.no?nno=<%=n.getNoticeNo()%>" class="btn btn-primary">수정하기</a>
				
				<%--
					<form action="<%=contextPath %>/delete.no" method="post">
						<a href="<%=contextPath %>/delete.no?nno=<%=n.getNoticeNo()%>" class="btn btn-danger">삭제하기</a>
						
						<input type="hidden" name="nno" value="<%=n.getNoticeNo() %>">
						<button type="submit" class="btn btn-danger">삭제하기</button>
					</form>
				 --%>
				 <button class="btn btn-danger" onclick="deleteNotice();">삭제하기</button>
			<%} %>
		</div>
	
		<br><br>
		
		<script>
			//form 요소 만들어서 submit하기 함수처리
			function deleteNotice(){
				//자바스크립트 방식 
				//DOM요소 생성 구문으로 form 요소 만들고 해당 요소의 submit(); 메소드 이용
				var form = document.createElement("form");
				form.method ='post';
				form.action ='<%=contextPath%>/delete.no';
				
				//입력요소 추가
				var input = document.createElement("input");
				input.type = 'hidden';
				input.name = 'nno';
				input.value = '<%=n.getNoticeNo()%>';
				
				//form요소 안에 input요소 자식요소로 추가 
				form.appendChild(input);
				
				//해당 form 요소를 DOM(문서)에 추가하기 
				document.body.appendChild(form);
				
				//form태그 submit 처리
				form.submit();
				
			}
			
			//제이쿼리 방식 DOM 생성 
			function deleteNoticeJQ(){
				//제이쿼리 방식 
				//DOM요소 생성 구문으로 form 요소 만들고 해당 요소의 submit(); 메소드 이용
				
				//입력요소 추가
				
				//form요소 안에 input요소 자식요소로 추가 
				
				//해당 form 요소를 DOM(문서)에 추가하기 
				
				//form태그 submit 처리
				
			}
		</script>
	</div>
	
</body>
</html>