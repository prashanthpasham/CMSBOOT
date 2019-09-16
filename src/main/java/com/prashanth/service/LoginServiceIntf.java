package com.prashanth.service;

import org.json.simple.JSONObject;

import com.prashanth.model.Role;

public interface LoginServiceIntf {
public JSONObject validateLogin(JSONObject login);
public JSONObject menusByUserName(String userName);
public JSONObject persistRole(Role r);
}
