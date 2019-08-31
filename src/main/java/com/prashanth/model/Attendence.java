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

@Entity
@Table(name = "STUDENT_ATTENDENCE")
public class Attendence {
	private int attendenceId;
	private Subject subjectId;
	private Date attendenceDate;
	private Employee entryBy;
	private Date entryDate;
	private Class classId;
	private int attendent;

	@Id
	@SequenceGenerator(sequenceName = "seq_attendence", allocationSize = 1, name = "seq_attendence")
	@GeneratedValue(generator = "seq_attendence", strategy = GenerationType.SEQUENCE)
	@Column(name = "ATTENDENCE_ID")
	public int getAttendenceId() {
		return attendenceId;
	}

	/**
	 * @param attendenceId the attendenceId to set
	 */
	public void setAttendenceId(int attendenceId) {
		this.attendenceId = attendenceId;
	}

	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID")
	public Subject getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Subject subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "ATTENDENCE_DATE")
	public Date getAttendenceDate() {
		return attendenceDate;
	}

	/**
	 * @param attendenceDate the attendenceDate to set
	 */
	public void setAttendenceDate(Date attendenceDate) {
		this.attendenceDate = attendenceDate;
	}

	@ManyToOne
	@JoinColumn(name = "ENTRY_BY")
	public Employee getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(Employee entryBy) {
		this.entryBy = entryBy;
	}

	@Column(name = "ENTRY_DATE")
	public Date getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@ManyToOne
	@JoinColumn(name = "CLASS")
	public Class getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Class classId) {
		this.classId = classId;
	}

	@Column(name = "ATTENDENT")
	public int getAttendent() {
		return attendent;
	}

	/**
	 * @param attendent the attendent to set
	 */
	public void setAttendent(int attendent) {
		this.attendent = attendent;
	}

}
