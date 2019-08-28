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
@Table(name = "EMPLOYEE_DETAILS")
public class EmployeeDetails {
	private int empDetailsId;
	private String Institute;
	private Date startYear;
	private Date endYear;
	private String type;
	private Employee employee;

	@Id
	@SequenceGenerator(sequenceName = "seq_employee_details", allocationSize = 1, name = "seq_employee_details")
	@GeneratedValue(generator = "seq_employee_details", strategy = GenerationType.SEQUENCE)
	@Column(name = "EMP_DETAILS_ID")
	public int getEmpDetailsId() {
		return empDetailsId;
	}

	public void setEmpDetailsId(int empDetailsId) {
		this.empDetailsId = empDetailsId;
	}

	@Column(name = "INSTITUTE")
	public String getInstitute() {
		return Institute;
	}

	public void setInstitute(String institute) {
		Institute = institute;
	}

	@Column(name = "START_YEAR")
	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	@Column(name = "END_YEAR")
	public Date getEndYear() {
		return endYear;
	}

	public void setEndYear(Date endYear) {
		this.endYear = endYear;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
