package ftn.bsep.pkiapp.dto;

import ftn.bsep.pkiapp.model.User;
import ftn.bsep.pkiapp.model.UserType;

public class UserDTO {

	protected String firstName;
	protected String lastName;
	protected String password;
	protected String username;
	protected String email;
	protected String city;
	protected String number;
	protected String JWTToken;
	protected UserType userType;

	public UserDTO() {
		super();
	}

	public UserDTO(String firstName, String lastName, String password, String username, String email, String city,
			String number, String jWTToken, String companyName, UserType userType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.username = username;
		this.email = email;
		this.city = city;
		this.number = number;
		this.JWTToken = jWTToken;
		this.companyName = companyName;
		this.userType = userType;
	}

	public UserDTO(User user) {
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setUsername(user.getUsername());
		setEmail(user.getEmail());
		setCity(user.getCity());
		setNumber(user.getPhoneNumber());
		setUserType(user.getUserType());

	}

	public String getJWTToken() {
		return JWTToken;
	}

	public void setJWTToken(String jWTToken) {
		JWTToken = jWTToken;
	}

	private String companyName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
