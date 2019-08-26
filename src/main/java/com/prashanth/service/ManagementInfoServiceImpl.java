package com.prashanth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashanth.dao.ManagementInfoDaoIntf;
import com.prashanth.model.Address;
@Service
public class ManagementInfoServiceImpl implements ManagementInfoService {
@Autowired
private ManagementInfoDaoIntf managementInfoDaoIntf;
	@Override
	public String saveManagementInfoDetails(Address address) {
		// TODO Auto-generated method stub
		return managementInfoDaoIntf.save(address).getAddressId()+"";
	}
	public ManagementInfoDaoIntf getManagementInfoDaoIntf() {
		return managementInfoDaoIntf;
	}
	public void setManagementInfoDaoIntf(ManagementInfoDaoIntf managementInfoDaoIntf) {
		this.managementInfoDaoIntf = managementInfoDaoIntf;
	}

}
