package model;

public class AppliedJobs {
	
	private String userName, email, jobCategory, jobRole, companyName;
	private int phone;
	
	public AppliedJobs(String userName, String email, String jobCategory, String jobRole, String companyName) {
		this.userName = userName;
		this.email = email;
		this.jobCategory = jobCategory;
		this.jobRole = jobRole;
		this.companyName = companyName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
	public String getJobRole() {
		return jobRole;
	}
	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
}
