package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouterService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouterServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouterServlet extends BaseServlet {
    private RouterService routerService=new RouterServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println(1111);
        //1.从request域中取出三个表现层前台的请求参数，即接收参数
        //获取当前页码
        String currentPageStr = request.getParameter("currentPageStr");
        // 获取每页显示条数
        String pageSizeStr = request.getParameter("pageSizeStr");
        //获取分页id
        String cidStr = request.getParameter("cidStr");
        //获取线路名称
        String rname = request.getParameter("rname");

        //2.对参数进行处理
        int cid=0;  //定义一个分页id变量cid,默认值为0
        /*因为我们从表现层前台获取到的参数都是string类型的字符串， 所以我们首先对这个字符串进行判读
        * 如果参数不为null而且字符串的长度大于0的时候我们调用Integer对象的parseInt方法来对对象进行强转
        * 将其转化为Integer类型，因为我们在调用service层的时候需要传递的参数是int类型，所以这里我们需要
        * 首先对表现层前台传递过来的参数进行处理通过Integer对象的parseInt方法将字符串转为int类型*/

        /*这里我们首先对这个简单的参数cid进行处理，cid不为空就表示它有值，除了不等于空之外，因为字符串还有长度，
        所以它不能够是空字符串，它不为空我们就将其进行转化，这里我们通过在外面定义一个cid的变量，这样我们就可以完成了
        对cid的参数处理，之后我们同理再去处理在这个currentPageStr参数和这个pageSizeStr参数，*/
        if(cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){
          cid=Integer.parseInt(cidStr);
        }
        /*这里注意如果我们参数字符串不为null而且长度大于0我们就将其进行转化为int类型；
        * 这里我们需要单独注意的是如果是第一次访问的话，那么我们的这个currentPageStr通常都是没有的
        * 它有可能就是空的或者就是压根没有获取出来那么我们就给这个currentPageStr一个默认值1*/
        int currentPage=0; //定义一个currentpage变量来表示当前页码
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);

        } else{
            currentPage=1; //如果当前是多少页前台页面没有传递，那么默认就是显示第一页
        }
        /**/
        int pageSize=0; //定义一个pagesize变量来表示每页显示多少条记录
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize=5;//如果每页显示条数前台页面没有传递，那么就是默认每一页显示5条
        }
        //3.调用service层进行这个分页处理，并且查询出来一个PageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        /*3.1这里我们分为两种情况，一种就是我们查询的时候根据这个分类id和线路名称以及当前是第几页进行查询*/
        if (rname != null && rname != "") {
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
            pageBean= routerService.pageQuery(cid, currentPage, pageSize,rname);
        }else{
            /*3.1这里第二种情况，我们只根据这个分类id和当前是第几页进行查询*/
            pageBean= routerService.pageQueryTwo(cid, currentPage, pageSize);
        }

       // PageBean<Route> pageBean= routerService.pageQuery(cid, currentPage, pageSize,rname);


        //4.将pageBean对象序列化为json,返回给客户端。
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(mapper.writeValueAsString(pageBean));

    }
    /*
     * 方法描述  旅游线路详情展示
     * @param null
     * @return: null
     * @Author: lxy
     * @date 2020/11/29 13:19
     */
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1.接收这个旅游线路的rid
        String ridStr = request.getParameter("ridStr");
        int rid=0;
        if(ridStr!=null&&ridStr!=""){
             rid = Integer.parseInt(ridStr);
        }
        //2.调用service进行查询
        Route route = routerService.findOne(rid);

        //3.将方法的返回值序列化为json进行返回
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(route);
        response.setContentType("application/json;charset:utf-8");
        response.getWriter().write(json);

    }
    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }

        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(flag);
        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(s);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路rid
        String rid = request.getParameter("rid");
        //2. 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return ;
        }else{
            //用户已经登录
            uid = user.getUid();
        }


        //3. 调用service添加
        favoriteService.add(rid,uid);

    }


}
