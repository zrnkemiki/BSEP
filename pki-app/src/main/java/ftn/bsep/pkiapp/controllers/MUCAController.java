package ftn.bsep.pkiapp.controllers;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.certificates.CSRGenerator;
import ftn.bsep.pkiapp.certificates.CertificateGenerator;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;
import ftn.bsep.pkiapp.model.CACountry;
import ftn.bsep.pkiapp.model.CAMU;
import ftn.bsep.pkiapp.model.CertificateAuthority;
import ftn.bsep.pkiapp.util.CertHelper;
import ftn.bsep.pkiapp.util.DataGenerator;

@RestController
@RequestMapping("/mu-ca")
public class MUCAController {
	
	CAMU ca = new CAMU("C:\\Users\\Z-AIO\\Documents\\Projekti\\BSEP\\pki-app\\src\\main\\resources\\muCAStores\\ca-rs-mu1-keystore.jks", null, "password", "ca-rs-mu1");
	CSRGenerator csrGen = new CSRGenerator();
	DataGenerator dataGen = new DataGenerator();
	PKCS10CertificationRequest csr = null;
	
	@GetMapping("/submit-server-csr")
	public String signCSR() throws Exception {
		KeyPair keyPair = CertHelper.generateKeyPair();
		
		SubjectData subjectData = dataGen.generateSSLData(keyPair);
		
		csr = csrGen.generateCSR(subjectData);
		boolean isCSRValid = ca.checkCSRSigniture(csr);
		
		
		if(isCSRValid) {
			
			
			return ca.getCSRData(csr);
		}
		return "Invalid CSR";
	
	}
	
	@GetMapping("/submit-client-csr")
	public String signClientCSR() throws Exception {
		KeyPair keyPair = CertHelper.generateKeyPair();
		
		SubjectData subjectData = dataGen.generateClientData(keyPair);
		
		csr = csrGen.generateCSR(subjectData);
		boolean isCSRValid = ca.checkCSRSigniture(csr);
		
		
		if(isCSRValid) {
			/*
			KeyStoreWriter ksw = new KeyStoreWriter();
			ksw.loadKeyStore(null, "password".toCharArray());
			
			X509Certificate issuedCert = ca.signClientCertificate(csr);
			CertHelper.writeCertToFileBase64Encoded(issuedCert, "C:\\Users\\Z-AIO\\Documents\\Projekti\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\CLIENT1-RS-MU1.cer");
			ksw.write("client1-rs-mu1", keyPair.getPrivate(), "password".toCharArray(), issuedCert);
			ksw.saveKeyStore("C:\\Users\\Z-AIO\\Documents\\Projekti\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\client1-rs-mu1-keystore.jks", "password".toCharArray());
			*/
			return ca.getCSRData(csr);
		}
		return "Invalid CSR";
	
	}
	
	@GetMapping("/sign-server-cert")
	public String signServerCert() throws Exception {
		X509Certificate issuedCert = ca.signServerCertificate(csr);
		return "Ok";
	}
	
	@GetMapping("/sign-client-cert")
	public String signClientCert() throws Exception {
		X509Certificate issuedCert = ca.signClientCertificate(csr);
		return "Ok";
	}


}
