package ftn.bsep.pkiapp.model;

import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

public class CertificateAuthority {

	// neki ID od authority
	// mozda sertifikat kao polje?
	// .....
	private String id;
	private KeyStoreReader keystoreReader;
	private KeyStoreWriter keystoreWriter;
	
	
	public CertificateAuthority() {
		this.keystoreReader = new KeyStoreReader();
		this.keystoreWriter = new KeyStoreWriter();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public KeyStoreReader getKeystoreReader() {
		return keystoreReader;
	}


	public void setKeystoreReader(KeyStoreReader keystoreReader) {
		this.keystoreReader = keystoreReader;
	}


	public KeyStoreWriter getKeystoreWriter() {
		return keystoreWriter;
	}


	public void setKeystoreWriter(KeyStoreWriter keystoreWriter) {
		this.keystoreWriter = keystoreWriter;
	}
	
	
}
