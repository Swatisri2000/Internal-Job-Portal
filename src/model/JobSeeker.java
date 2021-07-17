package model;

public class JobSeeker extends User {

	public JobSeeker(String name, String email, String password, String type){
		super(name, email, password, type);
	}

	public JobSeeker(String email, String password) {
		
		super(email, password);
	}

	public JobSeeker(String name, String email, String password) {
		
		super(name,  email, password);
	}
	
}
