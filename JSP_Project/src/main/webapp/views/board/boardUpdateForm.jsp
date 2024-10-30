<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#update-area input,#update-area textarea{
		width : 100%;
		box-sizing: border-box;
	}

</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시글 수정</h2>
		<br>
		
		<script>
			//기존에 선택했던 카테고리 정보 선택되어 있을 수 있게 처리 
			
			$(function(){
				//게시글 정보인 b 에서 가져온 카테고리명과 지금 select에 option으로 등록되어있는 
				//목록중 일치하는것을 찾아 선택시키기
				
				
				var cName = "${b.categoryNo}"; //선택되어있던 카테고리명
				
				//옵션들의 텍스트 중에서 cName과 일치한 텍스트요소를 선택해놓을 수 있도록 처리 
				//옵션태그 찾기 
				
				//console.log($("#update-area option"));
				//옵션태그 각 요소를 순차적으로 순회하여 접근해서 
				//텍스트 요소가 cName과 일치하는지 확인 
				//each() 메소드
				
				$("#update-area option").each(function(){
					//$(this) 는 각 요소(option태그)
					//option태그의 text 요소와 cName 비교 
					if($(this).text() == cName){ //같다면 (찾았다면)
						//해당 옵션 선택되어질 수 있도록 속성 처리 
						$(this).attr("selected",true);
						//요소를 찾아 처리했으면 나머지 순회는 필요없다. 벗어나기
						return false; //단순 반복문 아니기때문에 break; 아니고 return false로 함수벗어나기
					}
				});
				
			});
		
		</script>
		
		
		
		<form action="${contextPath }/update.bo" method="post" id="update-area" enctype="multipart/form-data">
			<!-- 게시글 번호 요청시 전달 같이하기 -->
			<input type="hidden" name="boardNo" value="${b.boardNo}">
			
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
					<th width="350"><input type="text" name="title" value="${b.boardTitle }"  required></th>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan='3'>
						<textarea name="content" rows='10' style='resize:none' required>${b.boardContent }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
						<!-- 기존 첨부파일이 있었다면 정보 보여주기 -->
						<c:if test="${at != null}">
							${at.originName}
							<!-- 게시글에 첨부파일이 있었던 경우 해당 첨부파일 정보를 등록한 DB에 있는 정보에서 수정을 해야한다.
								 때문에 해당 데이터 식별자용으로 fileNo를 전달하고 서버에 업로드된 파일을 삭제하기 위해 
								 changeName도 전달하기 
							 -->
							 <input type="hidden" name="originFileNo" value="${at.fileNo}">
							 <input type="hidden" name="originFileName" value="${at.changeName}">
						</c:if>
						<input type="file" name="reUploadFile">
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