package com.guanjunhuanyige.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/*
 * 根据token取用户信息
 */
@Service
public class TokenServiceImpl implements com.guanjunhuanyige.sso.service.TokenService {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${SESSION_TIME}")
	private Integer SESSION_TIME;
	
	@Override
	public TaojinResult getUserByToken(String token) {
		String json = jedisClient.get("SESSION:" + token);
		if (StringUtils.isBlank(json)){
			return TaojinResult.build(201, "用户登录已过期");
		}
		jedisClient.expire("SESSION:" + token, SESSION_TIME);
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return TaojinResult.ok(user);
	}

}
