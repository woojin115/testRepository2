<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

	#enroll-form table{
		margin:auto;
	}
	#enroll-form input{
		margin:5px;
	}
</style>

</head>
<body>
	<!-- 메뉴바 가져오기(절대경로방식) -->
	<%-- <%@ include file="/views/common/menubar.jsp" %> --%>
	<!-- 메뉴바 가져오기(상대경로방식):현재 파일 위치 기준으로 찾기
		../ : 현재 폴더로부터 상위폴더로 한번 이동
	-->
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">회원가입</h2>
		<!-- 위에 menubar를 include해오고 해당 페이지에 선언되어있는 변수는 사용 가능하다 -->
		<form action="<%=contextPath%>/insert.me" id="enroll-form" method="post">
			<!--아이디,비밀번호,이름,전화번호,이메일,주소,취미-->
			<table>
				<!-- (tr>td*3)*8 -->
				<tr>
					<td>* 아이디</td>
					<td><input type="text" name="userId" id="userId" maxlength="12" required></td>
					<td><button type="button" onclick="idCheck();">중복확인</button></td><!-- ajax 배우고 진행-->
					<!-- 
						비동기 통신을 이용하여 버튼을 눌렀을 때 아이디 입력란에 작성된 아이디를 
						서버에 전달 및 요청하여 아이디 중복확인을 하고 오기 
						매핑주소 : idCheck.me
						컨트롤러 : MemberIdCheckController
						메소드명 : idCheck()
						버튼을 눌렀을때 요청 후 응답데이터를 통해 사용가능하면 사용가능한 아이디입니다 사용하시겠습니까? 
						메세지로 확인 취소 작업(confirm이용)
						확인(사용하겠다)시에 input id 작성란을 readonly 속성을 넣어 확정해주기
						또한 기존에 아이디 중복체크가 되지 않은 상황에서는 회원가입 버튼 비활성화(disabled) 
						중복체크 후 활성화하여 회원가입 처리 가능하도록 구현하기.
						
						응답데이터는 네이버처럼 사용 가능 NNNNY / 사용불가 NNNNN 으로 처리하기 
					 -->
					
					
				</tr>
				<tr>
					<td>* 비밀번호</td>
					<td><input type="password" name="userPwd" id="pwd" maxlength="15" required></td>
					<td></td>
				</tr>
				<tr>
					<td>* 비밀번호 확인</td>
					<td><input type="password" id="chkPwd" maxlength="15" required> </td> <!--단순 비교 용도이기 때문에 name 속성 필요없음-->
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" maxlength="6" required> </td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" placeholder="-포함해서 입력"> </td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;관심분야</td>
					<td colspan="2">
						<!-- (input[type=checkbox name=interest id= value=]+label)*6 -->
						<input type="checkbox" name="interest" id="sports" value="운동">
						<label for="sports">운동</label>

						<input type="checkbox" name="interest" id="hiking" value="등산">
						<label for="hiking">등산</label>

						<input type="checkbox" name="interest" id="fishing" value="낚시">
						<label for="fishing">낚시</label>
						<br>
						<input type="checkbox" name="interest" id="cooking" value="요리">
						<label for="cooking">요리</label>

						<input type="checkbox" name="interest" id="game" value="게임">
						<label for="game">게임</label>

						<input type="checkbox" name="interest" id="movie" value="영화">
						<label for="movie">영화</label>
					</td>
				</tr>
			</table>
			<br><br>

			<div align="center">
				<button type="submit" onclick="return pwdCheck();" disabled>회원가입</button>
				<button type="reset">초기화</button>
			</div>
		</form>

	</div>
	
	<script>
		function pwdCheck(){
			
			var userPwd = document.querySelector("#pwd");
			var checkPwd = document.querySelector("#chkPwd");
			
			
			console.log(userPwd.value);
			console.log(checkPwd.value);
			
			if(userPwd.value != checkPwd.value){
				alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				userPwd.focus();
				return false;
			}
		}
		
		//ajax 이용한 아이디 중복체크
		function idCheck(){
			
			//아이디 입력 요소 잡기 
			var inputId = $("#userId");
			
			
			$.ajax({
				url : "${contextPath}/idCheck.me", 
				data : {
					inputId : inputId.val() //전달 데이터
				},
				success : function(result){//결과값 매개변수로 받아주기
					//result : NNNNN / NNNNY
					
					if(result=='NNNNN'){//사용불가
						alert("이미 존재하는 아이디입니다.")
					}else if(result=='NNNNY'){//사용가능
						if(confirm("사용가능합니다. 정말 사용하시겠습니까?")){ //사용하겠다(확정)
							/*
							확인(사용하겠다)시에 input id 작성란을 readonly 속성을 넣어 확정해주기
							또한 기존에 아이디 중복체크가 되지 않은 상황에서는 회원가입 버튼 비활성화(disabled) 
							중복체크 후 활성화하여 회원가입 처리 가능하도록 구현하기.
							*/
							$("#enroll-form :submit").removeAttr("disabled");//비활성화 제거
							inputId.attr("readonly",true);//아이디 수정 못하도록 읽기전용으로 확정
							
						}else{//사용 안함 
							inputId.focus();//다시 작성 유도
						}
					}
					
				},
				error : function(){
					console.log("통신 실패");
				}
			});		
			
			
			
		}
		
		
		
		
	</script>
	
	
	
	
</body>
</html>