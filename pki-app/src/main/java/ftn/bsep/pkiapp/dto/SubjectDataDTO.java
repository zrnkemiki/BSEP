package ftn.bsep.pkiapp.dto;

import java.util.Date;

public class SubjectDataDTO {
	private String commonName;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private Date dateFrom;
	private Date dateUntil;
	private int uid;
	
	
	
	public SubjectDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SubjectDataDTO(String commonName, String surname, String givenName, String organization,
			String organizationUnit, String country, String email, Date dateFrom, Date dateUntil, int uid) {
		super();
		this.commonName = commonName;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.country = country;
		this.email = email;
		this.dateFrom = dateFrom;
		this.dateUntil = dateUntil;
		this.uid = uid;
	}


	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationUnit() {
		return organizationUnit;
	}
	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateUntil() {
		return dateUntil;
	}
	public void setDateUntil(Date dateUntil) {
		this.dateUntil = dateUntil;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}

}
