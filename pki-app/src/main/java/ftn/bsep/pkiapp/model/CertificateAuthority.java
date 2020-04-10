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
}
