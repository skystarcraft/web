package com.guanjunhuanyige.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.cart.service.CartService;
import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.service.ItemService;
import com.guanjunhuanyige.utils.CookieUtils;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 购物车处理
 * @author 落雪封尘
 *
 */
@Controller
public class CartController {
	
	@Autowired
	private ItemService is;
	
	@Value("${TIME}")
	private Integer TIME;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1")Integer num, HttpServletRequest request, HttpServletResponse response) {
		
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		
		List<TbItem> cartList = getCartFromCookie(request);
		boolean flag = false;
		for (TbItem tb : cartList) {
			if (tb.getId() == itemId.longValue()) {
				flag = true;
				tb.setNum(tb.getNum() + num);
				break;
			}
		}
		if (!flag) {
			TbItem tbItem = is.getItemById(itemId);
			tbItem.setNum(num);
			String image = tbItem.getImage();
			if (StringUtils.isNoneBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			cartList.add(tbItem);
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),TIME,true);
		return "cartSuccess";
	}

	private List<TbItem> getCartFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart",true);
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response){
		List<TbItem> cartList = getCartFromCookie(request);
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.mergeCart(user.getId(), cartList);
			CookieUtils.deleteCookie(request, response, "cart");
			cartList = cartService.getCartList(user.getId());
		}
		
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaojinResult update(@PathVariable Long itemId,@PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.updateCart(user.getId(), itemId, num);
			return TaojinResult.ok();
		}
		
		List<TbItem> cartList = getCartFromCookie(request);
		for (TbItem tb : cartList) {
			if (tb.getId() == itemId.longValue()) {
				tb.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),TIME,true);
		return TaojinResult.ok();
	}
	
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCart(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		
		List<TbItem> cartList = getCartFromCookie(request);
		for (TbItem tb : cartList) {
			if (tb.getId() == itemId.longValue()) {
				cartList.remove(tb);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),TIME,true);
		return "redirect:/cart/cart.html";
	}
}
