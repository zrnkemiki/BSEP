package ftn.bsep.pkiapp.dto;

public class ExtensionDTO {
	private String oid;
	private String isCritical;
	private String value;
	
	
	
	public ExtensionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExtensionDTO(String oid, String isCritical, String value) {
		super();
		this.oid = oid;
		this.isCritical = isCritical;
		this.value = value;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getIsCritical() {
		return isCritical;
	}
	public void setIsCritical(String isCritical) {
		this.isCritical = isCritical;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

	@Override
	public String toString() {
		return "ExtensionDTO [oid=" + oid + ", isCritical=" + isCritical + ", value=" + value + "]";
	}
}
