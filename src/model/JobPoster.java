package model;

public class JobPoster extends User {
	
	public JobPoster(String name, String email, String password, String type){
		super(name, email, password, type);
	}

	public JobPoster(String email, String password) {
		// TODO Auto-generated constructor stub
		super(email, password);
	}
	
}
