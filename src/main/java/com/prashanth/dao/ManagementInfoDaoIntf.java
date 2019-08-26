package com.prashanth.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prashanth.model.Address;
import com.prashanth.model.MangementInfoDetails;
@Repository
public interface ManagementInfoDaoIntf extends CrudRepository<Address,Long> {

}
