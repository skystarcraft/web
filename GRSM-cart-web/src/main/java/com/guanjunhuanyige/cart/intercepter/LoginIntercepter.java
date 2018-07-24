package com.guanjunhuanyige.cart.intercepter;

/**
 * 用户登录处理
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.sso.service.TokenService;
import com.guanjunhuanyige.utils.CookieUtils;
import com.guanjunhuanyige.utils.TaojinResult;

public class LoginIntercepter implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String token = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isBlank(token)) {
			return true;
		}
		TaojinResult result = tokenService.getUserByToken(token);
		if (result.getStatus() != 200) return true;
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user", user);
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
