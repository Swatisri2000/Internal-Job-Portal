package service;

import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//import model.Admin;
import model.JobPoster;
import utility.ConnectionUtility;
//import model.JobPoster;


public class JobPosterService {

	private JobPoster jobPoster = null;
	
	private ConnectionUtility connectionUtility;
	public JobPosterService(ConnectionUtility connectionUtility) {
		this.connectionUtility = connectionUtility;
	}
	
	public boolean signup(String name, String email, String password, String confirmPassword) throws SQLException {
		String query = "select * from user where email = ?";
		
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, email);
		
//		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = statement.executeQuery();
		
//		checks that email is already exist or not
		if(rs.next()) {   // if email exists then if block will run... 
			System.out.println("Email already Exists... !");
			return false;
		}
		
//		if email does not exist then check for proper syntax of email
		if(!email.matches("[A-Za-z0-9]+@[A-Za-z]+\\.[A-za-z]{3}")) {
			System.out.println("Invalid Email");
			return false;
		}
		
//		check for valid password or not...
		boolean containsUppercase = password.matches(".*[A-Z]+.*");
		boolean containsLowercase = password.matches(".*[a-z]+.*");
		boolean containsDigit = password.matches(".*[0-9]+.*");
		boolean containsSpecialCharacter = password.matches(".*[!@#$%^&*?/.]+.*");
		boolean hasLength = password.length() >= 8;
		if(!containsDigit || !containsLowercase || !containsUppercase || !containsSpecialCharacter || !hasLength) {
			System.out.println("Invalid Password");
			return false;
		}
		
//		check for confirm password...
		if(!password.matches(confirmPassword)) {
			System.out.println("Password and confirm password doesnot match...");
			return false;
		}
		
		query = "insert into user(name, email, password, type) values(?, ?, ?, ?)"; 
		 
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, email);
		statement.setString(3, password);
		statement.setString(4, "Job poster");
		
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("Job poster Signup Successfull... !");
		    return true;
		}
		return false;
	}
	
	public boolean signin(String email, String password) throws SQLException {
		String query = "select * from user where email = ?";
		
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, email);
		
//		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = statement.executeQuery();
		
		if(!rs.next()) {
			System.out.println("Email doesnot exist..");
			return false;
		}
		
		String passwordFetched = rs.getString("password");
		if(!passwordFetched.equals(password)) {
			System.out.println("Invallid password");
			return false;
		}
		
		this.jobPoster = new JobPoster(email, password);
		System.out.println("Job Poster Signin Successfull... !");
		return true;
	}
	
	public void displayJobs() throws SQLException {
		if(jobPoster == null) {
			System.out.println("Cannot display jobs without Signin... !");
			return;
		}
		String query = "select * from jobs";
		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			String companyName = rs.getString("company_name");
			String jobRole = rs.getString("job_role");
			String jobCategory = rs.getString("job_category");
			float experience = rs.getFloat("experience");
			String location = rs.getString("location");
			int salary = rs.getInt("salary");
			int jobId = rs.getInt(1);
			String output = "%d: %s - %s - %s - %f - %s - %d";
			System.out.println(String.format(output, jobId, companyName, jobRole, jobCategory, experience, location, salary, output));
		}
	}
		
	
	public boolean postJobs(String companyName, String jobCategory, String jobRole, Float experience, String location, int salary) throws SQLException {
		String query = "select * from jobs where company_name = ? and job_category = ? and job_role = ? and experience = ? and location = ? and salary = ?";
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, companyName);
		statement.setString(2, jobCategory);
		statement.setString(3, jobRole);
		statement.setFloat(4, experience);	 
		statement.setString(5, location);
		statement.setInt(6, salary);
		
//		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = statement.executeQuery();
		
		if(rs.next()) {
			System.out.println("This job already exist... !");
			return false;
		}
		query = "insert into jobs(company_name, job_category, job_role, experience, location, salary) values(?, ?, ?, ? , ?, ?)";
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, companyName);
		statement.setString(2, jobCategory);
		statement.setString(3, jobRole);
		statement.setFloat(4, experience);	 
		statement.setString(5, location);
		statement.setInt(6, salary);
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			   System.out.println("Job is uploaded successfully!");
			   return true;
		}else {
			System.out.println("Cannot upload Job... !");
			return false;
		}
	}
	
	public void displayAppliedJobs() throws SQLException {
		if(jobPoster == null) {
			System.out.println("Cannot display Applied jobs by users without Signin... !");
			return;
		}
		String query = "select * from applied_jobs";
		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = st.executeQuery(query);
			
		while(rs.next()) {
			int appliedJobId = rs.getInt(1);
			String name = rs.getString("name");
			String email = rs.getString("email");
			String jobCategory = rs.getString("job_category");
			String jobRole = rs.getString("job_role");
			String companyName = rs.getString("company_name");
			String output = "%d: %s - %s - %s - %s - %s";
			System.out.println(String.format(output, appliedJobId, name, email, jobCategory, jobRole, companyName));
			}
		}
}
