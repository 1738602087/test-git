package cn.itcast.springmvc.service.impl;

import cn.itcast.springmvc.mapper.ItemsMapper;
import cn.itcast.springmvc.pojo.Items;
import cn.itcast.springmvc.pojo.ItemsExample;
import cn.itcast.springmvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Override
    public List<Items> getItemList() {
        ItemsExample example = new ItemsExample();
        List<Items> list = itemsMapper.selectByExampleWithBLOBs(example) ;
        return list;
    }

    @Override
    public Items getItemById(int id) {
        //根据商品id查询商品信息
        Items items = itemsMapper.selectByPrimaryKey(id);
        return items;
    }

    @Override
    public void updateItem(Items items) {
       itemsMapper.updateByPrimaryKeySelective(items);
    }

}
