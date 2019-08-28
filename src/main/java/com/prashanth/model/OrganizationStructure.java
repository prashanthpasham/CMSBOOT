package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Organization_Structure")
public class OrganizationStructure {
	private long organizationId;
	private String name;
	private String hierarchy;
	private String parentHierarchy;
	private String parentName;
	private String parentNames;
	private int ownerId;

	@Id
	@SequenceGenerator(sequenceName = "seq_organization", allocationSize = 1, name = "seq_organization")
	@GeneratedValue(generator = "seq_organization", strategy = GenerationType.SEQUENCE)
	@Column(name = "organization_id")
	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "hierachy")
	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	@Column(name = "parent_hierarchy")
	public String getParentHierarchy() {
		return parentHierarchy;
	}

	public void setParentHierarchy(String parentHierarchy) {
		this.parentHierarchy = parentHierarchy;
	}

	@Column(name = "parent_name")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "parent_names")
	public String getParentNames() {
		return parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
