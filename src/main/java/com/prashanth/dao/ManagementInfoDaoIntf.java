package com.prashanth.dao;

import org.springframework.data.repository.CrudRepository;

import com.prashanth.model.Address;
public interface ManagementInfoDaoIntf extends CrudRepository<Address,Long> {

}
