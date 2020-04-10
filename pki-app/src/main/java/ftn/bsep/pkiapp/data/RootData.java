package ftn.bsep.pkiapp.data;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;

public class RootData {

	private String serialNumber;
	private X500Name x500name;
	private Date startDate;
	private Date endDate;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public RootData() {
		
	}

	public RootData(String serialNumber, X500Name x500name, Date startDate, Date endDate, PrivateKey privateKey,
			PublicKey publicKey) {
		super();
		this.serialNumber = serialNumber;
		this.x500name = x500name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public X500Name getX500name() {
		return x500name;
	}

	public void setX500name(X500Name x500name) {
		this.x500name = x500name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	
}
