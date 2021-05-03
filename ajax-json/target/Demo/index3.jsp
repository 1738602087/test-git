<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax-demo2</title>
</head>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.js"></script>
<script>
    /*最外面的这个 $(function(){}也就是这个页面加载完成后，指定的函数执行。*/
    $(function(){
        /*这里我们首先将这个DOM对象转换成jquery对象，之后我们才能够调用这个jquery中的这个
        click事件，接着就会执行我们的这个click事件中的函数体。在这个函数内部我们向服务器后端代码发送
        Ajax异步请求，之后我们ajax请求成功后的数据就会返回到我们的这个回调函数的函数体中我们可以继续对
        数据进行操作，这里data是封装这个服务器端返回的数据，这个status是我们请求的状态是成功还是失败，
        我们一般看到的都是成功，这里注意我们异步请求的状态不是封装在data参数里面的。拿到后端返回的数据我们
        就需要把这个后台返回的数据动态的添加到我们的这个tbody里面。
        服务器后端代码发送一个ajax异步请求，这里我们采用的请求方式为post，第一个参数为
        这个后端的这个某一个控制器中的方法，第二个参数是一个回调函数，用来表明我们的ajax异步
        请求成功后所执行的这个回调函数，
        对于dom对象-----jquery对象   $(dom)
        对于jquery对象----dom对象    jquery对象[0] 或  jquery.get(0)
        */
        $("#btn").click(function(){
            alert(111111);
            $.post("${pageContext.request.contextPath}/json/j4",function(data,status){
                debugger;
                alert(data);
                console.log(data);
                console.log(status);
                /*1：json有两种格式，json对象和json数组
                     (1):json对象：{key:value,key:value, ........};
                     (2)：json数组:[value1,value2,.........]
                * 2：json对象解析方式 ： 对象.key ;
                *    json数组的解析方式: for循环遍历
                * 3：java对象转换为json
                      (1):Bean和map->json对象
                      (2)：list->json数组*/
                var html1="";   //定义用于拼接的字符串
                /*对后台返回的数据进行遍历，因为我们后台这个请求所对应的方法的返回值是一个map集合，而集合
                java会将map集合转化为json对象，也就是json格式的第三种形式json对象，我们对这个json对象
                进行遍历取出这个json对象中所存放的每一个对象,因为这里我们的json对象中存放2个对象，所以最终
                我们循环遍历2次，得到每一个对象，这里我们的data是json对象，json对象中存放的属性还是对象，
                所以是对象的嵌套格式即对象中嵌套对象，所以我们获取最外层每一个对象通过这个object[key],
                的形式获取到每一个数据，注意这里不是object.key，因为我们这个json对象嵌套json对象实质上就
                类似于一个嵌套的对象数组。
                ，而这个每一个data[i]又是一个json对象，所以我们通过这个object.key
                的形式获取到每一个对象的属性值。之后我们就进行这个表格数据的拼接，最后一步也就是我们把这个
                拼接好的表格数据填充到我们的这个table表格的tbody中，通过id*/
                /*js循环遍历对象*/
                for(var i in data) {
                    //1 : {username: "王五", age: 23, sex: "男"}
                    console.log(i,":",data[i]);// 2 : {username: "赵六", age: 18, sex: "男"}
                    console.log(i,":",data.i);//1 : undefined 2 : undefined
                     html1 += "<tr>"+
                                    "<td>"+data[i].username+"</td>" +
                                    "<td>"+data[i].age+"</td>"+
                                    "<td>"+data[i].sex+"</td>"+
                                 "</tr>"

                }

                /*追加到table表格的tbody中进行数据展示*/
                $("#content").html(html1);
            })
        })
    });
</script>
<body>
<input type="button" id="btn" value="获取数据">
<table width="80%" border="10px" align="center">
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>性别</td>
    </tr>
    <tbody id="content">
    </tbody>

</table>

</body>
</html>
