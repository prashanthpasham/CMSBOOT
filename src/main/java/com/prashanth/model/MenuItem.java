package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MENU_ITEM")
public class MenuItem {
	private int menuItemId;
	private String menuItemTitle;
	private String menuItemUrl;
	private int menuOrder;
	private int parentMenuId;

	public MenuItem() {

	}

	public MenuItem(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENU_ITEM")
	@SequenceGenerator(name = "SEQ_MENU_ITEM", sequenceName = "SEQ_MENU_ITEM", allocationSize = 1)
	@Column(name = "MENU_ITEM_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getMenuItemId() {
		return this.menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	@Column(name = "MENU_ITEM_TITLE", length = 104)
	public String getMenuItemTitle() {
		return this.menuItemTitle;
	}

	public void setMenuItemTitle(String menuItemTitle) {
		this.menuItemTitle = menuItemTitle;
	}

	@Column(name = "MENU_ITEM_URL")
	public String getMenuItemUrl() {
		return menuItemUrl;
	}

	public void setMenuItemUrl(String menuItemUrl) {
		this.menuItemUrl = menuItemUrl;
	}

	@Column(name = "MENU_ORDER")
	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	@Column(name = "PARENT_MENU_ID")
	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

}
