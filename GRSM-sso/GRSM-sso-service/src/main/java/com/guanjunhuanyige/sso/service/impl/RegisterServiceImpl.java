package com.guanjunhuanyige.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.guanjunhuanyige.mapper.TbUserMapper;
import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.pojo.TbUserExample;
import com.guanjunhuanyige.pojo.TbUserExample.Criteria;
import com.guanjunhuanyige.utils.TaojinResult;

/**
 * 用户注册
 * @author 落雪封尘
 *
 */
@Service
public class RegisterServiceImpl implements com.guanjunhuanyige.sso.service.RegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaojinResult checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria c = example.createCriteria();
		if (type == 1) {
			c.andUsernameEqualTo(param);
		} else if (type ==2 ) {
			c.andPhoneEqualTo(param);
		} else if (type == 3) {
			c.andEmailEqualTo(param);
		} else {
			return TaojinResult.build(400, "数据类型错误");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return TaojinResult.ok(false);
		}
		return TaojinResult.ok(true);
	}

	@Override
	public TaojinResult register(TbUser user) {
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())) {
			return TaojinResult.build(400, "用户数据不完整,注册失败");
		}
		TaojinResult result = checkData(user.getUsername(), 1);
		if (! (boolean) result.getData()) {
			return TaojinResult.build(400, "用户已经被占用,注册失败");
		}
		result = checkData(user.getPhone(), 2);
		if (! (boolean) result.getData()) {
			return TaojinResult.build(400, "手机号已经被占用,注册失败");
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		userMapper.insert(user);
		return TaojinResult.ok();
	}

}
