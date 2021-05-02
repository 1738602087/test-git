package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/Category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService=new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CategoryServlet的find方法执行了");
        //1，调用service进行查询
        List<Category> list = categoryService.findAll();
        //2.1 通过jackson内置对象将方法的返回值序列化为json.
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(list);
        //2.2 设置返回数据类型以及编码格式
        response.setContentType("application/json,charset=UTF-8");
        //2.3 通过response输出流的方式向前端写回数据
        response.getWriter().write(value);

    }
    public void findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("6666666666");
        String cidStr = request.getParameter("cid");
        int cid=0;
        if(cidStr!=null&&cidStr!=""){
             cid = Integer.parseInt(cidStr);
        }
        //1，调用service进行查询
        Category category = categoryService.findCategory(cid);
        //2.1 通过jackson内置对象将方法的返回值序列化为json.
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(category);
        //2.2 设置返回数据类型以及编码格式
        response.setContentType("application/json,charset=UTF-8");
        //2.3 通过response输出流的方式向前端写回数据
        response.getWriter().write(value);

    }
}
