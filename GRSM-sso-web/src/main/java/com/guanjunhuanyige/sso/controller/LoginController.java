package com.guanjunhuanyige.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.sso.service.LoginService;
import com.guanjunhuanyige.utils.CookieUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 登录
 * @author 落雪封尘
 *
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Value("${TOKEN}")
	private String TOKEN;
	
	@RequestMapping("/page/login")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult login(String username, String password,HttpServletRequest request, HttpServletResponse response) {
		TaojinResult result = loginService.userLogin(username, password);
		if (result.getStatus() == 200) {
			String token = result.getData().toString();
			CookieUtils.setCookie(request, response, TOKEN, token);
		}
		return result;
	}
	
}
