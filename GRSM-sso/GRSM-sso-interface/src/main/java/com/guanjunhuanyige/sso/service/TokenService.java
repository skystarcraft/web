package com.guanjunhuanyige.sso.service;

import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 根据token查询用户信息
 * @author 落雪封尘
 *
 */
public interface TokenService {

	TaojinResult getUserByToken(String token);
	
}
