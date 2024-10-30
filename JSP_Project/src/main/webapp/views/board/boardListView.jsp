<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	 <%-- <jsp:include page="/views/common/menubar.jsp"/> --%>
	<%@ include file="/views/common/menubar.jsp" %>
	 
	<div class="outer">
		<br>
		<h2 align="center">게시판</h2>	
		<br><br>
		
		<%-- 글작성 버튼은 로그인한 회원일 경우 보일 수 있도록 조건처리 --%>
		
		<c:if test="${not empty loginUser }">
			<div align="center">
				<a href="${contextPath }/insert.bo" class="btn btn-info">글작성</a>
			</div>
		</c:if>
		<br>
		
		<table class='list-area' align='center' border='1'>
			<thead>
				<tr>
					<th width='70'>글 번호</th>
					<th width='70'>카테고리</th>
					<th width='300'>제목</th>
					<th width='100'>작성자</th>
					<th width='50'>조회수</th>
					<th width='100'>작성일</th>
				</tr>
			</thead>
			<tbody>
			<%--
				<tr>
					<td>10</td>
					<td>운동</td>
					<td>제목입니다</td>
					<td>유저123</td>
					<td>99</td>
					<td>2024-10-22</td>
				</tr>
				<tr>
					<td>15</td>
					<td>요리</td>
					<td>흑백요리사2하나요</td>
					<td>비룡</td>
					<td>152</td>
					<td>2024-10-20</td>
				</tr>
			--%>
			<c:choose>
				<c:when test="${empty list }"> <!-- list가 비어있다면 -->
					<tr>
						<td colspan="6">조회된 게시글이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise><!-- 비어있지 않다면 -->
					<!-- 담겨있는 모든 데이터 순차적으로 접근해서 출력하기 -->
						<%-- 자바방식 : for(Board b : list){}--%>
					<c:forEach var="b" items="${list}">
						<tr>
							<td>${b.boardNo }</td>
							<td>${b.categoryNo }</td>
							<td>${b.boardTitle }</td>
							<td>${b.boardWriter }</td>
							<td>${b.count }</td>
							<td>${b.createDate }</td>
						</tr>
					</c:forEach>				
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
		<!--  
			글목록에 있는 글을 클릭했을때 해당 글의 상세보기 할 수 있도록 처리하기
			조회수 증가 메소드 구현하기 (공지사항 상세보기 메소드 참조)
			조회수 증가 메소드명 : increaseCount() 
			상세보기 Controller명 : BoardDetailController
			상세보기 메소드명 : selectBoard()
			매핑주소 : detail.bo
			조회할 데이터 : 번호 카테고리명 글제목 글내용 작성자명 작성일
			함수명 : detailBoard() 
			
		
		 -->
		<script>
			$(function(){
					$(".list-area>tbody>tr").click(function(){
						//글번호 추출
						//console.log($(this).children().first().text());
						var bno = $(this).children().first().text();
						
						location.href="detail.bo?bno="+bno;
					});	
			});
		</script>
		
		
		
		<br><br>
		
		<div align="center" class="paging-area">
			<!-- 이전/다음 버튼은 현재페이지에 따라서 만들어준다. 
				1페이지일땐(시작) 이전 버튼 없음 
				마지막 페이지일땐 다음 버튼 없음 
			 -->
			 <c:if test="${pi.currentPage != 1 }">
			 	<button onclick="location.href='list.bo?currentPage=${pi.currentPage-1}'">이전</button>
			 </c:if>
			
			<c:forEach var="i" begin="${pi.startPage}" end="${pi.endPage}">
				
				<c:choose>
					<c:when test="${i != pi.currentPage }">
						<button onclick="location.href='list.bo?currentPage=${i}'">${i}</button>
					</c:when>
					<c:otherwise>
						<!-- 현재 페이지 버튼은 비활성화 -->
						<button disabled>${i}</button>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			 <c:if test="${pi.currentPage != pi.maxPage }">
			 	<button onclick="location.href='list.bo?currentPage=${pi.currentPage+1}'">다음</button>
			 </c:if>
		</div>
		
		<br><br>
	</div>
	
</body>
</html>