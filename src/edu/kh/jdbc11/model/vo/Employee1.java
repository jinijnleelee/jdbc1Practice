package edu.kh.jdbc11.model.vo;

	public class Employee1 {
	private String empName;
	private String jobName;
	private int salary;
	private int annualIncome;
	private String hireDate;
	private String Gender;
	
	public Employee1() {}
	
	
	public Employee1(String empName, String jobName, int salary, int annualIncome) {
		super();
		this.empName = empName;
		this.jobName = jobName;
		this.salary = salary;
		this.annualIncome = annualIncome;
		
}
	
	
	public Employee1(String empName, String hireDate, String gender) {
		super();
		this.empName = empName;
		this.hireDate = hireDate;
		this.Gender = gender;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}
	
	

	public String getHireDate() {
		return hireDate;
	}


	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}


	public String getGender() {
		return Gender;
	}


	public void setGender(String gender) {
		this.Gender = gender;
	}


	@Override
	public String toString() {
		return  empName + " /  " + jobName + " / " + salary + " / "
				+ annualIncome ;
	}
	
	
	
	public String toString(String empString,String hireDate,String Gender) {
		return  empName + " /  " + hireDate + " / " + Gender;
	}
	
	
	
	
	

	
}
