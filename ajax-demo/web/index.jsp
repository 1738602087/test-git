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
</head>
<body>
	<script type="text/javascript">
		/*这里的js对象student有这个lastname，age，car和infos属性
		* 这个lastName属性表示的是姓名，它所对应的值是一个字符串，age属性
		* 表示的是年龄，它所对应的值是一个整型，car属性表示汽车，它所对应的值是
		* 一个对象类型，所以我们使用这个{}花括号表示，汽车对象有两个属性，第一个属性pp
		* 表示品牌，第二个属性price表示价格， infos属性表示说明信息，这里它是一个
		* 数组类型，数组中的第一个元素是一个对象类型，第二个元素是整型，
		* 第三个元素是Boolean类型*/
		//一个复杂的js对象，这个js对象key没有加双引号
		var student = {
			lastName : "张三",
			age : 18,
			car : {
				pp : "宝马",
				price : "30000$"
			},
			infos : [ {
				bookName : "西游记",
				price : 98.98
			}, 18, true ]
		};
		//js对象；属性操作特别方便  ，所以我们就可以直接通过这个对象名.属性名取出所对应的属性值。
		//alert(student.car.pp);//宝马
		/*这里我们想要取出这个students对象中的car属性中的这个宝马，我们思考一下怎么做
		*   我们首先通过这个student.car得到这个student对象的car属性，这个属性名所对应的值
		*   是一个对象类型，即我们通过这个student.car得到的是一个对象，而宝马也就是这个对象的
		*   pp属性名所对应的属性值，所以我么就是直接点.student.car.pp（对象名.属性名）*/

		//alert(student.infos[2]);//true
		/*这里我们想要取出这个students对象中的infos属性中的这个true，我们思考一下怎么做
		*   我们首先通过这个student.info得到这个student对象的info属性，这个属性名所对应的值
		*   是一个数组类型，而数组的索引是从0开始，而我们要得到的ture属性所对应的是数组的第
		*   三个元素，它的索引值为2，所以我们就是直接这个student.infos[2]*/

		//alert(student); [object,Object]
		/*通过typeof运算符可以分辨变量值属于哪种基本数据
		*类型 alert(typeof(变量名))或者alert(typeof 变量名)*/
        //alert(typeof(student)); //object
		
		//json的要求是和js对象是一样的，只不过key必须是字符串
		//js对象在声明的时候key是否加双引号是可以选择(加不加都可以)；

		/*这里student2仍是一个js对象，虽然key加上了双引号，而不是json字符串，*/
        //一个简单的js对象，这个js对象key加双引号
		var student2 = {
				"lastName" : "张三",
				"age" : 18
			};
		//json字符串
        var student3 = '{"lastName" : "张三","age" : 18}';
        //alert(typeof student3); //string
		console.log(student3); //{"lastName" : "张三","age" : 18} json字符串，控制台没有灰色小三角

		//alert(typeof student2); //object
        console.log(student2); //{lastName: "张三", age: 18} js对象，控制台前面有一个灰色小三角
		//JSON(js内置的对象)；将js对象转为json（应该是js对象字符串表示法）字符串
		var strJSon = JSON.stringify(student2);
		//alert(typeof strJSon); //String，JSON字符串
        console.log(strJSon);  //{"lastName":"张三","age":18} json字符串，控制台没有灰色小三角
	</script>
	<%=new Date()%>
	<a href="getinfo" id="aBtn">获取信息</a>
	<div id="haha">
		显示信息：
			${msg }
	</div>
</body>
<script type="text/javascript">
	/*1、$.get  */
	$("#aBtn").click(function(){
		//$.get(url, [data], [callback], [type])
		//[]代表这个参数可以不用传递
		//data：传递的数据："k=b&k=v"   传递一个js对象：会自动转为k=v&k=v的形式 
		//callback：定义一个回调函数，随便定义一个参数，这个参数就封装了服务器返回的数据
		//[type]：返回的数据类型；可以不写，自动判断；
		//type:返回内容格式，xml, html, script, json, text, _default。
		$.get("${ctp}/getinfo",{lastName:"长萨",age:18},function(abc){
			//abc代表服务器给我们的数据，如果服务器转发到一个页面，data代表整个页面；
			//alert(abc);
			$("#haha").append(abc);
		});
		return false;
	});
</script>
</html>