package ftn.bsep.pkiapp.dto;

public class LoginCaDTO {
	private String alias;
	private String privateKeyPassword;
	
	
	
	public LoginCaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginCaDTO(String alias, String privateKeyPassword) {
		super();
		this.alias = alias;
		this.privateKeyPassword = privateKeyPassword;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPrivateKeyPassword() {
		return privateKeyPassword;
	}
	public void setPrivateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
	}
	

}
