<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <body>
        <form action="${ pageContext.request.contextPath }/edite.action" method="post">
            <table border="1" width="400">
                <tr>
                     <td>用户id</td>
                     <td><input readonly type="text" name="sid" value="${employee.sid}"/></td>
                 </tr>
                 <tr>
                     <td>用户名</td>
                     <td><input type="text" name="username" value="${employee.username}"/></td>
                 </tr>
                 <tr>
                     <td>密码</td>
                     <td><input type="password" name="password" value="${employee.password}"/></td>
                 </tr>
                 <tr>
                     <td>年龄</td>
                     <td><input type="text" name="age" value="${employee.age}"/></td>
                 </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="修改"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
