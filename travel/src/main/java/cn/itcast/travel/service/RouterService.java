package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouterService {
    /**
     * 方法描述 分页查询旅游线路
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return: PageBean<Route>
     * @Author: lxy
     * @date 2020/11/22 10:36
     */

    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);
    /**
     * 方法描述 分页查询旅游线路
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return: PageBean<Route>
     * @Author: lxy
     * @date 2020/11/22 10:36
     */

    public PageBean<Route> pageQueryTwo(int cid,int currentPage,int pageSize);
    /**
     * 方法描述 查询线路详情描述
     * @param rid
     * @return: Route
     * @Author: lxy
     * @date 2020/11/29 13:27
     */

    public Route findOne(int rid);
}
