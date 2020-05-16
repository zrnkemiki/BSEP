package ftn.bsep.pkiapp.controllers;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.certificates.CertificateGenerator;
import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;
import ftn.bsep.pkiapp.model.CertificateAuthority;

@RestController
@RequestMapping("/")
public class ProbaController {

	CertificateGenerator cg = new CertificateGenerator();
	KeyStoreWriter ks = new KeyStoreWriter();
	KeyStoreReader ksr = new KeyStoreReader();
	CertificateAuthority ca = new CertificateAuthority();
	
	@GetMapping("hello")
	public String hello() {
		return "hello";
	}
	@GetMapping("self-sign")
	public String selfSigning() throws Exception {
		KeyStoreWriter ksw = new KeyStoreWriter();
		RootData rd = generateRootData();
		ksw.loadKeyStore(null, "password".toCharArray());
		X509Certificate cert = cg.generateSelfSignedCertificate(rd);
		writeCertToFileBase64Encoded(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\self-sign.cer");
		ksw.write("root", rd.getPrivateKey(), "password".toCharArray(), cert);
		ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks", "password".toCharArray());
		System.out.println("HELOOOOOO");
		return "Self Signing Finished";
	}
	
	@GetMapping("csr-create")
	public String createCSR() throws Exception {
		 // Generate a new KeyPair and sign it using the Root Cert Private Key
        // by generating a CSR (Certificate Signing Request)
		KeyPair issuedCertKeyPair = generateKeyPair();
		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, "password".toCharArray());
		
		KeyStoreWriter ksw2 = new KeyStoreWriter();
		ksw2.loadKeyStore(null, "password".toCharArray());
		
		KeyStoreWriter ksw3 = new KeyStoreWriter();
		ksw3.loadKeyStore(null, "password".toCharArray());
		
		SubjectData subjectData = generateSubjectData(issuedCertKeyPair);
        
		//=================================================================================================
		X500Name issuedCertSubject = subjectData.getX500name();

        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(issuedCertSubject, issuedCertKeyPair.getPublic());
        JcaContentSignerBuilder csrBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider("BC");

        // Sign the new KeyPair with the root cert Private Key
        PrivateKey rootKeyPrivate = ca.getKeystoreReader().readPrivateKey("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks", "password", "root", "password");
        ContentSigner csrContentSigner = csrBuilder.build(rootKeyPrivate);
        PKCS10CertificationRequest csr = p10Builder.build(csrContentSigner);
        
        //System.out.println(rootKeyPrivate);
        //=================================================================================================
        

		IssuerData issuerData = ca.getKeystoreReader().readIssuerFromStore("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks","root", "password".toCharArray(),  "password".toCharArray());
		Certificate issuerCert = ca.getKeystoreReader().readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\root-keystore.jks", "password", "root");
		X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(issuerData.getX500name(), BigInteger.valueOf(333333), subjectData.getStartDate(), subjectData.getEndDate(), csr.getSubject(), csr.getSubjectPublicKeyInfo());

        JcaX509ExtensionUtils issuedCertExtUtils = new JcaX509ExtensionUtils();

        // Add Extensions
        // Use BasicConstraints to say that this Cert is not a CA
        issuedCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));

        // Add Issuer cert identifier as Extension
        issuedCertBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuedCertExtUtils.createAuthorityKeyIdentifier((X509Certificate) issuerCert));
        issuedCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, issuedCertExtUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));

        X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(csrContentSigner);
        X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider("BC").getCertificate(issuedCertHolder);

        // Verify the issued cert signature against the root (issuer) cert
        issuedCert.verify(issuerCert.getPublicKey());
        writeCertToFileBase64Encoded(issuedCert,"D:\\BSEP\\pki-app\\src\\main\\resources\\new-cert.crt");
        
        ksw2.write("client", issuedCertKeyPair.getPrivate(), "password".toCharArray(), issuedCert);
		ksw2.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\client-keystore.jks", "password".toCharArray());
		
		ksw.storeCertificate("root", issuerCert);
		ksw.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\client-truststore.jks", "password".toCharArray());
		
		ksw3.storeCertificate("client", issuerCert);
		ksw3.saveKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\root-truststore.jks", "password".toCharArray());
		return "CSR created";
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
	
	private IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, "Nikola Luburic");
	    builder.addRDN(BCStyle.SURNAME, "Luburic");
	    builder.addRDN(BCStyle.GIVENNAME, "Nikola");
	    builder.addRDN(BCStyle.O, "UNS-FTN");
	    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
	    builder.addRDN(BCStyle.C, "RS");
	    builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "654321");

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(builder.build(), issuerKey);
	}
	
	private SubjectData generateSubjectData(KeyPair keyPairSubject) {
		try {
			
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");
			
			//Serijski broj sertifikata
			String sn="1";
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "Marko Kopanja");
		    builder.addRDN(BCStyle.SURNAME, "Marko");
		    builder.addRDN(BCStyle.GIVENNAME, "Kopanja");
		    builder.addRDN(BCStyle.O, "UNS-FTN");
		    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "123456");
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(builder.build(), sn, startDate, endDate, keyPairSubject.getPublic());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private RootData generateRootData() {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");
			
			//Serijski broj sertifikata
			String sn="1";
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, "Goran Sladic");
		    builder.addRDN(BCStyle.SURNAME, "Sladic");
		    builder.addRDN(BCStyle.GIVENNAME, "Goran");
		    builder.addRDN(BCStyle.O, "UNS-FTN");
		    builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		    builder.addRDN(BCStyle.C, "RS");
		    builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, "222222");
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new RootData(sn, builder.build(), startDate, endDate, keyPairSubject.getPrivate(), keyPairSubject.getPublic());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static void writeCertToFileBase64Encoded(X509Certificate certificate, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(certificate.getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
    }
	static void exportKeyPairToKeystoreFile(KeyPair keyPair, X509Certificate certificate, String alias, String fileName, String storeType, String storePass) throws Exception {
        KeyStore sslKeyStore = KeyStore.getInstance(storeType, "BC");
        sslKeyStore.load(null, null);
        sslKeyStore.setKeyEntry(alias, keyPair.getPrivate(),null, new X509Certificate[]{certificate});
        FileOutputStream keyStoreOs = new FileOutputStream(fileName);
        sslKeyStore.store(keyStoreOs, storePass.toCharArray());
    }
}
