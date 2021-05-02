package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CatergoryDao {
    /**
     * 方法描述  查询旅游分类数据
     * @param
     * @return: List<Category>
     * @Author: lxy
     * @date 2020/11/17 16:52
     */

    public List<Category> findAll();
    /**
     * 方法描述 根据这个cid来进行查询旅游线路的名称
     * @param cid
     * @return: Category
     * @Author: lxy
     * @date 2020/12/1 13:03
     */

    public Category findCategory(int cid);
}
