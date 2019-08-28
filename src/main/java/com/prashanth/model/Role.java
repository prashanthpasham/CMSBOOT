package com.prashanth.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "Role")
@Entity
public class Role {
	private int roleId;
	private String role;
	private String roleDescription;
	private Date createdDateTime;
	private Date modifiedDateTime;
	private String createdUserName;
	private String modifiedUserName;
	private int ownerId;
	private Set<RoleMenuMap> roleMenus;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE")
	@SequenceGenerator(name = "SEQ_ROLE", sequenceName = "SEQ_ROLE", allocationSize = 1)
	@Column(name = "ROLE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE", length = 104)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "ROLE_DESCRIPTION", length = 504)
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(name = "CREATED_DATE_TIME", length = 7)
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Column(name = "MODIFIED_DATE_TIME", length = 7)
	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Column(name = "CREATED_USER_NAME", length = 80)
	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	@Column(name = "MODIFIED_USER_NAME", length = 80)
	public String getModifiedUserName() {
		return modifiedUserName;
	}

	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@OneToMany(mappedBy = "role")
	public Set<RoleMenuMap> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(Set<RoleMenuMap> roleMenus) {
		this.roleMenus = roleMenus;
	}

}
