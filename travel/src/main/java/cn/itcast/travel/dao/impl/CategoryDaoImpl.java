/**
 * @ClassName : CategoryDaoImpl  //类名
 * @Description : 旅游分类  //描述
 * @Author : lxy //作者
 * @Date: 2020-11-17 16:52  //时间
 */
package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CatergoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CatergoryDao {

    private JdbcTemplate template= new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 方法描述 旅游分类查询方法
     * @param
     * @return: List<Category>
     * @Author: lxy
     * @date 2020/11/17 16:54
     */

    @Override
    public List<Category> findAll() {
        String sql="SELECT * FROM `tab_category`";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public Category findCategory(int cid) {
        String sql="SELECT * FROM `tab_category` where cid =?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),cid);
    }
}

