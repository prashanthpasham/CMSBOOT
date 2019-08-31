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
@Table(name = "FACULTY_TO_CLASS")
public class FacultyToClass {
	private int FacultyClassId;
	private Student student;
	private Date assignedDate;
	private String entryBy;
	private int ownerId;
	private Class classId;
	private Employee faculty;

	@Id
	@SequenceGenerator(sequenceName = "seq_Faculty_Class", allocationSize = 1, name = "seq_Faculty_Class")
	@GeneratedValue(generator = "seq_Faculty_Class", strategy = GenerationType.SEQUENCE)
	@Column(name = "FACULTY_CLASS_ID")
	public int getFacultyClassId() {
		return FacultyClassId;
	}

	/**
	 * @param facultyClassId the facultyClassId to set
	 */
	public void setFacultyClassId(int facultyClassId) {
		FacultyClassId = facultyClassId;
	}

	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "ASSIGNED_DATE")
	public Date getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate the assignedDate to set
	 */
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	@Column(name = "ENTRY_BY")
	public String getEntryBy() {
		return entryBy;
	}

	/**
	 * @param entryBy the entryBy to set
	 */
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
	@JoinColumn(name = "CLASS_ID")
	public Class getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(Class classId) {
		this.classId = classId;
	}

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getFaculty() {
		return faculty;
	}

	public void setFaculty(Employee faculty) {
		this.faculty = faculty;
	}

}
