package com.prashanth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "CLASS_TO_STUDENT")
@Entity
public class ClassToStudent {
	private int classStudentId;
	private int classId;
	private int studentId;
	private int ownerId;
	private String assignedUser;
	private Date assignedDate;

	@Id
	@SequenceGenerator(sequenceName = "seq_class_student", allocationSize = 1, name = "seq_class_student")
	@GeneratedValue(generator = "seq_class_student", strategy = GenerationType.SEQUENCE)
	@Column(name = "CLASS_STUDENT_ID")
	public int getClassStudentId() {
		return classStudentId;
	}

	public void setClassStudentId(int classStudentId) {
		this.classStudentId = classStudentId;
	}

	@Column(name = "CLASS_ID")
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Column(name = "STUDENT_ID")
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "ASSIGNED_USER")
	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	@Column(name = "ASSIGNED_DATE")
	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

}
