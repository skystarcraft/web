package com.guanjunhuanyige.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.mapper.TbOrderItemMapper;
import com.guanjunhuanyige.mapper.TbOrderMapper;
import com.guanjunhuanyige.mapper.TbOrderShippingMapper;
import com.guanjunhuanyige.order.pojo.OrderInfo;
import com.guanjunhuanyige.order.service.OrderService;
import com.guanjunhuanyige.pojo.TbOrderItem;
import com.guanjunhuanyige.pojo.TbOrderShipping;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 订单处理
 * @author 落雪封尘
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN}")
	private String ORDER_ID_GEN;
	
	@Value("${ORDER_ID_VAL}")
	private String ORDER_ID_VAL;
	
	@Value("${ORDER_ID}")
	private String ORDER_ID;
	
	@Override
	public TaojinResult create(OrderInfo orderinfo) {
		if (!jedisClient.exists(ORDER_ID_GEN)) {
			jedisClient.set(ORDER_ID_GEN, ORDER_ID_VAL);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN).toString();
		orderinfo.setOrderId(orderId);
		orderinfo.setStatus(1);//1.未付款2.已付款
		orderinfo.setCreateTime(new Date());
		orderinfo.setUpdateTime(new Date());
		orderMapper.insert(orderinfo);
		List<TbOrderItem> orderList = orderinfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderList) {
			String OrderId = jedisClient.incr(ORDER_ID).toString();
			tbOrderItem.setId(OrderId);
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);
		}
		TbOrderShipping orderShipping = orderinfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		
		return TaojinResult.ok(orderId);
	}

	
}
