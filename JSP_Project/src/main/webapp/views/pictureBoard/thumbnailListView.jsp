<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.list-area{
		width:760px;
		margin : auto;
	}
	.thumbnail{
		border : 1px solid white;
		width : 220px;
		display : inline-block;
		margin : 14px;
	}
	.thumbnail:hover{
		cursor : pointer;
		opacity : 0.7;
	}

</style>


</head>
<body>
	<%@ include  file="/views/common/menubar.jsp"%>
	
	<div class="outer">
		<h2 align="center">사진 게시판</h2>
		<br>
		
		<c:if test="${loginUser != null }">
		
			<div align="center">
				<button class="btn btn-info" onclick="location.href='${contextPath}/insert.th'">글작성</button>
			</div>
		</c:if>
		
		<!-- 글 클릭했을때 상세보기 페이지 작성하기
			 jsp : photoDetailView
			 controller : PhotoDetailController
			 method/function : photoDetail();
			 
			 -참고 페이지 : 사진게시글 작성페이지
			 작성 영역에 데이터 조회해서 보여주기 
			 
		 -->
		 
		 <script>
		 	$(function(){
		 		
		 		$(".thumbnail").click(function(){
		 			//글번호 추출
		 			var bno = $(this).find("input[type=hidden]").val();
		 			
		 			location.href='${contextPath}/detail.th?bno='+bno;
		 		});
		 		
		 	});
		 
		 </script>
		
		
		
		<div class="list-area">
			<!-- 사진 게시글 목록 반복돌려서 출력 -->
			<c:forEach items="${list }" var="b">
				<div class="thumbnail" align="center">
					<input type="hidden" value="${b.boardNo }"> <!-- 글번호 숨겨넣기 -->
					<img src="${contextPath}${b.titleImg}" width="200px" height="150px">
					<p>
						No.${b.boardNo } ${b.boardTitle }<br>
						조회수 : ${b.count }
					</p>
				</div>
			</c:forEach>
		
		
		</div>
	
	
	</div>
	
	
</body>
</html>