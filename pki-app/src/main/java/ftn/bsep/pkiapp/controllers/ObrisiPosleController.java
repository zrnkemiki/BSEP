package ftn.bsep.pkiapp.controllers;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

@RestController
public class ObrisiPosleController {

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
