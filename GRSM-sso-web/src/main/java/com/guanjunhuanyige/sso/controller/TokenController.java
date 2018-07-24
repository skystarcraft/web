package com.guanjunhuanyige.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guanjunhuanyige.sso.service.TokenService;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 根据token查询用户信息
 * @author 落雪封尘
 *
 */
@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaojinResult result = tokenService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			//return callback + "(" + JsonUtils.objectToJson(result) + ")";
			MappingJacksonValue map = new MappingJacksonValue(result);
			map.setJsonpFunction(callback);
			return map;
		}
		return result;
	}
	
}
