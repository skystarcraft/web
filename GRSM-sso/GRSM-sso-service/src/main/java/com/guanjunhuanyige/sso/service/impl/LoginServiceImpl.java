package com.guanjunhuanyige.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.guanjunhuanyige.jedis.JedisClient;
import com.guanjunhuanyige.mapper.TbUserMapper;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.pojo.TbUserExample;
import com.guanjunhuanyige.pojo.TbUserExample.Criteria;
import com.guanjunhuanyige.sso.service.LoginService;
import com.guanjunhuanyige.utils.JsonUtils;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 登陆处理
 * @author 落雪封尘
 *
 */
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${SESSION_TIME}")
	private Integer SESSION_TIME;
	
	@Override
	public TaojinResult userLogin(String username, String password) {
		TbUserExample example = new TbUserExample();
		Criteria c = example.createCriteria();
		c.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return TaojinResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaojinResult.build(400, "用户名或密码错误");
		}
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
		jedisClient.expire("SESSION:" + token, SESSION_TIME);
		return TaojinResult.ok(token);
	}

}
