package com.prashanth.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "EMPLOYEE", uniqueConstraints = @UniqueConstraint(columnNames = { "employee_Code" }))
public class Employee {
	private int employeeId;
	private int employeeCode;
	private String employeeName;
	private String createdUser;
	private Date createdDate;
	private Date joinedDate;
	private int ownerId;
	private OrganizationStructure designation;
	private Set<Address> addressSet;
	private long phoneNumber;
	private double salary;
	private String qualification;
	private String epfno;
	private String panCard;
	private String aadharCard;
	private String experience;
	private Set<EmployeeDetails> employeeDetailsSet;
	private Department department;
	private String employeeType;

	@Id
	@SequenceGenerator(sequenceName = "seq_employee", allocationSize = 1, name = "seq_employee")
	@GeneratedValue(generator = "seq_employee", strategy = GenerationType.SEQUENCE)
	@Column(name = "EMPLOYEE_ID")
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "EMPLOYEE_CODE")
	public int getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(int employeeCode) {
		this.employeeCode = employeeCode;
	}

	@Column(name = "EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	@Column(name = "JOINED_DATE")
	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	@Column(name = "OWNER_ID")
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@ManyToOne(targetEntity = OrganizationStructure.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "DESIGNATION_ID")
	public OrganizationStructure getDesignation() {
		return designation;
	}

	public void setDesignation(OrganizationStructure designation) {
		this.designation = designation;
	}

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ADDRESS_ID")
	public Set<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
	}

	@Column(name = "PHONE_NUMBER")
	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "SALARY")
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Column(name = "QUALIFICATION")
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Column(name = "EPF_NO")
	public String getEpfno() {
		return epfno;
	}

	public void setEpfno(String epfno) {
		this.epfno = epfno;
	}

	@Column(name = "PAN_CARD")
	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	@Column(name = "AADHAR_CARD")
	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	@Column(name = "experience")
	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "EMPLOYEE_ID")
	public Set<EmployeeDetails> getEmployeeDetailsSet() {
		return employeeDetailsSet;
	}

	public void setEmployeeDetailsSet(Set<EmployeeDetails> employeeDetailsSet) {
		this.employeeDetailsSet = employeeDetailsSet;
	}

	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "EMPLOYEE_TYPE")
	public String getEmployeeType() {
		/* Teaching Staff ,Non-Teaching Staff */
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

}
