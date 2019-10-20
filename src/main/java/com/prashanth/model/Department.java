package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
	private int departmentId;
	private String deptCode;
	private String departmentName;
	private int ownerId;

	public Department() {

	}

	public Department(int departmentId) {
		this.departmentId = departmentId;
	}

	@Id
	@SequenceGenerator(sequenceName = "seq_department", allocationSize = 1, name = "seq_department")
	@GeneratedValue(generator = "seq_department", strategy = GenerationType.SEQUENCE)
	@Column(name = "DEPARTMENT_ID")
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "DEPARTMENT_CODE")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "DEPARTMENT_NAME")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
