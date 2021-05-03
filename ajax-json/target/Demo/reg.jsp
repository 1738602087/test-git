<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax验证用户名可用</title>
</head>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
    /*这里的这个我们验证这个用户名和密码是否可用是通过一个controller控制器类中的这个
    * "/ajax/a3"这个方法实现的，这里我们的这个*/
    function a1(){
        $.ajax({
            url:"${pageContext.request.contextPath}/ajax/a3",
            type : "post",
           /* data:{"name":$("#name").val()},这个写法也是可以的。所以我们要注意我们
           * 这个属性名加不加双引号或者单引号，ajax都是可以帮助我们进行解析。*/
            data:{name:$("#name").val()},
            success:function(data){
                console.log(data);
               if(data.toString()=="ok"){//信息核对成功
                   $("#userInfo").css("color","green");
               }else {
                   $("#userInfo").css("color","red");
               }
               $("#userInfo").html(data);
            }

        })
    }
    function a2(){
        $.ajax({
            url:"${pageContext.request.contextPath}/ajax/a3",
            data:{"pwd":$("#pwd").val()},
            success:function(data){
                console.log(data);
                if(data.toString()=="ok"){//信息核对成功
                    $("#pwdInfo").css("color","green");
                }else {
                    $("#pwdInfo").css("color","red");
                }
                $("#pwdInfo").html(data);
            }

        })
    }
</script>
<body>
  <p>
      用户名：
      <input type="text" id="name" onblur="a1()"/>
      <span id="userInfo"></span>
      <%--这里我们添加的这个span标签也就是这个隐藏的东西提示这个用户登录成功还是失败，下面的也是类似--%>
  </p>
  <p>
      密码：
      <input type="text" id="pwd" onblur="a2()"/>
      <span id="pwdInfo"></span>
  </p>

</body>
</html>
