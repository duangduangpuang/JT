package com.jt.controller;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    ItemCatService itemCatService;

    @RequestMapping("/queryItemName")
    public String findItemCatById(Integer itemCatId){
        return itemCatService.findItemCatById(itemCatId).getName();
    }

    /**
     * 新增页类目选择框
     * url:/item/cat/list
     * 参数：id(一级没有)
     * 返回值：itemCat对象集合
     */
    @RequestMapping("/list")
    public List<EasyUITree> findItemCatByParentId(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return itemCatService.findItemCatByParentId(parentId);
//        return itemCatService.findItemCatByCache(parentId);
    }


}
