package com.prashanth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SUBJECT")
public class Subject {
	private int subjectId;
	private String subjectCode;
	private String subjectName;
	private Date entryDate;
	private String entryBy;
	private int ownerId;
	private LookUpData courseLookUPId;
	private LookUpData semLookUpId;

	@Id
	@SequenceGenerator(sequenceName = "seq_subject", allocationSize = 1, name = "seq_subject")
	@GeneratedValue(generator = "seq_subject", strategy = GenerationType.SEQUENCE)
	@Column(name = "SUBJECT_ID")
	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "SUBJECT_CODE")
	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "SUBJECT_NAME")
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Column(name = "ENTRY_DATE")
	@Temporal(TemporalType.DATE)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "ENTRY_BY")
	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@ManyToOne
	@JoinColumn(name = "COURSES_LOOKUP_ID")
	public LookUpData getCourseLookUPId() {
		return courseLookUPId;
	}

	public void setCourseLookUPId(LookUpData courseLookUPId) {
		this.courseLookUPId = courseLookUPId;
	}

	@ManyToOne
	@JoinColumn(name = "SEM_LOOKUP_ID")
	public LookUpData getSemLookUpId() {
		return semLookUpId;
	}

	public void setSemLookUpId(LookUpData semLookUpId) {
		this.semLookUpId = semLookUpId;
	}

}
