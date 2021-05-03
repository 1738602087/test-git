package cn.itcast.springmvc.service;

import cn.itcast.springmvc.pojo.Items;

import java.util.List;

public interface ItemService {
    List<Items> getItemList();
    Items getItemById(int id);
    void updateItem(Items items);

}
