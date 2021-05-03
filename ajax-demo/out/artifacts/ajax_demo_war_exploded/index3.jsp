<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String token = UUID.randomUUID().toString();
	
	session.setAttribute("to", token);

%>
<form action="TokenServlet.jsp" method="post">
	<input name="username" value="aaaaa"/>
	<input name="token" value="<%=token%>">
	<input type="submit"/>
</form>
</body>
</html>