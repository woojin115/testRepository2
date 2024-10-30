<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String errorMsg = (String)request.getAttribute("errorMsg");
%>    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#er {
		color:red;
	}
</style>

</head>
<body>	
	<%@ include file="/views/common/menubar.jsp" %>
	<h1 id="er" align="center"><%=errorMsg %></h1>
</body>
</html>