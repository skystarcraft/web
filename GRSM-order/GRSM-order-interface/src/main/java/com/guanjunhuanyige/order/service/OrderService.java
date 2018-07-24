package com.guanjunhuanyige.order.service;

import com.guanjunhuanyige.order.pojo.OrderInfo;
import com.guanjunhuanyige.utils.TaojinResult;

public interface OrderService {

	TaojinResult create(OrderInfo orderinfo);
	
}
