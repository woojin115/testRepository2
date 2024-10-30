<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	//스크립틀릿 영역에서 데이터 추출하기 
	//컨텍스트 패스(루트) 추출하기 
	String contextPath = request.getContextPath();
	
	//로그인 유저 정보 추출하기 
	Member loginUser = (Member)session.getAttribute("loginUser");
	//loginUser 에 담긴 데이터 
	//로그인 전 : null
	//로그인 후 : 로그인한 회원정보 담은 Member객체
	
	//알림메세지 추출하기
	String alertMsg = (String)session.getAttribute("alertMsg");
	//서비스요청 동작 전 : null
	//서비스요청 후 알림메세지 있을땐 : 메세지 문구 

%>    
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	
	<!-- 부트스트랩 cdn-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<style>
    #login-form,#user-info {
        float : right; /*로그인 폼 오른쪽 정렬*/
    }
    #user-info a {
        text-decoration: none; /*a 태그 밑줄 삭제*/
        color :black;
        font-size: 12px;
    }

    .nav-area{
        background-color: black;
    }
    .menu{
        display: inline-block; 
        height: 50px;
        width: 150px;
    }

    .menu a{
        text-decoration: none;
        color: white;
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px;
    }

    .menu a:hover{
        background-color: darkgray;
    }
    
    .outer{
    	background-color:black;
    	color : white;
    	width : 1000px;
    	margin : auto;
    	margin-top : 50px; 
    }
    
    
    
</style>


</head>
<body>
    <h1 align="center">Welcome JSP Project</h1>
    
    <script>
    	//script 태그 내에서도 자바코드 스크립틀릿 또는 표현식 이용 하여 처리 가능 
    	
    	var msg = "<%=alertMsg%>"; //로그인 성공 또는 null 
    	
    	//null이 아닐경우에 alert 띄워주기 
    	if(msg != "null"){
    		alert(msg);
    		
    		//알림창을 띄우고 메시지 지워주기 (안지우면 메뉴바 뜰때마다 알림창뜸)
    		<%session.removeAttribute("alertMsg");%>
    	}
    	
    	//회원가입 폼 이동 함수 
    	function enrollPage(){
    		//회원가입 폼으로 이동할 수 있도록 처리 
    		//location.href="<%=contextPath%>/views/member/memberEnrollForm.jsp";
    		//웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약하다
    		//허용하지 않는 페이지에 대한 접근을 막을 수 없음.
    		//단순한 정적 페이지 이동시에도 servlet을 거쳐서 서버에서 매핑시켜 처리할 수 있도록 할것.
    		location.href="<%=contextPath%>/enrollForm.me";
    		
    	}
    	
    	
    </script>
    
    

    <div class="login-area">

        <!--로그인 전에 보여지는 로그인 form-->
        <%if(loginUser == null) {%>
        <!-- 요청 매핑 주소 : login.me -->
        <form action="<%=contextPath%>/login.me" id="login-form" method="post">
            <!-- table>(tr>th+td)*3 -->
            <table>
                <tr>
                    <th>아이디 : </th>
                    <td><input type="text" name="userId"></td>
                </tr>
                <tr>
                    <th>비밀번호 : </th>
                    <td><input type="password" name="userPwd"></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </th>
                </tr>
            </table>
        </form>
		<%}else{ %>
        <!--로그인 성공 후 보여지는 화면-->
        <div id="user-info">
            <b><%=loginUser.getUserName()%>님</b> 환영합니다 . <br><br>
            <div align="center">
                <a href="<%=contextPath%>/mypage.me">마이페이지</a>
                <a href="<%=contextPath%>/logout.me">로그아웃</a>
            </div>
        </div>
		<%} %>

    </div> <!--로그인 관련 div 끝 -->

    <br clear="both"> <!-- float 속성 해제 -->
    <br>
    
    <!-- contextPath pageScope에 담아두기 -->
  	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <div class="nav-area" align="center">
        <!-- (div.menu>a)*4 -->
        <div class="menu"><a href="${contextPath}">HOME</a></div>
        <div class="menu"><a href="<%=contextPath%>/list.no">공지사항</a></div>
        <div class="menu"><a href="${contextPath}/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="${contextPath}/list.th">사진게시판</a></div>
    </div>

</body>
</html>