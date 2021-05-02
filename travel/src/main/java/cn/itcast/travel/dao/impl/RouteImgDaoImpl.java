/**
 * @ClassName : RouteImgDaoImpl  //类名
 * @Description :   //描述
 * @Author : lxy //作者
 * @Date: 2020-11-29 13:47  //时间
 */
package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate= new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 方法描述 根据这个rid来查询这个旅游线路详情图片集合
     * @param rid
     * @return: List<RouteImg>
     * @Author: lxy
     * @date 2020/11/29 13:48
     */

    @Override
    public List<RouteImg> listRouteImgByrid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}

