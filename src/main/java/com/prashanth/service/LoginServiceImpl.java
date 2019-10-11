package com.prashanth.service;

import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashanth.dao.LoginDaoIntf;
import com.prashanth.model.OrganizationStructure;
import com.prashanth.model.Role;

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

	@Override
	public JSONObject menusByUserName(String userName) {
		// TODO Auto-generated method stub
		return loginDaoIntf.menusByUserName(userName);
	}

	public LoginDaoIntf getLoginDaoIntf() {
		return loginDaoIntf;
	}

	public void setLoginDaoIntf(LoginDaoIntf loginDaoIntf) {
		this.loginDaoIntf = loginDaoIntf;
	}

	@Override
	public JSONObject persistRole(Role r) {
		// TODO Auto-generated method stub
		return loginDaoIntf.persistRole(r);
	}

	@Override
	public JSONObject saveOrgChart(List<OrganizationStructure> orgChart, int ownerId) {
		// TODO Auto-generated method stub
		return loginDaoIntf.saveOrgChart(orgChart, ownerId);
	}

	@Override
	public JSONObject fetchOrgChart(int ownerId) {
		// TODO Auto-generated method stub
		return loginDaoIntf.fetchOrgChart(ownerId);
	}

}
