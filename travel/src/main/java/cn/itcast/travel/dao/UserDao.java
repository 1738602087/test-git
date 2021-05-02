package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 方法描述 根据用户名查询信息
     * @param username
     * @return: User
     * @Author: lxy
     * @date 2020/10/30 9:11
     */

    public User findUserByname(String username);
    /**
     * 方法描述 保存用户方法
     * @param user
     * @return: void
     * @Author: lxy
     * @date 2020/10/30 9:13
     */

    public void saveUser(User user);

    User findByCode(String code);

    void updateStatus(User user);
    /**
     * 方法描述  根据用户名和密码查询用户
     * @param username
     * @param password
     * @return: User
     * @Author: lxy
     * @date 2020/11/10 15:43
     */

    User findByUsernameAndPassword(String username,String password);
}
