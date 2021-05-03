<%--
  Created by IntelliJ IDEA.
  User: 李向阳
  Date: 2019/8/4
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/user/login" method="post">
    <label>用户名</label>
    <input type="text" name="username">
    <br>
    <label>密 码</label>
    <input type="text" name="password">
    <input type="submit">
    <br>
</form>

</body>
</html>
