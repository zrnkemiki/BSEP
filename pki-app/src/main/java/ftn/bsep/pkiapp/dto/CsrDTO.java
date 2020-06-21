package ftn.bsep.pkiapp.dto;

import java.util.ArrayList;

public class CsrDTO {
	private Long id;
	private String commonName;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private int uid;
	private ArrayList<String> extensions;
	
	public CsrDTO() {
		super();
		extensions = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	public CsrDTO(Long id, String commonName, String surname, String givenName, String organization, String organizationUnit,
			String country, String email, int uid, ArrayList<String> extensions) {
		super();
		this.id = id;
		this.commonName = commonName;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.country = country;
		this.email = email;
		this.uid = uid;
		this.extensions = extensions;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public ArrayList<String> getExtensions() {
		return extensions;
	}
	public void setExtensions(ArrayList<String> extensions) {
		this.extensions = extensions;
	}
	
	
	
}
