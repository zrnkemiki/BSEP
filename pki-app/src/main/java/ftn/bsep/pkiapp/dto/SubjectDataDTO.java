package ftn.bsep.pkiapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubjectDataDTO {
	private String commonName;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private String uid;
	private List<ExtensionDTO> extensions;
	
	
	
	public SubjectDataDTO() {
		super();
		setExtensions(new ArrayList<ExtensionDTO>());
		// TODO Auto-generated constructor stub
	}
	
	
	public SubjectDataDTO(String commonName, String surname, String givenName, String organization,
			String organizationUnit, String country, String email, String uid, ArrayList<ExtensionDTO> extensions) {
		super();
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
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<ExtensionDTO> getExtensions() {
		return extensions;
	}


	public void setExtensions(List<ExtensionDTO> extensions) {
		this.extensions = extensions;
	}

}
