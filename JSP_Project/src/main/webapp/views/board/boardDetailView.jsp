<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--파일명 : boardDetailView -->
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시글 상세보기</h2>
		<br>
		<table border="1" align="center">
			<tr>
				<th width="70">카테고리</th>
				<td width="70">${b.categoryNo }</td>
				<th width="70">제목</th>
				<td width="350">${b.boardTitle }</td>
			</tr>
			<tr>
				<th width="70">작성자</th>
				<td width="70">${b.boardWriter }</td>
				<th width="70">작성일</th>
				<td width="350">${b.createDate }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:200px; white-space:pre;">${b.boardContent }</p>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th> <!-- 첨부파일이 있는 경우에만 보여주기 -->
				<td colspan="3">
					<c:choose>
						<c:when test="${empty at}">
							첨부파일이 없습니다 
						</c:when>
						<c:otherwise>
							<!-- 다운로드 속성 추가 -->
							<a href="${contextPath}${at.filePath}${at.changeName}">${at.originName}</a>
						</c:otherwise>
					</c:choose>
				
				</td>
			</tr>
		</table>
		
		
		<!-- 로그인한 회원이 작성자 본인 또는 관리자라면 수정/삭제 버튼 보이도록 -->
		<c:if test="${loginUser.userId == b.boardWriter or loginUser.userId eq 'admin'}">
			<br>
			<div align="center">
				<button type="button" onclick="location.href='${contextPath}/update.bo?bno=${b.boardNo}'">수정하기</button>
				<button type="button" id="deleteBtn">삭제하기</button>
			</div>
		</c:if>
		
		<br>
		
		<div id="reply-area">
			<table border="1" align="center">
				<thead>
					<c:choose>
						<c:when test="${not empty loginUser }">
							<tr>
								<th>댓글작성</th>
								<td>
									<textarea id="replyContent" rows="3" cols="50" style="resize:none;"></textarea>
								</td>
								<td><button onclick="insertReply();">댓글작성</button> </td>
							</tr>						
						</c:when>
						<c:otherwise>
							<tr>
								<th>댓글작성</th>
								<td>
									<textarea rows="3" cols="50" style="resize:none;" readonly>로그인 후 이용가능한 서비스입니다.</textarea>
								</td>
								<td><button disabled>댓글작성</button> </td>
							</tr>				
						</c:otherwise>
					</c:choose>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
		
		
		<script>
			$(function(){
				//문서 DOM요소 만들어지면 댓글목록 조회
				replyList();
			});
		
			//댓글작성
			function insertReply(){
				/*
					컨트롤러명 : InsertReplyController
					메소드명 : insertReply
					매핑주소 : insertReply.bo
					
					ajax를 이용하여 비동기식 처리하기 
					완료시 alert으로 댓글작성 성공 메시지 보여주며 작성란 비워주기
					실패시 alert으로 댓글 작성 실패 메시지 보여주기
				*/
				$.ajax({
					url : 'insertReply.bo',
					type : "post", 
					data : {
						replyContent : $("#replyContent").val(),
						refBno : "${b.boardNo}",
						replyWriter : "${loginUser.userNo}"
					},
					success : function(result){
						//성공 실패 여부에 따라서 보여질 작업 처리 
						if(result>0){
							alert("댓글 작성 성공!");
							$("#replyContent").val("");
							replyList(); //댓글목록 갱신
						}else{
							alert("댓글 작성 실패!");
						}
					},
					error : function(){
						console.log("통신 실패");
					}
				});
			}
		
			//댓글 목록 조회 함수
			function replyList(){
				$.ajax({
					url:"replyList.bo",
					data : {
						bno : "${b.boardNo}"
					},
					success : function(list){
						
						//조회된 list tbody에 추가하기
						var tr = "";
						
						for(var b of list){
							tr+="<tr>"
							   +"<td>"+b.replyWriter+"</td>"
							   +"<td>"+b.replyContent+"</td>"
							   +"<td>"+b.createDate+"</td>"
							   +"</tr>";
						}
						$("#reply-area tbody").html(tr);
					},
					error : function(){
						console.log("통신 오류");
					}
				});
			}
		
			$(function(){
				
				$("#deleteBtn").click(function(){
					<%-- 
					삭제하기 버튼을 누르면 정말 삭제하시겠습니까? 확인 취소 가 나오는 confirm을 이용하여
					확인을 눌렀을때 삭제 요청이 되도록 처리하기
					매핑주소 : delete.bo
					메소드명 : deleteBoard() 
					컨트롤러명 : BoardDeleteController
					
					함수를 이용하여 post 방식 form 요청이 될 수 있도록 처리할것 
					
					삭제시 DML - DELETE 를 사용한다면 업로드 파일도 삭제 
					삭제시 DML - UPDATE 를 사용한다면 업로드 파일은 보류
					--%>
					if(confirm("정말 삭제하시겠습니까?")){
						//DOM 생성 구문으로 form 요청해보기 
						//var form = $("<form>");
						//form.attr("method","POST").attr("action","${contextPath}/delete.bo");
						//attr도 전달값을 객체형식으로 제시가능
						//var form = $("<form>").attr({method:"POST",action:"${contextPath}/delete.bo"});
						//태그생성할때부터 추가하기 
						/*
						var form = $("<form>",{method:"POST",action:"${contextPath}/delete.bo"});
						
						//폼안에 넣어서 전송할 게시글 번호를 hidden으로 작성
						var inputEl = $("<input>",{type:"hidden",name:"boardNo",value:"${b.boardNo}"});
						//form에 input요소 추가 
						form.append(inputEl);
						//만든 form을 문서 body안에 넣고 submit 실행
						$("body").append(form);
						form.submit();
						*/
						//위 구문을 한번에 작성
						$("<form>",{
									method:"POST"
								   ,action:"${contextPath}/delete.bo"
						  }).append($("<input>",{
									  type:"hidden"
									 ,name:"boardNo"
									 ,value:"${b.boardNo}"
						  })).appendTo("body").submit();
					}
					
				});
				
			});
		</script>
		
		
		
		<br><br>
	</div>
</body>
</html>