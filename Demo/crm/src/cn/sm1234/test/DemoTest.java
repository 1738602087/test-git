/**
 * @ClassName : DemoTest  //类名
 * @Description : 测试数据库连接  //描述
 * @Author : lxy //作者
 * @Date: 2020-10-26 17:53  //时间
 */
package cn.sm1234.test;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DemoTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 		1）、根据全局配置文件得到SqlSessionFactory；
     * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */


    @Test
    public void test01() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        System.out.println(sqlSessionFactory);
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        System.out.println(openSession);
        try {
            // 3、获取接口的实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CustomerMapper mapper = openSession.getMapper(CustomerMapper.class);
            Customer customer = mapper.findById(1);
            System.out.println(mapper.getClass());
            System.out.println(customer);
        } finally {
            openSession.close();
        }

    }

}

