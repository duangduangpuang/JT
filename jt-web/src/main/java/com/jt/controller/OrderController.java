package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(check = false)
    private DubboCartService cartService;

    @Reference(check = false)
    private DubboOrderService orderService;

    @RequestMapping("/create")
    public String create(Model model){
        Long userId = ThreadLocalUtil.getUser().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }

    /**
     * 订单提交
     * url:http://www.jt.com/order/submit
     * 参数：表单数据
     * 返回：SysResult
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult submit(Order order){
        Long userId = ThreadLocalUtil.getUser().getId();
        order.setUserId(userId);
        String orderId = orderService.saveOrder(order);
        if(StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    /**
     * 提交成功页面
     * url:http://www.jt.com/order/success.html?id=71582982280920
     * 参数：id
     * 返回值：
     */
    @RequestMapping("/success")
    public String success(String id,Model model){
        Order order = orderService.findOrderById(id);
        model.addAttribute("order",order);
        return "success";
    }
}
