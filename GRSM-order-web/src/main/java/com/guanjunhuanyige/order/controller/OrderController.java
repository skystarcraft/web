package com.guanjunhuanyige.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guanjunhuanyige.cart.service.CartService;
import com.guanjunhuanyige.order.pojo.OrderInfo;
import com.guanjunhuanyige.order.service.OrderService;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 订单管理
 * @author 落雪封尘
 *
 */

@Controller
public class OrderController {

	@Autowired
	private CartService cartSrevice;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = cartSrevice.getCartList(user.getId());
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		TaojinResult result = orderService.create(orderInfo);
		if (result.getStatus() == 200) {
			cartSrevice.clearCartItem(user.getId());
		}
		request.setAttribute("orderId", result.getData());
		request.setAttribute("payment", orderInfo.getPayment());
		
		return "success";
	}
	
}
