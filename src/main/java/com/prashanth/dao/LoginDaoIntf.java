package com.prashanth.dao;

import org.json.simple.JSONObject;

import com.prashanth.model.Users;

public interface LoginDaoIntf {
	public JSONObject validateLogin(JSONObject login);
	public Users findUserByName(String userName);
}
