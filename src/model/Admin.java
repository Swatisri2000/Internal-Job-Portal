package model;



public class Admin extends User{
	public Admin(String name, String email, String password, String type){
		super(name, email, password, type);
	}

	public Admin(String email, String password) {
		super(email, password);
	}
}
