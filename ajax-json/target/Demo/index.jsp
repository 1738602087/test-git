<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajax第二个小Demo</title>
    <%--要使用jquery，首先我们需要导入这个js文件，这里因为我们是jsp，所以我们不能--%>
    <%--使用这种方式进行导入，<<script src="static/js/jquery-1.8.js"应该使用绝对路径></script>>--%>
    <%--加上我们的这个项目路径--%>

</head>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
    //所有参数
    //url:待载入页面的地址。json(怎么解析是由前端来的)
    //data：待发送的key/value参数(从前端页面获取到)
    //success：function 载入成功时的回调函数
          //(data服务器返回的数据
         //status：服务器响应的状态)
   function run1(){
       /*我们给我们的页面中的某一个文本框或者表格通过指定一个id属性之后我们在我们的HTML页面所对应
       * 的这个文本框input或者table标签绑定一个js事件，失去焦点事件或者键盘弹起事件，并且给事件绑定
       * 一个自定义的函数，在这个函数内部我们通过这个ajax异步请求去和我们的后端代码进行交互，请求我们
       * ajax异步请求url对应的控制器中的某一个方法，之后拿到这个后端返回的数据。这个例子也就是1:首先获
       * 取到这个文本输出的值( data: {'name':$("#textName").val()},)，
       * 之后向服务器控制器中某一个方法发起ajax异步请求（$.ajax），
       * 3：接收服务器返回的数据，(ajax请求成功后所执行的回调函数)*/
       /*我们如果 想要知道我们的某一个操作所绑定的js是否执行成功我们就可以
       * 给我们的这个指定方法中通过这个alert弹框看我们的事件是否绑定成功，
       * 还有就是我们声明一个js函数的时候我们要选择这个
       * function AAA() (serval definations),而不是选择哪一个function后面没有括号的
       * 这点我们一定要进行注意，因为我们的编辑工具都是有提示功能，如果我们写出来的代码
       * 某一块报红，则原因一定是我们导错了maven的jar包，或者是我们使用的函数定义不
       * 符合规则。都有可能会导致我们程序报错。此外我们要对这个ajax发送异步请求的写法要熟悉*/
       alert(111111);
       $.ajax({
           url: "${pageContext.request.contextPath}/ajax/a1",
          /*这里我们的name属性加不加双引号或者单引号都是可以的。ajax底层会帮助我没呢进行解析
           data: {'name':$("#textName").val()},*/
           data: {name:$("#textName").val()},
           success: function(data,status){
           /*这个是在我们浏览器的控制台上面进行打印的*/
               console.log(data); //这里的这个data也就是我们服务器端返回的数据
               console.log(status);//服务器端是否响应成功
           }
       });
   }


</script>
<body>
  <%--我们使用ajax作为我们的这个前后端数据交互，下面这个<input>标签也就是我们这个前端页面中展示给用户的一个
  文本输入框，到时候用户在这个文本输入框中进行输入文本信息，我们通常都是给我们的html中的每一个标签绑定一个id
  属性和这个class属性，其中这个id属性是给我们的这个js函数进行操作的，而这个我们给某一个标签所设置的class
  属性通常都是用来设置这个我们的每一个标签的样式显示给用户。这里我们采用的是jsp也就是这个前端的js代码和这个
  HTML代码在同一个文件里面，而采用这个前后端分离的情况下我们都是这个前端分为这个html页面以及这个和每一个html
  页面同名的js文件，通过这个html向用户展示这个固定内容，然后通过同名的js文件向这个html页面完成数据的填充，
  这些数据是从我们的这个js文件中向我们的服务器端发送ajax异步请求，在这个ajax异步请求发送之前，它会首先通过
  这个获取到这个用户在HTML页面中的文本框中输入的值，并且将文本框输入的值作为这个我们发送ajax异步请求的参数data
  ，之后$("#textName").val()，这里的这个textname也就是我们的这个id标签的属性值。$("#textName").val()
  表示的也就是通过这个val()方法得到用户输入的值，之后向我们的后台代码controller控制器中指定到的某一个方法
  发送ajax异步请求--%>


  <%--点击这个用户名输入我们进行用户名输入操作，一旦我们的这个输入文本框失去焦点就会触发
  一个js函数run1(),在这个函数内部我们需要向这个服务器后端代码发送一个ajax异步请求(包含请求
  地址，请求参数等)，请求成功后就会执行这个请求成功的回调函数success，并且把后端返回的数据得到--%>
 <%--onblur失去焦点事件--%>
  用户名：
   <input type="text" id="textName" onblur="run1()"/>

</body>
</html>
