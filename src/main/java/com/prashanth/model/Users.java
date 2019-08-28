package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "users")
@Entity
public class Users {

	private long userId;
	private String userName;
	private String password;
	private String status;
	private long empId;
	private Role roleId;
	private long ownerId;
	private OrganizationStructure designation;
	private int reportingUser;

	@Id
	@SequenceGenerator(sequenceName = "seq_users", allocationSize = 1, name = "seq_users")
	@GeneratedValue(generator = "seq_users", strategy = GenerationType.SEQUENCE)
	@Column(name = "USER_ID")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD")

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATUS")

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "EMP_ID")

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	@Column(name = "owner_id")
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@OneToOne
	@JoinColumn(name = "DESIGNATION_ID")
	public OrganizationStructure getDesignation() {
		return designation;
	}

	public void setDesignation(OrganizationStructure designation) {
		this.designation = designation;
	}

	@Column(name = "REPORTING_USER")
	public int getReportingUser() {
		return reportingUser;
	}

	public void setReportingUser(int reportingUser) {
		this.reportingUser = reportingUser;
	}

}
