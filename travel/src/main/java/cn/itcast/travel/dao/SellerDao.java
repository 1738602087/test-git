package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 方法描述 根据这个sid查询商家信息
     * @param sid
     * @return: Seller
     * @Author: lxy
     * @date 2020/11/29 14:03
     */

    public Seller getSellerBysid(int sid);
}
