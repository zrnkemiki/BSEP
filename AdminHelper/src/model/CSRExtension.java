package model;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public class CSRExtension {
	ASN1ObjectIdentifier oid;
	ASN1Encodable value;
	boolean isCritical;
	
	
	
	
	public CSRExtension() {
	}


	public CSRExtension(ASN1ObjectIdentifier oid, boolean isCritical, ASN1Encodable value) {
		this.oid = oid;
		this.value = value;
		this.isCritical = isCritical;
	}
	
	
	public ASN1ObjectIdentifier getOid() {
		return oid;
	}
	public void setOid(ASN1ObjectIdentifier oid) {
		this.oid = oid;
	}
	public ASN1Encodable getValue() {
		return value;
	}
	public void setValue(ASN1Encodable value) {
		this.value = value;
	}
	public boolean isCritical() {
		return isCritical;
	}
	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}
	
	
	
}
