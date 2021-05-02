package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 方法描述  根据cid查询总记录数
     * @param cid
     * @return: int
     * @Author: lxy
     * @date 2020/11/22 11:04
     */

    public int findTotalCount(int cid,String rname);
    /**
     * 方法描述  根据这个cid，currentPage,pageSize来查询这个每页显示的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return: List<Route>
     * @Author: lxy
     * @date 2020/11/22 11:07
     */

    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    /**
     * 方法描述  根据cid查询总记录数
     * @param cid
     * @return: int
     * @Author: lxy
     * @date 2020/11/22 11:04
     */

    public int findTotalCountTwo(int cid);
    /**
     * 方法描述  根据这个cid，currentPage,pageSize来查询这个每页显示的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return: List<Route>
     * @Author: lxy
     * @date 2020/11/22 11:07
     */

    public List<Route> findByPageTwo(int cid,int start,int pageSize);
    /**
     * 方法描述 根据这个rid查询这个Route对象
     * @param rid
     * @return: Route
     * @Author: lxy
     * @date 2020/11/29 13:31
     */

    public Route findById(int rid);
}
