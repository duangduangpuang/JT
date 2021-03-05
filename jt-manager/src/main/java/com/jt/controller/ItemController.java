package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.ItemVo;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/query")
	public ItemVo queryItems(Integer page, Integer rows){
		ItemVo itemVo = itemService.findItemsByPage(page, rows);
		return itemVo;
	}

	/**
	 * 新增商品操作
	 * url:/item/save
	 * 参数：表单内容
	 * 返回值：SysResult.success
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc){
		itemService.insertItem(item,itemDesc);
		return SysResult.success();
	}

	/**
	 * 删除操作
	 * url:/item/delete
	 * 参数：ids
	 * 返回值：SysResult
	 */
	@RequestMapping("/delete")
	public SysResult deleteItemByIds(Long ...ids){
		itemService.deleteItemByIds(ids);
		return SysResult.success();
	}

	/**
	 * 修改商品操作
	 * url:/item/update
	 * 参数：表单对象
	 * 返回值：SysResult
	 */
	@RequestMapping("/update")
	public SysResult updateItemById(Item item, ItemDesc itemDesc){
		itemService.updateItemById(item,itemDesc);
		return SysResult.success();
	}


	/**
	 * 商品上下架操作
	 * url:/item/instock,	/item/reshelf
	 * 参数：ids
	 */
	@RequestMapping("/instock")
	public SysResult instockItemByIds(Long ...ids){
		int status = 2;
		itemService.updateStatusByIds(ids,status);
		return SysResult.success();
	}
	@RequestMapping("/reshelf")
	public SysResult reshelfItemByIds(Long ...ids){
		int status = 1;
		itemService.updateStatusByIds(ids,status);
		return SysResult.success();
	}

	/**
	 * 获取商品详情
	 * url:/item/query/item/desc/data.itemId
	 * 返回值：SysResult(itemDesc)
	 */
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId){
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		return SysResult.success(itemDesc);
	}



}
