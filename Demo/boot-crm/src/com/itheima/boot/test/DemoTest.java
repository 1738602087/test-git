/**
 * @ClassName : DemoTest  //类名
 * @Description : 测试数据库连接  //描述
 * @Author : lxy //作者
 * @Date: 2020-10-25 17:09  //时间
 */
package com.itheima.boot.test;

import com.itheima.boot.dao.CustomerDao;
import com.itheima.boot.pojo.Customer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DemoTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "SqlMapconfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        System.out.println(sqlSessionFactory);
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CustomerDao mapper = openSession.getMapper(CustomerDao.class);
            Customer employee = mapper.getCustomerById(1L);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

}

