package com.guanjunhuanyige.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.sso.service.RegisterService;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 注册
 * @author 落雪封尘
 *
 */
@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaojinResult chechData(@PathVariable String param, @PathVariable Integer type) {
		TaojinResult result = registerService.checkData(param, type);
		return result;
	}
	
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public TaojinResult register(TbUser user) {
		TaojinResult result = registerService.register(user);
		return result;
	}
}
