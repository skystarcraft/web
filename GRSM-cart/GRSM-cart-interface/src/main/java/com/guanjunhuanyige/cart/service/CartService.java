package com.guanjunhuanyige.cart.service;

import java.util.List;

import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.utils.TaojinResult;

public interface CartService {

	TaojinResult addCart(long userId, long itemId, int num);
	
	TaojinResult mergeCart(long userId, List<TbItem> itemList);
	
	List<TbItem> getCartList(long userId);
	
	TaojinResult updateCart(long userId, long itemId, int num);
	
	TaojinResult deleteCart(long userId, long itemId);
	
	TaojinResult clearCartItem(long userId);
	
}
