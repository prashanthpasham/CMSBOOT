package com.prashanth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "LOOKUP_DATA")
@Entity
public class LookUpData {
	private int lookUpDataId;
	private String dataName;
	private String parentNames;
	private int parentDataId;
	private String parentIds;
	private LookUp lookUpId;

	@Id
	@SequenceGenerator(sequenceName = "seq_lookup_data", allocationSize = 1, name = "seq_lookup_data")
	@GeneratedValue(generator = "seq_lookup_data", strategy = GenerationType.SEQUENCE)
	@Column(name = "LOOKUP_DATA_ID")
	public int getLookUpDataId() {
		return lookUpDataId;
	}

	public void setLookUpDataId(int lookUpDataId) {
		this.lookUpDataId = lookUpDataId;
	}

	@Column(name = "DATA_NAME")
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	@Column(name = "PARENT_NAME")
	public String getParentNames() {
		return parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	@Column(name = "PARENT_DATA_ID")
	public int getParentDataId() {
		return parentDataId;
	}

	public void setParentDataId(int parentDataId) {
		this.parentDataId = parentDataId;
	}

	@Column(name = "PARENT_IDS")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@ManyToOne
	@JoinColumn(name = "LOOKUP_ID")
	public LookUp getLookUpId() {
		return lookUpId;
	}

	public void setLookUpId(LookUp lookUpId) {
		this.lookUpId = lookUpId;
	}

}
