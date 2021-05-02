package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 方法描述 旅游分类查询
     * @param
     * @return: List<Category>
     * @Author: lxy
     * @date 2020/11/17 17:13
     */

    public List<Category> findAll();
    /**
     * 方法描述  根据这个cid查询这个category对象
     * @param cid
     * @return: Category
     * @Author: lxy
     * @date 2020/12/1 13:11
     */

    public Category findCategory(int cid);
}
