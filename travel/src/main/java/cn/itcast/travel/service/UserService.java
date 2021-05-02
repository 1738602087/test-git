package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 方法描述
     * @param user
     * @return: boolean
     * @Author: lxy
     * @date 2020/10/30 8:14
     */

    public boolean register(User user);
    /**
     * 方法描述 激活用户方法
     * @param code
     * @return: boolean
     * @Author: lxy
     * @date 2020/11/3 21:26
     */
    public boolean active(String code);
    /**
     * 方法描述 根据用户名和密码查询用户
     * @param user
     * @return: User
     * @Author: lxy
     * @date 2020/11/10 15:48
     */

    public User login(User user);

}
