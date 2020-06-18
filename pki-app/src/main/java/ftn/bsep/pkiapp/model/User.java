 package ftn.bsep.pkiapp.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ftn.bsep.pkiapp.dto.UserDTO;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	protected Long id;
	private Long carId;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String email;
	protected String password;
	protected String city;
	protected String phoneNumber;
	@Enumerated(EnumType.ORDINAL)
    protected UserStatus userStatus;
	
	protected String uuid;
    
    
    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(Long id, Long carId, String firstName, String lastName, String username, String email, String password,
			String city, String phoneNumber, UserStatus userStatus, String uuid) {
		super();
		this.id = id;
		this.carId = carId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.userStatus = userStatus;
		this.uuid = uuid;
	}
	
	public User(UserDTO dto) {
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.username = dto.getUsername();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.city = dto.getCity();
		this.phoneNumber = dto.getNumber();
		this.uuid = UUID.randomUUID().toString();
	}







	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public UserStatus getUserStatus() {
		return userStatus;
	}



	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
    
}
