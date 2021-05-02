/**
 * @ClassName : UserServiceImpl  //类名
 * @Description : 用户实体类的逻辑性增删改查  //描述
 * @Author : lxy //作者
 * @Date: 2020-10-29 18:15  //时间
 */
package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

  private UserDao userDao = new UserDaoImpl();
  /**
   * 方法描述 增加用户
   * @param user
   * @return: boolean
   * @Author: lxy
   * @date 2020/10/30 8:18
   */
  @Override
  public boolean register(User user) {
    System.out.println(user);
    // 1：首先根据用户名进行查询，判断用户是否存在，不存在调用增加方法
    User user1 = userDao.findUserByname(user.getUsername());
    if (user1 != null) {
      return false;
    }
    // 2.1设置激活码，唯一字符串
    user.setCode(UuidUtil.getUuid());
    // 2.2设置激活状态
    user.setStatus("N");
    userDao.saveUser(user);

    // 3.激活邮件发送，邮件正文
      String content="<a href='http://localhost:8088/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";

      MailUtils.sendMail(user.getEmail(),content,"激活邮件");

      return true;
  }
   /**
    * 方法描述 激活用户
    * @param code
    * @return: boolean
    * @Author: lxy
    * @date 2020/11/3 21:31
    */

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
       User user= userDao.findByCode(code);
       if(user!=null){
           //2.调用DAO的修改激活状态的方法
           userDao.updateStatus(user);
           return true;
       }else{
           return false;
       }
    }

    @Override
    public User login(User user) {
        //调用dao中根据用户名和密码查询用户
        User user1 = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return user1;
    }

}


    
    
   

