package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE_MENU_MAP")
public class RoleMenuMap {

	private int roleMenuItemMapId;
	private Role role;
	private MenuItem menuItem;
	private int addOperation;
	private int editOperation;
	private int deleteOperation;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ROLE_MENU_ITEM_MAP")
	@SequenceGenerator(name = "SEQ_ROLE_MENU_ITEM_MAP", sequenceName = "SEQ_ROLE_MENU_ITEM_MAP")
	@Column(name = "ROLE_MENU_ITEM_MAP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getRoleMenuItemMapId() {
		return this.roleMenuItemMapId;
	}

	public void setRoleMenuItemMapId(int roleMenuItemMapId) {
		this.roleMenuItemMapId = roleMenuItemMapId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MENU_ITEM_ID")
	public MenuItem getMenuItem() {
		return this.menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Column(name = "ADD_OPERATION", precision = 22, scale = 0)
	public int getAddOperation() {
		return this.addOperation;
	}

	public void setAddOperation(int addOperation) {
		this.addOperation = addOperation;
	}

	@Column(name = "EDIT_OPERATION", precision = 22, scale = 0)
	public int getEditOperation() {
		return this.editOperation;
	}

	public void setEditOperation(int editOperation) {
		this.editOperation = editOperation;
	}

	@Column(name = "DELETE_OPERATION", precision = 22, scale = 0)
	public int getDeleteOperation() {
		return this.deleteOperation;
	}

	public void setDeleteOperation(int deleteOperation) {
		this.deleteOperation = deleteOperation;
	}

}
