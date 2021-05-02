/**
 * @ClassName : RouterServiceImpl  //类名
 * @Description : 旅游线路分页查询  //描述
 * @Author : lxy //作者
 * @Date: 2020-11-22 10:37  //时间
 */
package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouterService;

import java.util.List;

public class RouterServiceImpl implements RouterService {
    private RouteDao routeDao= new RouteDaoImpl();
    private RouteImgDao routeImgDao= new RouteImgDaoImpl();
    private SellerDao sellerDao= new SellerDaoImpl();
    private FavoriteDao favoriteDao= new FavoriteDaoImpl();


    /**
     * 方法描述 分页查询旅游线路
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return: PageBean<Route>
     * @Author: lxy
     * @date 2020/11/22 10:37
     */
    /*我们要完成这个pageQuery方法的编写无外乎就是组装一个pageBean对象，将来把
    这个pageBean对象返回就可以，要想返回这样的一个pageBean对象我们就需要依赖于数据库
    数据库完成一个查询动作，所以我们首先要搞清楚我们的pageBean对象中那些数据是直接可以
    得到的，那些数据是需要从数据库中查询出来的，我们通过观察我们的pageBean实体类可知这个
    totalCount需要从数据库中查询得到，当前页码currentpage和这个每页显示条数pagesize
    是从页面中得到即是客户端前台页面提交的， 而这个总页数totalpage是可以计算出来的，
    通过这个总记录数和每页显示条数进行计算得到，最户这个List集合我们也是从数据库查询得到的
    ，所以我们分析之后我们知道将来我们的Dao层中需要两个方法，这里我们先把这个Dao层写一下*/
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        /*这里service层的业务逻辑也就是封装这个pageBean，并且把这个pageBean对象进行返回就可以*/
        //1.创建一个pageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //2.设置pageBean对象的属性值
        //2.1 首先我们设置直接从这个客户端可以得到的当前页码和这个每页大小给到我们创建的pageBean对象中
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //2.1 设置pageBean对象的totalCount和这个totalPage属性，这两个属性都是调用我们的
        //Dao层中得到的
        //设置总记录数
        int totalCount=routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //设置总页数，总页数=总记录数/每页显示条数
        int totalpage= totalCount % pageSize==0 ? totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalpage);
        //设置当前页显示的数据集合
        int start=(currentPage-1)*pageSize; //起始记录
        List<Route> routeList = routeDao.findByPage(cid, start, pageSize,rname);
        pageBean.setList(routeList);
        //pageBean的每一个属性都有了，所以我们把这个pageBean对象直接返回就可以
        return pageBean;
    }
    @Override
    public PageBean<Route> pageQueryTwo(int cid, int currentPage, int pageSize) {
        /*这里service层的业务逻辑也就是封装这个pageBean，并且把这个pageBean对象进行返回就可以*/
        //1.创建一个pageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //2.设置pageBean对象的属性值
        //2.1 首先我们设置直接从这个客户端可以得到的当前页码和这个每页大小给到我们创建的pageBean对象中
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //2.1 设置pageBean对象的totalCount和这个totalPage属性，这两个属性都是调用我们的
        //Dao层中得到的
        //设置总记录数
        int totalCount=routeDao.findTotalCountTwo(cid);
        pageBean.setTotalCount(totalCount);
        //设置总页数，总页数=总记录数/每页显示条数
        int totalpage= totalCount % pageSize==0 ? totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalpage);
        //设置当前页显示的数据集合
        int start=(currentPage-1)*pageSize; //起始记录
        List<Route> routeList = routeDao.findByPageTwo(cid, start, pageSize);
        pageBean.setList(routeList);
        //pageBean的每一个属性都有了，所以我们把这个pageBean对象直接返回就可以
        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //1.根据这个rid查询route对象
        Route route = routeDao.findById(rid);
        //2.根据rid线路id查询table_route_img，将集合设置到route对象中
        List<RouteImg> routeImgs = routeImgDao.listRouteImgByrid(rid);
        route.setRouteImgList(routeImgs);
        //3.根据sid卖家id到table_seller商家表中查询商家信息
        Seller seller = sellerDao.getSellerBysid(route.getSid());
        route.setSeller(seller);

        //设置route对象的旅游线路收藏次数
       int count= favoriteDao.findCountByRid(route.getRid());
       route.setCount(count);
       return route;
    }
}

