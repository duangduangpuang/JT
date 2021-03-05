package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.ItemVo;
import org.apache.ibatis.annotations.Param;

public interface ItemService {

    ItemVo findItemsByPage(@Param("page") Integer page, @Param("rows") Integer rows);

    void insertItem(Item item, ItemDesc itemDesc);

    void deleteItemByIds(@Param("ids") Long ...ids);

    void updateItemById(Item item,ItemDesc itemDesc);

    void updateStatusByIds(Long[] ids, int status);

    ItemDesc findItemDescById(Long itemId);

    Item findItemById(Long itemId);
}
