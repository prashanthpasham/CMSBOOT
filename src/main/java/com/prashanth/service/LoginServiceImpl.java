package com.prashanth.service;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashanth.dao.LoginDaoIntf;

@Service
@Transactional
public class LoginServiceImpl implements LoginServiceIntf {
	@Autowired
	private LoginDaoIntf loginDaoIntf;

	@Override
	public JSONObject validateLogin(JSONObject login) {
		// TODO Auto-generated method stub
		return loginDaoIntf.validateLogin(login);
	}

	public LoginDaoIntf getLoginDaoIntf() {
		return loginDaoIntf;
	}

	public void setLoginDaoIntf(LoginDaoIntf loginDaoIntf) {
		this.loginDaoIntf = loginDaoIntf;
	}

}
