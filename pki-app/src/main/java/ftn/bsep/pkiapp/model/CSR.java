package ftn.bsep.pkiapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "csr")
public class Csr {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String commonName;
	private String surname;
	private String givenName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private int uid;
	@Column
	@ElementCollection(targetClass = String.class)
	private List<String> extensions;
	
	public Csr() {
		super();
		extensions = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}
	public Csr(Long id, String commonName, String surname, String givenName, String organization, String organizationUnit,
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
	public List<String> getExtensions() {
		return extensions;
	}
	public void setExtensions(ArrayList<String> extensions) {
		this.extensions = extensions;
	}
	
	
	
}
