package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;


	@Override
	public String saveOrder(Order order) {
		String orderId = ""+order.getUserId()+ System.currentTimeMillis();
		Date date = new Date();
		//1、入库订单
		order.setOrderId(orderId)
				.setCreated(date)
				.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功！");
		//2、入库物流
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId)
					.setCreated(date)
					.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功！");
		//3、入库订单商品
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId)
					.setCreated(date)
					.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功！");
		return orderId;
	}

	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id",id);
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		order.setOrderShipping(orderShipping)
				.setOrderItems(orderItems);
		return order;
	}
}
