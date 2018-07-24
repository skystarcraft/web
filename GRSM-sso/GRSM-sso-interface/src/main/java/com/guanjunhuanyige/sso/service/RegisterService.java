package com.guanjunhuanyige.sso.service;

import com.guanjunhuanyige.pojo.TbUser;
import com.guanjunhuanyige.utils.TaojinResult;

public interface RegisterService {

	TaojinResult checkData(String param, int type);
	
	TaojinResult register(TbUser user);
	
}
