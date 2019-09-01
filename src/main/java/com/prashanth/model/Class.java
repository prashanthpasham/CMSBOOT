package com.prashanth.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "Class")
@Entity
public class Class {
	private int classId;
	private String name;
	private LookUpData coursesLookUp;
	private int ownerId;
	private String createdUser;
	private Date createdDate;
	private LookUpData SemLookUp;
	private Set<Student> students;

	@Id
	@SequenceGenerator(sequenceName = "seq_lookup_data", allocationSize = 1, name = "seq_lookup_data")
	@GeneratedValue(generator = "seq_lookup_data", strategy = GenerationType.SEQUENCE)
	@Column(name = "CLASS_ID")
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "COURSE_LOOKUP_DATA")
	public LookUpData getCoursesLookUp() {
		return coursesLookUp;
	}

	public void setCoursesLookUp(LookUpData coursesLookUp) {
		this.coursesLookUp = coursesLookUp;
	}

	@ManyToOne
	@JoinColumn(name = "SEM_LOOKUP_DATA")
	public LookUpData getSemLookUp() {
		return SemLookUp;
	}

	public void setSemLookUp(LookUpData semLookUp) {
		SemLookUp = semLookUp;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "CREATED_USER")
	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@ManyToMany
	@JoinTable(name = "CLASS_STUDENT_MAP", joinColumns = @JoinColumn(name = "CLASS_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
