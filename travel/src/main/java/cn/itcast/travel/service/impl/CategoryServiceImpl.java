/**
 * @ClassName : CategoryServiceImpl  //类名
 * @Description : 旅游分类  //描述
 * @Author : lxy //作者
 * @Date: 2020-11-17 17:14  //时间
 */
package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CatergoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
   private CatergoryDao catergoryDao= new CategoryDaoImpl();
    /**
     * 方法描述 旅游分类查询
     * @param
     * @return: List<Category>
     * @Author: lxy
     * @date 2020/11/17 17:15
     */

    @Override
    public List<Category> findAll() {

        //1.从redis中查询,我们要从redis中进行查询我们就是首先要获取这个jedis的客户端，
        //这里我们通过这个JedisUtil工具类来进行获取
        Jedis jedis = JedisUtil.getJedis();
        //sortedset获取命令zrange,存储命令zadd.

        //1.2 我们可以使用sortedset排序查询，我们通过这个zrange方法来进行sortedSet范围的查询
       /* Redis Zrange 返回有序集中，指定区间内的成员。其中成员的位置按分数值递增(从小到大)
       来排序。具有相同分数值的成员按字典序(lexicographical order )来排列。如果你需要
       成员按值递减(从大到小)来排列，请使用 ZREVRANGE 命令。下标参数 start 和 stop
       都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，
       以此类推。你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，
       以此类推。*/
       /*第一个参数也就是redis的key，第二个参数和第三个参数表示索引范围。这里我们查询所有
       * 该方法的返回值是一个set集合categorys*/
       //查询sortedset中的分数和值
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> ls=null;
        //2.判断查询的集合是否为空,判断这个cateergorys集合是否等于null或者长度等于0
        if(categorys == null || categorys.size()==0){
            System.out.println("从数据库中查询数据");
            //3.如果查询的集合为空，证明用户第一次访问，需要从数据库中查询数据，并且将数据存放到redis中
            //3.1 调用dao层从数据库中查询
            ls = catergoryDao.findAll();
            //3.2 把查询到的数据存入到redis中,这里我们遍历list集合取出集合中的每一个元素并且存放到Jedis
            for (int i=0;i<ls.size();i++){
                /*Redis Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。
                 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入
                 这个成员元素，来保证该成员在正确的位置上。分数值可以是整数值或双精度浮点
                 数。如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
                 当 key 存在但不是有序集类型时，返回一个错误。*/
                /*我们的这个sortset在进行存储的时候要给一个分数，这个分数也就是排序的准则
                * 这个分数我们直接拿id就可以，存的时候我们需要注意也就是将来我们数据中有几条数据
                * 我们就存几次，所以这里我们需要进行这个集合的遍历。*/
               jedis.zadd("category", ls.get(i).getCid(), ls.get(i).getCname());

            }

    } else {
            /*如果不为空，缓存有数据我们就应该直接返回这个categorys,但是这个categorys又是一个set集合
            * 而我们的方法的返回值是一个list集合所以我们需要把这个set集合转换成一个list
            * 集合进行返回，所以我们就是首先把这个set集合中的每
            * 一个元素遍历出来，之后再存入到这个list集合中*/
            System.out.println("从redis缓存中查询数据");
            // 如果不为空，就把这个set集合转换成list集合进行返回
            // 首先创建一个list集合
            ls = new ArrayList<Category>();
            /*增强for循环首先遍历set集合取出set集合中的每一个元素，首先把每一个元素都存放在我们的
            * category对象里面，之后把category对象存放到我们创建好的list集合中*/
            for (Tuple tuple : categorys) {
              Category category = new Category();
              category.setCname(tuple.getElement());
              category.setCid((int) tuple.getScore());
              ls.add(category);
           }
        }

        //如果查询的集合不为空，就直接从redis中查询数据返回就可。
        return ls;
    }
    /**
     * 方法描述 根据这个cid查询这个category对象
     * @param cid
     * @return: Category
     * @Author: lxy
     * @date 2020/12/1 13:13
     */

    @Override
    public Category findCategory(int cid) {
        System.out.println(cid);
        return catergoryDao.findCategory(cid);
    }
}

