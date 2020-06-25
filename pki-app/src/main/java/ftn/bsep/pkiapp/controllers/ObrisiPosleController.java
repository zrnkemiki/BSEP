package ftn.bsep.pkiapp.controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.certificates.CertificateGenerator;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

@RestController
public class ObrisiPosleController {

	@GetMapping("/ca.cer")
	public String genRoot() throws Exception {
		System.out.println("USAO");
		
		
		return "Ok";
	}
	@GetMapping("/ok")
	public String ok(HttpServletRequest request) {

		X509Certificate[] certs = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
		for(X509Certificate cert : certs) {
			System.out.println(cert.toString());
		}
		
		CertificateFactory cf = null;
		try {
			cf = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			CertPath cp = cf.generateCertPath(Arrays
			    .asList(new X509Certificate[] { certs[0] }));
			System.out.println(cp.getCertificates().size());
		} catch (CertificateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		KeyStore keyStore = null;
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (KeyStoreException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\server-truststore.jks"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			keyStore.load(in, "password".toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PKIXParameters pkixParams = null;
		try {
			pkixParams = new PKIXParameters(keyStore);
			pkixParams.setRevocationEnabled(true);
			
		} catch (KeyStoreException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(pkixParams.isRevocationEnabled());
		Security.setProperty("ocsp.enable", "true");
		return "OK";
	}
	
	@GetMapping("/generisi")
	public void generateSiemStores() {
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate muCACert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\muCAStores\\ca-rs-mu1-keystore.jks", "password", "ca-rs-mu1");
		X509Certificate countryCert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\countryCAStores\\ca-rs-keystore.jks", "password", "ca-rs");
		X509Certificate rootCert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", "password", "root");
		
		
		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, "password".toCharArray());
		
		
		ksw.storeCertificate("ca-rs-mu1", muCACert);
		ksw.storeCertificate("ca-rs", countryCert);
		ksw.storeCertificate("root", rootCert);

		ksw.saveKeyStore("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\serverStores\\server-truststore.jks", "password".toCharArray());
	}
	
	@GetMapping("/upisipk")
	public void wirtePKtoFile() {
		KeyStoreReader ksr = new KeyStoreReader();
		PrivateKey pk = ksr.readPrivateKey("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\client1-rs-mu1-keystore.jks", "password", "client1-rs-mu1", "password");
		String outFile = "C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\root-cert.pem";
		
		X509Certificate cert = (X509Certificate) ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\clientStores\\client1-rs-mu1-keystore.jks", "password", "client1-rs-mu1");
		X509Certificate muCACert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\muCAStores\\ca-rs-mu1-keystore.jks", "password", "ca-rs-mu1");
		X509Certificate countryCert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\countryCAStores\\ca-rs-keystore.jks", "password", "ca-rs");
		X509Certificate rootCert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", "password", "root");

		
		try (PemWriter writer = new PemWriter(new FileWriter(outFile))) {
	        writer.writeObject(new PemObject("CERTIFICATE", rootCert.getEncoded()));
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/uradi")
	public void makeJks() {
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate rootCert = (X509Certificate)ksr.readCertificate("C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\DFRoot-keystore.jks", "password", "root");

		
		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, "password".toCharArray());

	}
}
