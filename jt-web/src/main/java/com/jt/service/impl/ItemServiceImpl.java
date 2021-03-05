package com.jt.service.impl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClientService;

    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemById";
        Map<String,String> params = new HashMap<>();
        params.put("itemId",itemId.toString());
        String result = httpClientService.doGet(url, params);
        return ObjectMapperUtil.toObj(result, Item.class);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemDescById";
        Map<String,String> params = new HashMap<>();
        params.put("itemId",itemId.toString());
        String result = httpClientService.doGet(url, params);
        ItemDesc itemDesc = ObjectMapperUtil.toObj(result, ItemDesc.class);
        return itemDesc;
    }
}
