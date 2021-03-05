package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.utils.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    ItemCatMapper itemCatMapper;

//    @Autowired
    Jedis jedis;

    @Override
    public ItemCat findItemCatById(Integer itemCatId) {
        ItemCat itemCat = itemCatMapper.selectById(itemCatId);
        return itemCat;
    }

    @Override
    @CacheFind
    public List<EasyUITree> findItemCatByParentId(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<ItemCat> itemCats = itemCatMapper.selectList(queryWrapper);
        List<EasyUITree> itemCatList = new ArrayList(itemCats.size());
        for (ItemCat itemCat : itemCats) {
            Long id = itemCat.getId();
            String text = itemCat.getName();
            String state = itemCat.getIsParent()?"closed":"open";
            itemCatList.add(new EasyUITree(id,text,state));
        }
        return itemCatList;
    }

    @Override
    public List<EasyUITree> findItemCatByCache(Long parentId) {
        long start = System.currentTimeMillis();
        String key = "com.jt.service.ItemCatServiceImpl.findItemCatByCache::"+parentId;
        String value = jedis.get(key);
        List<EasyUITree> treeList=new ArrayList<>();
        if(StringUtils.isEmpty(value)){
            treeList = findItemCatByParentId(parentId);
            value = ObjectMapperUtil.toJSON(treeList);
            jedis.set(key, value);
            long dbTime = System.currentTimeMillis()-start;
            System.out.println("dbTime:"+dbTime);
        }else{
            treeList = ObjectMapperUtil.toObj(value, treeList.getClass());
            long redisTime = System.currentTimeMillis()-start;
            System.out.println("redisTime:"+redisTime);
        }
        return treeList;
    }
}
