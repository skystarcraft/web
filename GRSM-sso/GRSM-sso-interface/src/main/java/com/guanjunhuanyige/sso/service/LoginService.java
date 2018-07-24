package com.guanjunhuanyige.sso.service;

import com.guanjunhuanyige.utils.TaojinResult;

public interface LoginService {

	TaojinResult userLogin(String username, String password);
	
}
