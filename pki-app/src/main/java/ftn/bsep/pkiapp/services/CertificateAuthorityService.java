package ftn.bsep.pkiapp.services;

import org.springframework.stereotype.Service;

import ftn.bsep.pkiapp.model.CACountry;

@Service
public class CertificateAuthorityService {
	CACountry ca = null;
	
	public void initCA(String alias, String privateKeyPass) {
		
		ca = new CACountry("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-rs-keystore.jks", null, privateKeyPass, alias);
	}
	
	public CACountry getCA() {
		return ca;
	}

}
