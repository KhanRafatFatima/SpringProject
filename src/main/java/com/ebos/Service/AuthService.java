package com.ebos.Service;

import java.util.Map;

import com.ebos.Request.LoginRequest;

public interface AuthService {
	
	public Map<String,Object> studentLogin(LoginRequest Req);
	

}
