<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <title>欢迎：${ msg }登陆</title>
    </head>
    <body>
           <h3><font color="red"> ${ msg }</font></h3><br/>
           <tr style="position:fixed;">
               <td align="center">用户id</td>&nbsp;&nbsp;&nbsp;&nbsp;|
               <td align="center">用户名</td>&nbsp;&nbsp;&nbsp;&nbsp;|
               <td align="center">密码</td>&nbsp;&nbsp;&nbsp;&nbsp;|
               <td align="center">年龄<tr/>&nbsp;&nbsp;&nbsp;&nbsp;|
               <td align="center"><a href="${ pageContext.request.contextPath }/toregist.action"><font color="green">添加</font></a></td>
           </tr><br/>
           <c:forEach items="${ list }" var="user">
               <br/>
               <tr>
                   <td align="center">${user.sid}</td>&nbsp;&nbsp;|
                   <td align="center">${user.username}</td>&nbsp;&nbsp;|
                   <td align="center">${user.password}</td>&nbsp;&nbsp;|
                   <td align="center">${user.age}</td>&nbsp;&nbsp;|
                    <td align="center">
                        <form id ="${user.sid}" method ="post" action ="${ pageContext.request.contextPath }/delete.action">
                            <input type="hidden" name="uid" value="${user.sid}"/>
                            <input style="color: red" type ="submit" value ="删除"/>
                        </form>
                    </td>
                    <td align="center">
                        <form id ="${user.sid}" method ="post" action ="${ pageContext.request.contextPath }/toedite.action">
                            <input type="hidden" name="uid" value="${user.sid}"/>
                            <input type ="submit" value ="修改"/>
                        </form>
                    </td>
               </tr>
           </c:forEach>
    </body>
</html>
