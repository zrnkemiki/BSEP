package ftn.bsep.pkiapp.certificates;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.model.CertificateAuthority;

public class CertificateGenerator {

	
	private CSRGenerator csrg = new CSRGenerator();
	
	public boolean verifyCSRSignature(PKCS10CertificationRequest csr) {
		
		// ako je potpis dobijen sa public key isti kao i taj sto je poslato --> true
		
		return false;
	}
	
	
	
	// ovi ce predstavljati neki konverter 
	public SubjectData getSubjectFromCSR() {
		
		return null;
	}
	
	public IssuerData getIssuerDataFromCSR() {
		
		return null;
	}
	
	
	// generise sertifikat na osnovu CSR
	// odnosno --> potpisuje CSR
	// kao param prima CSR, private key od CA, i treba jos public key od subjekta
	// ne znam da li public key od subjekta moze da se izvuce direktno iz klase CSR
	public X509Certificate signCSR(SubjectData subjectData, CertificateAuthority ca, PKCS10CertificationRequest csr,ContentSigner csrContentSigner) throws CertIOException, NoSuchAlgorithmException, CertificateException, InvalidKeyException, NoSuchProviderException, SignatureException {
	       
			IssuerData issuerData = ca.getKeystoreReader().readIssuerFromStore("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks","ca-rs", "password".toCharArray(),  "password".toCharArray());
			Certificate issuerCert = ca.getKeystoreReader().readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks", "password", "ca-rs");
			X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(issuerData.getX500name(), BigInteger.valueOf(333333), subjectData.getStartDate(), subjectData.getEndDate(), csr.getSubject(), csr.getSubjectPublicKeyInfo());

	        JcaX509ExtensionUtils issuedCertExtUtils = new JcaX509ExtensionUtils();

	        // Add Extensions
	        // Use BasicConstraints to say that this Cert is not a CA
	        issuedCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));

	        // Add Issuer cert identifier as Extension
	        issuedCertBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuedCertExtUtils.createAuthorityKeyIdentifier((X509Certificate) issuerCert));
	        issuedCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, issuedCertExtUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
	        GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost"));
	        issuedCertBuilder.addExtension(Extension.subjectAlternativeName, false, subjectAltName.toASN1Primitive());
	        issuedCertBuilder.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
	        //issuedCertBuilder.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
	        X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(csrContentSigner);
	        X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider("BC").getCertificate(issuedCertHolder);

	        // Verify the issued cert signature against the root (issuer) cert
	        issuedCert.verify(issuerCert.getPublicKey(), "BC");

	        return issuedCert;
	}
	
	
	// ovo je samo za root CA -> mozda da se ne radi programski
	// vec direktno na masini ??
	public X509Certificate generateSelfSignedCertificate(RootData rootData) {
		
		try {
			// certificate data
			X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(rootData.getX500name(),
														new BigInteger(rootData.getSerialNumber()),
														rootData.getStartDate(),
														rootData.getEndDate(),
														rootData.getX500name(),
														rootData.getPublicKey());
			
			// signature with private key
			JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			contentSignerBuilder = contentSignerBuilder.setProvider("BC");
			
			ContentSigner contentSigner = contentSignerBuilder.build(rootData.getPrivateKey());
			
			// add extensions
			BasicConstraints basicConstraints = new BasicConstraints(true);  // is a CA
			certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints);
			
			KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature);
			certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.15"), true, keyUsage);
			
			// generate certificate + signature
			X509CertificateHolder certHolder = certBuilder.build(contentSigner);
			
			// convert from holder to certificate
			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			converter.setProvider("BC");
			
			return converter.getCertificate(certHolder);
			
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
}
