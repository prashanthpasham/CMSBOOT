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

	@Override
	public JSONObject getDesignations(int ownerId) {
		// TODO Auto-generated method stub
		return loginDaoIntf.getDesignations(ownerId);
	}

	@Override
	public JSONObject saveDepartment(JSONObject deptObj) {
		// TODO Auto-generated method stub
		return loginDaoIntf.saveDepartment(deptObj);
	}

	@Override
	public JSONObject departmentList(int ownerId) {
		// TODO Auto-generated method stub
		return loginDaoIntf.departmentList(ownerId);
	}
	
	@Override
	public JSONObject deleteDepartment(int deptId) {
		return loginDaoIntf.deleteDepartment(deptId);
	}

	@Override
	public JSONObject saveEmployee(JSONObject empObj) {
		// TODO Auto-generated method stub
		return loginDaoIntf.saveEmployee(empObj);
	}

	@Override
	public JSONObject employeeList(JSONObject empObj) {
		// TODO Auto-generated method stub
		return loginDaoIntf.employeeList(empObj);
	}

}
