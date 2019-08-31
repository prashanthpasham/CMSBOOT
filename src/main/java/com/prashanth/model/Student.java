package com.prashanth.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT")
public class Student {
	private int studentId;
	private String rollNo;
	private String studentName;
	private String gender;
	private String phoneNo;
	private Set<Address> addressSet;
	private byte[] photo;
	private Date joinedDate;
	private String parentName;
	private String parentPhoneNo;
	private Set<Class> classes;
	private int YearOfPass;

	@Id
	@SequenceGenerator(sequenceName = "seq_student", allocationSize = 1, name = "seq_student")
	@GeneratedValue(generator = "seq_student", strategy = GenerationType.SEQUENCE)
	@Column(name = "CLASS_ID")
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	@Column(name = "ROLL_NO")
	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	@Column(name = "STUDENT_NAME")
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@OneToMany(mappedBy = "student")
	public Set<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
	}

	@Column(name = "photo")
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Column(name = "JOINED_DATE")
	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	@Column(name = "PARENT_NAME")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "PARENT_PHONE_NO")
	public String getParentPhoneNo() {
		return parentPhoneNo;
	}

	public void setParentPhoneNo(String parentPhoneNo) {
		this.parentPhoneNo = parentPhoneNo;
	}

	@ManyToMany(mappedBy = "students")
	public Set<Class> getClasses() {
		return classes;
	}

	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}

	@Column(name = "YEAR_OF_PASS")
	public int getYearOfPass() {
		return YearOfPass;
	}

	public void setYearOfPass(int yearOfPass) {
		YearOfPass = yearOfPass;
	}

}
