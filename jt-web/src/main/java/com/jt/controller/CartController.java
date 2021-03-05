package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(check = false)
    private DubboCartService cartService;

    @RequestMapping("/show")
    private String show(Model model){
        Long userId= ThreadLocalUtil.getUser().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    /**
     * 加入购物车
     * url:http://www.jt.com/cart/add/562379.html
     * 参数：itemId以及整个表单
     * 返回：重定向至购物车页面
     */
    @RequestMapping("/add/{itemId}")
    public String saveCart(Cart cart){
        Long userId = ThreadLocalUtil.getUser().getId();
        cart.setUserId(userId);
        cartService.saveCart(cart);
        return "redirect:/cart/show.html";
    }

    /**
     * 修改购物车商品数量
     * url:http://www.jt.com/cart/update/num/562379/10
     * 参数：itemId,num,userId
     * 返回值：？
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(Cart cart){
        Long userId = ThreadLocalUtil.getUser().getId();
        cart.setUserId(userId);
        cartService.updateCartNum(cart);
        return SysResult.success();
    }

    /**
     *  从购物车删除
     *  url：/cart/delete/${cart.itemId}.html
     *  参数：itemId
     *  返回值：重定向至购物车页面
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteFromCart(@PathVariable Long itemId){
        Long userId = ThreadLocalUtil.getUser().getId();
        cartService.deleteFromCart(userId,itemId);
        return "redirect:/cart/show.html";
    }
}
