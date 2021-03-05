package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.utils.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class JSONPController {

//    @RequestMapping("/testJSONP")
    public String jsonp(String callback){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L).setItemDesc("商品详情");
        String json = ObjectMapperUtil.toJSON(itemDesc);
        return callback+"("+json+")";
    }

    @RequestMapping("/testJSONP")
    public JSONPObject jsonp2(String callback){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(100000L).setItemDesc("商品详情");
        return new JSONPObject(callback, itemDesc);
    }

}
