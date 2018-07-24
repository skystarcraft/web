package com.guanjunhuanyige.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.cart.service.CartService;
import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.mapper.TbItemMapper;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 购物车处理服务
 * @author 落雪封尘
 *
 */
@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CART}")
	private String CART;
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public TaojinResult addCart(long userId, long itemId, int num) {
		Boolean hexists = jedisClient.hexists(CART + ":" + userId, itemId + "");
		if (hexists) {
			String json = jedisClient.hget(CART + ":" + userId, itemId + "");
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			item.setNum(num + item.getNum());
			jedisClient.hset(CART + ":" + userId, itemId + "",JsonUtils.objectToJson(item));
			return TaojinResult.ok();
		}
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		item.setNum(num);
		String image = item.getImage();
		if (StringUtils.isNotBlank(image)) {
			item.setImage(image.split(",")[0]);
		}
		jedisClient.hset(CART + ":" + userId, itemId + "",JsonUtils.objectToJson(item));
		return TaojinResult.ok();
	}

	@Override
	public TaojinResult mergeCart(long userId, List<TbItem> itemList) {
		for (TbItem tbItem : itemList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return TaojinResult.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		List<String> jsonList = jedisClient.hvals(CART + ":" + userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String string : jsonList) {
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public TaojinResult updateCart(long userId, long itemId, int num) {
		String json = jedisClient.hget(CART + ":" + userId, itemId + "");
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		tbItem.setNum(num);
		jedisClient.hset(CART + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
		return TaojinResult.ok();
	}

	@Override
	public TaojinResult deleteCart(long userId, long itemId) {
		jedisClient.hdel(CART + ":" + userId, itemId + "");
		return TaojinResult.ok();
	}

	@Override
	public TaojinResult clearCartItem(long userId) {
		jedisClient.del(CART + ":" + userId);
		return TaojinResult.ok();
	}
	
}
