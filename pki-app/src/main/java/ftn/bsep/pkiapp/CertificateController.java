package ftn.bsep.pkiapp;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
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
import ftn.bsep.pkiapp.model.CertificateAuthority;
import ftn.bsep.pkiapp.util.DataGenerator;

@RestController
@RequestMapping("/")
public class CertificateController {
	
	DataGenerator dg = new DataGenerator();
	CertificateGenerator cg = new CertificateGenerator();
	KeyStoreWriter ksw = new KeyStoreWriter();
	KeyStoreReader ksr = new KeyStoreReader();
	CertificateAuthority ca = new CertificateAuthority();
	CSRGenerator csrGen = new CSRGenerator();
	
	
	//Privremeni
	
	
	KeyPair keyPairSubject = this.generateKeyPair();
	SubjectData subjectData = dg.generateSubjectData(keyPairSubject);
	PKCS10CertificationRequest csr = null;
	X509Certificate signedCertificate = null;
	
	SubjectData sslSubjectData = dg.generateSSLData(keyPairSubject);
	
	//---------------SELF SIGN
	@GetMapping("self-sign")
	public String selfSigning() throws Exception {
		KeyStoreWriter ksw = new KeyStoreWriter();
		RootData rd = dg.generateRootData();
		ksw.loadKeyStore(null, "password".toCharArray());
		X509Certificate cert = cg.generateSelfSignedCertificate(rd);
		writeCertToFileBase64Encoded(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\self-sign.cer");
		ksw.write("root", rd.getPrivateKey(), "password".toCharArray(), cert);
		ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks", "password".toCharArray());
		System.out.println("HELOOOOOO");
		return "Self Signing Finished";
	}
	
	@GetMapping("hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("create-csr")
	public String createCSR() throws Exception {
		//ca.loadStores();
		
		csr = csrGen.generateCSR(sslSubjectData, ca.getKeystoreReader().readPrivateKey("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks", "password", "root", "password"));
		writeCertToFileBase64Encoded(csr,"D:\\BSEP\\pki-app\\src\\main\\resources\\csrs\\csr1.csr");
		
		return"CSR Created";
	}
	
	@GetMapping("sign-csr")
	public String signCSR() throws Exception {
		//ca.loadStores();
		JcaContentSignerBuilder csrBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider("BC");
		ContentSigner csrContentSigner = csrBuilder.build(ca.getPrivateKey());
		signedCertificate = cg.signCSR(sslSubjectData, ca, csr, csrContentSigner);
		
		ksw.loadKeyStore(null, "password".toCharArray());
		ksw.write("sslCert", sslSubjectData.getPrivateKey(), "password".toCharArray(), signedCertificate);
		ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\client-keystore.jks", "password".toCharArray());
		
		ksw.loadKeyStore(null, "password".toCharArray());
		ksw.storeCertificate("root", ca.getCertificate());
		ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\client-truststore.jks", "password".toCharArray());
		
		writeCertToFileBase64Encoded(signedCertificate,"D:\\BSEP\\pki-app\\src\\main\\resources\\certificates\\clientCert.cer");
		
		ca.getKeystoreWriter().loadKeyStore(ca.getTrustStorePath(), ca.getPassword().toCharArray());
		ca.getKeystoreWriter().saveKeyStore(ca.getTrustStorePath(), ca.getPassword().toCharArray());
		
		
		//writeCertToFileBase64Encoded(csr,"D:\\BSEP\\pki-app\\src\\main\\resources\\certificates\\cert1.crt");		
		return"CSR signed";
	}
	
	static void writeCertToFileBase64Encoded(PKCS10CertificationRequest csr, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
    }
	
	static void writeCertToFileBase64Encoded(X509Certificate certificate, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(certificate.getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
    }
	
	private KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	

}
