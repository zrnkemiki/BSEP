package ftn.bsep.pkiapp.services;

import org.springframework.stereotype.Service;

import ftn.bsep.pkiapp.model.CACountry;

@Service
public class CertificateAuthorityService {
	CACountry ca = null;
	
	public void initCA(String alias, String privateKeyPass) {
		
		ca = new CACountry("D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", null, privateKeyPass, alias);
	}

}
