<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<%
	pageContext.setAttribute("ctp", request.getContextPath());
%>
<style type="text/css">
div {
	border: 1px solid #BBB;
	float: left;
	margin-left: 20px;
	width: 200px;
	height: 180px;
}
</style>
</head>

<body>
	<!--  -->

	<div>
		div1：<%=new Date()%>
	</div>
	<div>
		div2：<%=new Date()%>
	</div>
	<div>
		div3：<%=new Date()%><br/>
	</div>
	<br/>
	<hr/>
	<a id="aBtn" href="getstuinfo">获取学生数据</a><br/>
	<a id="aBtnAjax" href="getstuinfo">获取学生数据-使用底层的ajax方法</a>
</body>
<script type="text/javascript">
	//url：请求地址
	//以下参数可选
	//data：请求发送的数据，可以写k=v&k=v  也可以写js对象（自动转）
	//callback：回调函数；响应成功以后的回调函数
	//type：指定返回的数据类型，我们jquery可以帮我们自动转为指定的这个类型

	//js中可以使用"el表达式"来取出el表达式的值。注意这个el表达式的值我们需要写在这个双引号内
	/*${ctp}错误，"${ctp}".是正确的。*/
	$("#aBtn").click(function() {
		/* $.get("${ctp}/getstuinfo", {
			lastName : "lisi",
			age : 18
		}); */
		//$.get("${ctp}/getstuinfo","lastName='admin'&age=18");
		//post和get的用法是一样的，只是一个发的是post请求，一个是get请求
		$.post("${ctp}/getstuinfo","lastName=admin&age=18",function(data){
			//alert(typeof data);
			//alert(data.lastName);
			
			//第一种：将返回的字符串转为json对象/js对象或者我们在服务器端指定返回json
			//var obj = JSON.parse(data);
			//alert(obj.lastName);
			
			//第二种：type最后一个参数直接指定是json。jquery自动转为json对象
			alert(data.lastName);
		},"json");
		//
		//$.post()
		return false;
	});
	
	
	//一定要掌握
	/*这里我们首先将这个DOM对象转换成jquery对象，之后我们才能够调用这个jquery中的这个
        click事件，接着就会执行我们的这个click事件中的函数体。在这个函数内部我们向服务器
        后端代码发送Ajax异步请求。
        对于dom对象-----jquery对象   $(dom)
        对于jquery对象----dom对象    jquery对象[0] 或  jquery.get(0)
      */
	$("#aBtnAjax").click(function(){
		//发送ajax请求
		//所有请求的属性参数都是可以通过这个js对象定义的；
		
		var options = {
				url:"${ctp}/getstuinfo",//规定请求地址
				type:"GET",//请求方式
				data:{"lastName":"haha",age:22},//发送的数据
				async:true,//调整异步（true）/同步（false）
				success:function(data){
					
					//alert("成功！"+(typeof data)); //
					//把收到的数据放在第三个div中
					var lastName = data.lastName;
					var age = data.age;
					$("div:eq(2)").append("姓名:"+lastName+"<br/>年龄:"+age);
					$("div:eq(2)").css("background-color", "#bbb")
				},
				error:function(a,b){
					alert("请求失败了，"+b+"；状态码："+a.status);	
				},
				dataType:"json"
				
		};
		$.ajax(options);
		
		
		//默认ajax是异步的；数据的接受和下面方法的执行，不冲突；
		//异步：不用等待整个ajax请求完成才执行下面的方法  弹出这个哈哈和这个页面从服务器获得数据填充同时进行
		//同步：等待ajax整个完成，才会执行后面的方法    首先这个页面填充完数据之后才会弹出这个哈哈。
		alert("哈哈");
		//禁用默认行为，因为如果说我们不禁用这个默认行为这个上面定义好的超链接就会
		// 重新发送一次请求，所以说我们点击事件触发的js函数中的函数体发送了一次ajax
		// 请求，请求我们的这个服务器端getstuinfo这个路径的servlet类。
		// 而我们从服务器端重新获取数据页面跳转也会执行这个服务器端
		// getstuinfo这个路径的servlet类，重新发送一次请求。
		return false;
	});
	
	
</script>
</html>