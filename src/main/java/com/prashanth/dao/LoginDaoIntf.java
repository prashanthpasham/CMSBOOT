package com.prashanth.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.prashanth.model.OrganizationStructure;
import com.prashanth.model.Role;
import com.prashanth.model.Users;

public interface LoginDaoIntf {
	public JSONObject validateLogin(JSONObject login);
	public Users findUserByName(String userName);
	public JSONObject menusByUserName(String userName);
	public JSONObject persistRole(Role r);
	public JSONObject saveOrgChart(List<OrganizationStructure> orgChart, int ownerId);
	public JSONObject fetchOrgChart(int ownerId);
	public JSONObject getDesignations(int ownerId);
	public JSONObject saveDepartment(JSONObject deptObj);
	public JSONObject departmentList(int ownerId);
	public JSONObject deleteDepartment(int deptId);
}
