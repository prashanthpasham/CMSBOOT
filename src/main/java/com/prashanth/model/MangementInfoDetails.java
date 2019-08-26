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
import javax.persistence.Transient;

@Table(name = "Management_Info_Details")
@Entity
public class MangementInfoDetails {
	private long manageInfoId;
	private String name;
	private String description;
	private byte[] logo;
	private Address address;

	@Id
	@SequenceGenerator(sequenceName = "seq_manage_info", allocationSize = 1, name = "seq_manage_info")
	@GeneratedValue(generator = "seq_manage_info", strategy = GenerationType.SEQUENCE)
	@Column(name = "MANAGE_INFO_ID")
	public long getManageInfoId() {
		return manageInfoId;
	}

	public void setManageInfoId(long manageInfoId) {
		this.manageInfoId = manageInfoId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "LOGO")
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	/*
	 * @JoinColumn(name = "ADDRESS_ID")
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY, targetEntity = Address.class)
	 */
	@Transient
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
