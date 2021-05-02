/**
 * @ClassName : RegisterUserServlet  //类名
 * @Description : 注册用户的servlet  //描述
 * @Author : lxy //作者
 * @Date: 2020-10-29 17:02  //时间
 */
package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(11111);
        //验证码，首先得到验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        ////为了保证验证码只能使用一次,如果用户点击注册之后点击回退按钮验证码应该清空,重新生成
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json字符串。
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
      // 这里因为我们设置了返回给前台的数据格式是json，所以就会将我们返回的json格式的字符串直接转化为json对象。
      // 所以我们在前台页面register.html中alert(data)为【object,object】,这里如果说我们不进行设置，它
      // 服务器端也会根据这个我们前端请求服务器端的数据格式确定返回到前台的数据格式，这里我们前端页面中
       //post请求就是没有指定，所以我们这里设置了返回为json，那么返回到前端就是一个json对象。
            // dataType:
            // 要求为String类型的参数，预期服务器返回的数据类型。如果不指定，
            // JQuery将自动根据http包mime信息返回responseXML或responseText，
            // 并作为回调函数参数传递。
      response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }


        //1获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3调用service完成注册
        UserService userService = new UserServiceImpl();
        boolean flag=userService.register(user);
        ResultInfo Info = new ResultInfo();
        if(flag){
            //注册成功就提示true
            Info.setFlag(true);
        }else {
            //注册失败就提示false
            Info.setFlag(false);
            Info.setErrorMsg("注册失败");
        }
        //将info对象序列化为json字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Info);//json="{"flag":true,"data":null,"errorMsg":null}"
        System.out.println(json); //json="{"flag":true,"data":null,"errorMsg":null}"
        //将json数据写回客户端
        //设置content-type返回类型为json格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);


    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}

