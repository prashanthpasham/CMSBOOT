package com.prashanth.dao;

import org.springframework.data.repository.CrudRepository;

import com.prashanth.model.Address;
import com.prashanth.model.MangementInfoDetails;
public interface ManagementInfoDaoIntf extends CrudRepository<MangementInfoDetails,Long> {

}
