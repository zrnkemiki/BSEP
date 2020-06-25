package ftn.bsep.pkiapp.controllers;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.certificates.CSRGenerator;
import ftn.bsep.pkiapp.certificates.CertificateGenerator;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;
import ftn.bsep.pkiapp.model.CARoot;
import ftn.bsep.pkiapp.model.CertificateAuthority;
import ftn.bsep.pkiapp.util.CertHelper;
import ftn.bsep.pkiapp.util.DataGenerator;

@RestController
@RequestMapping("/root-ca")
public class RootCAController {
	
	CertificateAuthority ca = new CARoot(null, null, null, null);
	CSRGenerator csrGen = new CSRGenerator();
	DataGenerator dataGen = new DataGenerator();
	PKCS10CertificationRequest csr = null;
	
	@PreAuthorize("hasAuthority('ADMIN_CA')")
	@GetMapping("/submit-csr")
	public String signCSR() throws Exception {
		KeyPair keyPair = CertHelper.generateKeyPair();
		
		SubjectData subjectData = dataGen.generateCAData(keyPair);
		
		csr = csrGen.generateCSR(subjectData);
		boolean isCSRValid = ca.checkCSRSigniture(csr);
		
		if(isCSRValid) {
			/*--Privremeni kod za potpisivanje i izdavanje sertifikata zbog pristupa kljucevima
			 * ------------------------------------------------------------------------------------------
			KeyStoreWriter ksw = new KeyStoreWriter();
			ksw.loadKeyStore(null, "password".toCharArray());
			
			X509Certificate issuedCert = ca.signCertificate(csr);
			CertHelper.writeCertToFileBase64Encoded(issuedCert, "D:\\BSEP\\pki-app\\src\\main\\resources\\countryCAStores\\CA-RS.cer");
			ksw.write("ca-rs", keyPair.getPrivate(), "password".toCharArray(), issuedCert);
			ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\countryCAStores\\ca-rs-keystore.jks", "password".toCharArray());
			*/
			return ca.getCSRData(csr);
		}
		return "Invalid CSR";
	
	}
	@PreAuthorize("hasAuthority('ADMIN_CA')")
	@GetMapping("/gen-root")
	public String genRoot() throws Exception {
		RootData rootData = dataGen.generateRootData();
		CertificateGenerator certGen = new CertificateGenerator();
		X509Certificate rootCert = certGen.generateSelfSignedCertificate(rootData);
		
		
		return "Ok";
	}
	@PreAuthorize("hasAuthority('ADMIN_CA')")
	@GetMapping("/sign-cert")
	public String signCert() throws Exception {
		X509Certificate issuedCert = ca.signCertificate(csr);
		return "Ok";
	}

}
