package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 方法描述 根据这个rid来查询这个旅游线路详情图片集合
     * @param rid
     * @return: List<RouteImg>
     * @Author: lxy
     * @date 2020/11/29 13:46
     */

    public List<RouteImg> listRouteImgByrid(int rid);
}
