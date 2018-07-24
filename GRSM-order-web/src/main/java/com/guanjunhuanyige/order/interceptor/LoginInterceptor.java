package com.guanjunhuanyige.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.guanjunhuanyige.cart.service.CartService;
import com.guanjunhuanyige.pojo.TbItem;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.sso.service.TokenService;
import com.guanjunhuanyige.utils.CookieUtils;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 用户登录拦截器
 * @author 落雪封尘
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${ADD}")
	private String ADD;

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isBlank(token)) {
			response.sendRedirect(ADD + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		TaojinResult result = tokenService.getUserByToken(token);
		if (result.getStatus() != 200) {
			response.sendRedirect(ADD + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user", user);
		String json = CookieUtils.getCookieValue(request, "cart",true);
		if (StringUtils.isNotBlank(json)) {
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(json, TbItem.class));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
