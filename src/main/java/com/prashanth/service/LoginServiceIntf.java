package com.prashanth.service;

import org.json.simple.JSONObject;

public interface LoginServiceIntf {
public JSONObject validateLogin(JSONObject login);
public JSONObject menusByUserName(String userName);
}
