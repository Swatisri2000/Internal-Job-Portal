package service;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.List;
//import java.util.Scanner;
//import model.AppliedJobs;
//import model.JobPoster;
import model.JobSeeker;
import utility.ConnectionUtility;

public class JobSeekerService {

	private JobSeeker jobSeeker = null;
	
	private ConnectionUtility connectionUtility;
	public JobSeekerService(ConnectionUtility connectionUtility) {
		this.connectionUtility = connectionUtility;
	}
	
	public boolean signup(String name, String email, String password, String confirmPassword) throws SQLException {
		String query = "select * from user where email = ?";
		
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, email);
		
		
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
		statement.setString(4, "Job seeker");
		
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("Job Seeker Signup Successfull... !");
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
		
		this.jobSeeker = new JobSeeker(rs.getString("name") , email, password);
		System.out.println("Job Seeker Signin Successfull... !");
		return true;
	}
	
	public void displayJobs() throws SQLException {
		if(jobSeeker == null) {
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
	
	public boolean appliedJobs(int jobId) throws SQLException {
		if(jobSeeker == null) {
			System.out.println("Signin first");
			return false;
		}
		
		String query = "select * from applied_jobs where applied_job_id = ? and email = ?";
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, jobId);
		statement.setString(2, this.jobSeeker.getEmail());
		
		ResultSet rs = statement.executeQuery();
		if(rs.next()) {
			System.out.println("You already applied to this job");
			return false;
		}
		
		query = "select * from jobs where job_id = ?";
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, jobId);
//		Statement st = this.connectionUtility.conn.createStatement();
		rs = statement.executeQuery();
		rs.next();
		
		query = "insert into applied_jobs(name, email, job_category, job_role, company_name) values(?, ?, ?, ?, ?)";
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, this.jobSeeker.getName());
		statement.setString(2, this.jobSeeker.getEmail());
		statement.setString(3, rs.getString("job_category"));
		statement.setString(4, rs.getString("job_role"));
		statement.setString(5, rs.getString("company_name"));
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("Job applied Successfully... !");
		    return true;
		} else {
			System.out.println("Unbale to apply for job... !");
			return false;
		}
	}
	
}

