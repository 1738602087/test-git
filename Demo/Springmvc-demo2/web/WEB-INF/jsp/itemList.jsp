<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>查询商品列表</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
</head>
<body> 
<form action="${pageContext.request.contextPath }/queryitem.action" method="post">
	查询条件：
<table width="100%" border=1>
<tr>
	<td>商品id: <input type="text" name="items.id"></td>
	<td>商品名称：<input type="text" name="items.name"></td>
    <td><input type="submit" value="查询"/></td>
</tr>
</table>
商品列表：
<table width="100%" border=1>
<tr>
	<td>选择</td>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>生产日期</td>
	<td>商品描述</td>
	<td>操作</td>
</tr>
<c:forEach items="${itemList}" var="item" varStatus="s">
<tr>
	<td><input type="checkbox" name="ids" value="${item.id }"></td>

	<td>
		<input type="hidden" name="[itemList]${s.index}.id" value="${item.id }">
		<input type="text" name="[itemList]${s.index}.name" value="${item.name }">
	</td>
	<td><input type="text" name="[itemList]${s.index}.price" value="${item.price }"></td>
	<td><input type="text" name="[itemList]${s.index}.createtime" value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"></td>
	<td><input type="text"  name="[itemList]${s.index}.detail" value="${item.detail }"></td>
	
	<%--<td><a href="${pageContext.request.contextPath }/itemEdit.action?id=${item.id}">修改</a></td>--%>
    <%-- 使用这个restful风格。--%>
	<td><a href="${pageContext.request.contextPath }/itemEdit/${item.id}">修改</a></td>

</tr>
</c:forEach>

</table>
</form>
<%--这个button按钮里面我们要想要让它有动作，我们需要将这个按钮上绑定动作，这个方法需要在script
标签中进行定义一下，$.ajax({}})，这个括号里面的都是这个ajax的参数，这里的这个URL表示的是我们要发送给谁
的请求，这里的这个data也就是我们的这个json数据，这里的这个json数据格式是多个无序的数据:对象
这个succees:function（data）表示的是一旦成功就会去调用这个方法，这个data也就是我们的服务端响应
的数据，最后如果是json数据，我们可以直接取出数据的值

我们一旦点击这个按钮，我们就触发这个方法，接着执行方法的内部内容，也就是我们在方法内部做一个ajax
请求，我们向一个特定的URL发送一些数据，type表示我们的提交方式，contenttype是数据的格式，data是
数据的内容，请求成功以后，服务器端给我们响应一个json，这个json也就是我们所提交的这个 json，
然后我们把这个json中的数据取出来进行打印。
--%>
<button onclick="sendJson()">Json数据测试</button>
<script type="text/javascript">
    //请求json响应json
    function sendJson(){
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath }/jsonTest.action",
            data:'{"id":"1","name":"电冰箱","price":"999"}',
            contentType:"application/json;charset=utf-8",
            success:function(data){
                alert(data.id+":"+data.name);
            }
        });
    }
</script>

</body>

</html>