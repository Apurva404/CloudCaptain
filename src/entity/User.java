package entity;

import constants.EntityConstants;

public class User {
	private int userId;
	private String fullname;
	private String emailId;
	private String phone;
	private String company;
	private String password;
	
	
	public User( String fullnameIn, String passwordIn) {
		userId = EntityConstants.INVALID_ID;
		fullname = fullnameIn;
		emailId = "";
		password = passwordIn;
		phone = "";
		company = "";

	}
	
	public User(String fullnameIn, String emailIdIn, String phoneIn, String companyIn, String passwordIn){
		userId = EntityConstants.INVALID_ID;
		fullname = fullnameIn;
		emailId = emailIdIn;
		password = passwordIn;
		phone = phoneIn;
		company = companyIn;
		
	}
	
	public User(int userIdIn, String fullnameIn, String emailIdIn, String phoneIn, String companyIn, String passwordIn){
		userId = userIdIn;
		fullname = fullnameIn;
		emailId = emailIdIn;
		password = passwordIn;
		phone = phoneIn;
		company = companyIn;
		
	}

	public User clone(User other) {
		User newUser = new User(other.userId, other.fullname, other.emailId, other.phone, other.company, other.password);
		return newUser;
	}
	
	public int getId() { return userId; }
	public String getFullname() { return fullname; }
	public String getEmailId() { return emailId; }
	public String getPhone() { return phone; }
	public String getCompany() { return company; }
	public String getUserPassword() { return password; }
}
