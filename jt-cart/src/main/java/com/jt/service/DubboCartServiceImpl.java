package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        return cartList;
    }

    @Override
    public void saveCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId()).eq("item_id",cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if(cartDB==null){
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else{
            int num = cartDB.getNum() + cart.getNum();
            Cart updatedCart = new Cart();
            updatedCart.setId(cartDB.getId()).setNum(num).setUpdated(new Date());
            cartMapper.updateById(updatedCart);
        }
    }

    @Override
    public void updateCartNum(Cart cart) {
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",cart.getUserId())
                .eq("item_id",cart.getItemId());
        cart.setUpdated(new Date());
        cartMapper.update(cart,updateWrapper);
    }

    @Override
    public void deleteFromCart(Long userId, Long itemId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("item_id",itemId);
        cartMapper.delete(queryWrapper);
    }
}
