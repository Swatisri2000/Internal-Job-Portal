package service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Admin;

import utility.ConnectionUtility;

public class AdminService {
	
	private ConnectionUtility connectionUtility;
	private Admin admin = null;
	
	public AdminService(ConnectionUtility connectionUtility) {
		this.connectionUtility = connectionUtility;
	}
	
	public boolean signup(String name, String email, String password, String confirmPassword) throws Exception{
		String query = "select type from user where type = 'admin'";
		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		int count = 0;
		while(rs.next()) {
			System.out.println(rs.next());
			// this will run for each row
			count++;
		}
		if(count > 0) {
			System.out.println("Admin exists already");
			return false;
		}
		if(!email.matches("[A-Za-z0-9]+@[A-Za-z]+\\.[A-za-z]{3}")) {
			System.out.println("Invalid Email");
			return false;
		}
		
		boolean containsUppercase = password.matches(".*[A-Z]+.*");
		boolean containsLowercase = password.matches(".*[a-z]+.*");
		boolean containsDigit = password.matches(".*[0-9]+.*");
		boolean containsSpecialCharacter = password.matches(".*[!@#$%^&*?/.]+.*");
		boolean hasLength = password.length() >= 8;
		if(!containsDigit || !containsLowercase || !containsUppercase || !containsSpecialCharacter || !hasLength) {
			System.out.println("Invalid Password");
			return false;
		}
		
		if(!password.matches(confirmPassword)) {
			System.out.println("Password and confirm password doesnot match...");
			return false;
		}
		
		query = "insert into user(name, email, password, type) values(?, ?, ?, ?)"; 
		 
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, email);
		statement.setString(3, password);
		statement.setString(4, "admin");
		
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("Admin Signup Successfull... !");
		    return true;
		}
		return false;
	}
	
	public boolean signin(String email, String password) throws SQLException {
		
		// Check for email and password is present or not
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
		
		
		
		// if both email and password exists then i will create an admin object where we can keep the details, which will help for further operations.
		this.admin = new Admin(email, password);
		System.out.println("Admin Signin Successfull... !");
		return true;
		
	}
	
	public void displayUsers() throws SQLException {
		if(admin == null) {
			System.out.println("Cannot display users without Signin... !");
			return;
		}
		String query = "select * from user where type <> 'admin'";
		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		
		while(rs.next()) {
			int userId = rs.getInt(1);
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String type = rs.getString("type");
			String output = "%d: %s - %s - %s - %s";
			System.out.println(String.format(output, userId, name, email, password, type));
		}
	}
	
	public void deleteUser(int userId) throws SQLException {
		if(admin == null) {
			System.out.println("Cannot delete users without Signin... !");
			return;
		}
		
		String query = "select * from user where user_id = ?";
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, userId);
		
//		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = statement.executeQuery();
		if(!rs.next()) {
			System.out.println("User Doesnot exist... !");
			return;
		}
		query = "delete from user where user_id = ?";
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, userId);
		
		int rowsDeleted = statement.executeUpdate();
		if (rowsDeleted > 0) {
		    System.out.println("A user is deleted successfully!");
		}else {
			System.out.println("Cannot delete User... !");
		}
		
	}
	
	public void displayJobs() throws SQLException {
		if(admin == null) {
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
	
	public void deleteJobs(int jobId) throws SQLException {
		if(admin == null) {
			System.out.println("Cannot delete job without Signin... !");
			return;
		}
		
		String query = "select * from jobs where job_id = ?";
		PreparedStatement statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, jobId);
		
//		Statement st = this.connectionUtility.conn.createStatement();
		ResultSet rs = statement.executeQuery();
		if(!rs.next()) {
			System.out.println("Job Doesnot exist... !");
			return;
		}
		query = "delete from jobs where job_id = ?";
		statement = this.connectionUtility.conn.prepareStatement(query);
		statement.setInt(1, jobId);
		
		int rowsDeleted = statement.executeUpdate();
		if (rowsDeleted > 0) {
		    System.out.println("A Job is deleted successfully!");
		}
	}
}
