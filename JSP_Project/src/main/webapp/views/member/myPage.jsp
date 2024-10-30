<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
#mypage-form table {
	margin: auto;
}

#mypage-form input {
	margin: 5px;
}
</style>

</head>
<body>
	<%@ include file="/views/common/menubar.jsp"%>

	<%
	//로그인 되어있는 회원 정보 session에 담겨있음 
	//해당 정보 추출하여 각 인풋요소에 담아서 보여주기
	//session영역에 담아둔 loginUser 정보 추출 
	//Member loginUser = (Member)session.getAttribute("loginUser");
	//menubar에 이미 loginUser 변수를 선언해놨기때문에 중복 오류 발생 
	//menubar include된 아래에서 작성하면 기존 변수 활용 가능 

	String userId = loginUser.getUserId();
	String userName = loginUser.getUserName();
	//비밀번호 추출
	String userPwd = loginUser.getUserPwd();

	//나머지 데이터는 필수 입력요소가 아니기 때문에 null값이 담겨있을 수 있고 해당 처리 필요
	//삼항연산자 이용하여 null값을 "" 빈문자열로 치환하기
	String phone = (loginUser.getPhone() == null) ? "" : loginUser.getPhone();
	String email = (loginUser.getEmail() == null) ? "" : loginUser.getEmail();
	String address = (loginUser.getAddress() == null) ? "" : loginUser.getAddress();
	String interest = (loginUser.getInterest() == null) ? "" : loginUser.getInterest();
	%>

	<div class="outer">
		<br>
		<h2 align="center">마이 페이지</h2>
		<form action="<%=contextPath%>/update.me" id="mypage-form"
			method="post">
			<table>
				<tr>
					<td>* 아이디</td>
					<td><input type="text" name="userId" value="<%=userId%>"
						readonly></td>
					<td></td>
				</tr>
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="userName" value="<%=userName%>"
						maxlength="6" required></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;전화번호</td>
					<td><input type="text" name="phone" value="<%=phone%>">
					</td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;이메일</td>
					<td><input type="email" name="email" value="<%=email%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;주소</td>
					<td><input type="text" name="address" value="<%=address%>"></td>
					<td></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;관심분야</td>
					<td colspan="2">
						<!-- (input[type=checkbox name=interest id= value=]+label)*6 --> <input
						type="checkbox" name="interest" id="sports" value="운동"> <label
						for="sports">운동</label> <input type="checkbox" name="interest"
						id="hiking" value="등산"> <label for="hiking">등산</label> <input
						type="checkbox" name="interest" id="fishing" value="낚시"> <label
						for="fishing">낚시</label> <br> <input type="checkbox"
						name="interest" id="cooking" value="요리"> <label
						for="cooking">요리</label> <input type="checkbox" name="interest"
						id="game" value="게임"> <label for="game">게임</label> <input
						type="checkbox" name="interest" id="movie" value="영화"> <label
						for="movie">영화</label>
					</td>
				</tr>
			</table>
			<br>
			<br>

			<script>
				//제이쿼리 구문을 이용하여 checkbox를 가지고 있는 interest 데이터 대로 
				//체크되어 있을 수 있도록 처리해보기
				$(function(){
					//자바스크립트 변수로 데이터 담아두기
					var interest = "<%=interest%>";//"게임,영화"

					//split 메소드 이용하여 배열로 만들어주기
					var interestArr = interest.split(",");

					//배열.indexOf("대상") : 대상이 있는 인덱스위치반환 없으면 -1반환

					//interest 체크박스의 value를 추출하여 indexOf에 넣어서 판별해보기
					//모든 interest 체크박스 순회하며 value 확인해야함.
					//each메소드 이용하여 순차적 접근 
					$("input[name=interest]").each(function() {
						//접근된 요소 : $(this) 
						//접근요소의 value값이 배열안에 포함되어있다면 
						if (interestArr.indexOf($(this).val()) != -1) {
							//해당 요소 체크시켜놓기 
							$(this).attr("checked", true);
						}

					});

				});
			</script>


			<div align="center">
				<button type="submit" class="btn btn-info">정보변경</button>
				<!-- 비밀번호 변경/회원탈퇴는 모달창 이용해서 처리해볼것 -->
				<button type="button" data-toggle="modal" data-target="#updatePwd" class="btn btn-success" >비밀번호 변경</button>
				<button type="button" data-toggle="modal" data-target="#deleteMember" class="btn btn-danger">회원탈퇴</button>
			</div>
		</form>
		<br>
		<br>
		<br>
		<br>
		<br>
	</div>


	<!-- 비밀번호 변경용 모달 영역 -->
	<div class="modal" id="updatePwd">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">비밀번호 변경</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				
				<!-- 요청 매핑 주소 : updatePwd.me
					 컨트롤러명 : UpdatePwdController
					 메소드명 : updatePwd() 
					 변경할 비밀번호와 비밀번호 확인이 일치해야 서버에 전송될 수 있도록 함수 처리 
					 일치하지 않으면 변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다 alert메세지 내보내고 전송 막기 
					 변경해야할 회원의 식별자가 있어야 변경 가능하니 회원의 아이디값을 이용하여 특정 회원 비밀번호 수정할 수 있도록 처리
					 아이디값 추출하는 2가지 방법
					 1번 : form안에 숨겨서 보내기 (type=hidden) 이용 
					 2번 : servlet에서 session에 있는 로그인 정보중 아이디 추출하기
					 
					 성공시 비밀번호 변경 완료 메세지를 세션에 담고 마이페이지 재요청
					 실패시 비밀번호 변경 실패 메세지를 request에 담고 에러 페이지로 위임요청
				
				 -->
				
				
				<!-- Modal body -->
				<div class="modal-body" align="center">
					<form action="<%=contextPath %>/updatePwd.me" method="post">
						
						<!-- 회원의 아이디를 전송하기 (식별자 용도로 조건에 추가할것) -->
						<!-- 사용자 입장에선 보이지 않도록 input type을 hidden으로 처리할것 -->
						
						<input type="hidden" name="userId" value="<%=userId %>">
						
						<table>
							<tr>
								<td>현재 비밀번호</td>
								<td><input type="password" name="userPwd" required> </td>
							</tr>
							
							<tr>
								<td>변경할 비밀번호</td>
								<td><input type="password" name="updatePwd" required> </td>
							</tr>
							
							<tr>
								<td>변경할 비밀번호 확인</td>
								<td><input type="password" id="pwdCheck" required> </td>
							</tr>
						</table>
						
						<br>
						<button type="submit" onclick="return chkPwd();" class="btn btn-secondary">비밀번호 변경</button>
					</form>
				</div>
				<script>
					function chkPwd(){
						//변경할 비밀번호와 비밀번호 확인 일치하는지 확인작업 
						var updatePwd = $("input[name=updatePwd]").val();
						var pwdCheck = $("#pwdCheck").val();
						
						//console.log(updatePwd,pwdCheck);
						
						//비교후 두 값이 다르면 fasle 를 이용하여 기본 이벤트 막아주기
						
						if(updatePwd != pwdCheck){
							alert("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
							return false;
						}
						
					}
				
				</script>
				

			</div>
		</div>
	</div>


	<!-- 
		회원 탈퇴용 모달영역 만들어서 연결하고 
		모달영역에서는 현재 비밀번호 입력받아 로그인되어 있는 회원 비밀번호와 비교하여 
		일치한다면 정말 탈퇴하시겠습니까? 라는 confirm 창을 띄워 확인 버튼 누르면 
		서버에 전송해서 해당 회원의 status 상태값을 N 으로 변경하는 작업 하기 
		성공시 : 로그인 해제 후 세션에 그동안 사이트를 이용해주셔서 감사합니다. 메세지를 담고(alert) 메인페이지로 돌아오기 (재요청)
		실패시 : 회원 탈퇴 실패 메세지를 세션에 담고 마이페이지로 돌아오기(재요청)
		
		매핑주소 : delete.me
		메소드명 deleteMember
		컨트롤러명 MemberDeleteController
		함수명 : deleteCheck
		
	
	 -->
	


	<!-- 회원탈퇴용 모달 영역 -->
	<div class="modal" id="deleteMember">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">회원탈퇴</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				<!-- Modal body -->
				<div class="modal-body" align="center">
					<form action="<%=contextPath%>/delete.me" method="post">
						<!-- 식별자로 사용할 아이디 숨겨서 전달하기 -->
						<input type="hidden" name="userId" value="<%=userId %>">
						<table>
							<tr>
								<td>현재 비밀번호</td>
								<td><input type="password" id="delPwd" name="userPwd" required> </td>
							</tr>
						</table>
						
						<br>
						<button type="submit" onclick="return deleteCheck();" class="btn btn-danger">회원 탈퇴</button>
					</form>
				</div>
				<script>
					function deleteCheck(){
						
						//사용자가 입력한 현재 비밀번호 추출 
						var delPwd = $("#delPwd").val();
						//현재 로그인 되어있는 회원의 비밀번호 추출 
						var userPwd = "<%=userPwd%>";
						
						if(delPwd == userPwd){//입력한 비밀번호와 로그인 비밀번호 같다면 
							
							//confirm 결과는 확인 : true / 취소 : false 
							//var flag = confirm("정말 탈퇴하시겠습니까? 탈퇴후 복구는 불가능합니다.");
							//return flag;
							
							return confirm("정말 탈퇴하시겠습니까? 탈퇴후 복구는 불가능합니다.");
							
						}else{//비밀번호 잘못 입력했다면
							
							alert("비밀번호를 잘못 입력 하셨습니다.")
							return false;
						}
						
					}
					
				
				
				</script>
				

			</div>
		</div>
	</div>






</body>
</html>