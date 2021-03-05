package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
    @Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	public ItemVo findItemsByPage(Integer page, Integer rows) {
		Page<Item> iPage = new Page<>(page,rows);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		IPage<Item> itemIPage = itemMapper.selectPage(iPage, queryWrapper);
		Integer total = (int)itemIPage.getTotal();
		List<Item> itemList = itemIPage.getRecords();
		ItemVo itemVo = new ItemVo(total, itemList);
		return itemVo;
	}

	@Override
	public void insertItem(Item item, ItemDesc itemDesc) {
	    //由于id是主键自增，所以这里id的值为null
		item.setStatus(1)
				.setCreated(new Date())
				.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//这里之所有能通过item.getId()取到id值是因为mybatis-plus在将item入库后会将id回显
		itemDesc.setItemId(item.getId())
                .setCreated(item.getCreated())
                .setUpdated((item.getCreated()));
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void deleteItemByIds(Long... ids) {
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}


	@Override
	public void updateItemById(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);

		itemDesc.setItemId(item.getId())
                .setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

    @Override
    public void updateStatusByIds(Long[] ids, int status) {
        Item item = new Item();
        item.setStatus(status).setUpdated(new Date());
        UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",Arrays.asList(ids));
        itemMapper.update(item,updateWrapper);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        return itemDescMapper.selectById(itemId);
    }

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectById(itemId);
	}


}
