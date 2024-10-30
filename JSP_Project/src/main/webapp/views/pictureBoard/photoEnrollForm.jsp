<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	#enroll-form input,#enroll-form textarea{
		width:100%;
		box-sizing: border-box;
	}
	#enroll-form td>img{
		width : 100%;
		height: 100%;
	}

</style>


</head>
<body>
	<%@include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">사진 게시글 작성</h2>
		<br>
		
		<form action="${contextPath }/insert.th" method="post" id="enroll-form" enctype="multipart/form-data">
			<!-- 작성자 정보 요청시 전달 같이하기 -->
			<input type="hidden" name="userNo" value="${loginUser.userNo}">
			<table align="center">
				<tr>
					<th width="100">제목</th>
					<td colspan="3">
						<input type="text" name="title" required>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan='3'>
						<textarea name="content" rows='10' style='resize:none' required></textarea>
					</td>
				</tr>
				<tr>
					<th>대표이미지</th>
					<td colspan="3" align="center" width="250" height="170">
						<img id="titleImg">
					</td>
				</tr>
				
				<tr>
					<th>상세이미지</th>
					<td width="150" height="120">
						<img id="contentImg1">
					</td>
					<td width="150" height="120">
						<img id="contentImg2">
					</td>
					<td width="150" height="120">
						<img id="contentImg3">
					</td>
				</tr>
			</table>
			
			<br><br>
			
			<div id="file-area">
				<input type="file" id="file1" name="file1" onchange="loadImg(this,1);" required>
				<input type="file" id="file2" name="file2" onchange="loadImg(this,2);">
				<input type="file" id="file3" name="file3" onchange="loadImg(this,3);">
				<input type="file" id="file4" name="file4" onchange="loadImg(this,4);">
			</div>
			
			
			<div align="center">
				<button type="submit">등록하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</form>
		
			<script>
			
				$(function(){
					//이미지 영역 클릭했을때 input file 태그가 동작하도록 처리해보기
					$("#titleImg").click(function(){
						//대표이미지 영역이 클릭되었을때 input file1 태그 동작시키기 
						$("#file1").click();
					});
					$("#contentImg1").click(function(){
						//대표이미지 영역이 클릭되었을때 input file1 태그 동작시키기 
						$("#file2").click();
					});
					$("#contentImg2").click(function(){
						//대표이미지 영역이 클릭되었을때 input file1 태그 동작시키기 
						$("#file3").click();
					});
					$("#contentImg3").click(function(){
						//대표이미지 영역이 클릭되었을때 input file1 태그 동작시키기 
						$("#file4").click();
					});
					
					//input file 태그들 숨기기 
					$("#file-area").hide();
					
					
				});
			
			
			
			
			
				//onchage에서 동작할 함수 정의
				function loadImg(inputFile,num){
					//inputFile : 현재 이벤트가 발생한 요소(input type="file")
					//num : 해당 요소가 몇번째 요소인지 정보
					//inputFile.files : input file 에 현재 등록된 파일 정보를 확인할 수 있는 속성(배열형식의 fileList)
					//파일을 선택하면 0번 인덱스에 파일 정보가 담긴다. 이때 length는 1이 됨
					//파일을 선택했을땐 length가 1이고 선택하지 않았을때 또는 선택을 취소했을땐 0이기 때문에 
					//해당 조건으로 파일에 대한 처리 하기 
					
					if(inputFile.files.length == 1){ //파일이 등록됐을때
						//해당 파일의 정보를 읽어서 미리보기 영역에 보여주기 
						//파일 정보를 읽어줄 객체 FileReader() 준비 
						var reader = new FileReader();
						//FileReader 의 메소드중 readAsDataURL(파일) 이라는 메소드로 
						//파일을 읽어 해당 파일의 고유한 url을 읽어와서 사용한다.
						
						//우리가 등록한 파일 정보는 inputFile.files 속성의 0번 인덱스에 등록되어있음
						reader.readAsDataURL(inputFile.files[0]);
						
						
						//reader 객체가 해당 파일 정보를 읽어오는 시점 : onload 
						//reader 객체가 해당 파일을 읽어서 url 정보를 result 속성에서 받아온다.
						reader.onload = function(e){
							//처리된 이벤트의 결과를 받아보기
							//이벤트 정보 매개변수 e 
							//이벤트가 동작한 대상 : e.target
							//해당 이벤트가 발생한 대상의 결과 : e.target.result (생성된 고유 url이 담긴다.)
							//console.log(e.target.result);
							
							//읽어온 url 정보를 각 미리보기 영역에 src에 부여하기 
							
							switch(num){ //같이 전달했던 번호 이용하기 (input file 요소 번호)
								case 1 : $("#titleImg").attr("src",e.target.result); break;
								case 2 : $("#contentImg1").attr("src",e.target.result); break;
								case 3 : $("#contentImg2").attr("src",e.target.result); break;
								case 4 : $("#contentImg3").attr("src",e.target.result); break;
							}
						};
					}else{
						//선택된 파일이 사라졌을때 띄워놨던 이미지 지워주기 (src 비우기)
						switch(num){
							case 1 : $("#titleImg").removeAttr("src"); break;
							case 2 : $("#contentImg1").attr("src",null); break;
							case 3 : $("#contentImg2").attr("src",null); break;
							case 4 : $("#contentImg3").attr("src",null); break;
						}
					}
					
				}
			</script>
			
		
		
		<br><br>
	</div>
	
</body>
</html>