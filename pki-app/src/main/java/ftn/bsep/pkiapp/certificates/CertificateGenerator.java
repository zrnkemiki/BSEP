package ftn.bsep.pkiapp.certificates;

import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;

public class CertificateGenerator {

	
	
	
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
	public X509Certificate signCSR(SubjectData subjectData, IssuerData issuerData) {
		
		try {
			X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
											new BigInteger(subjectData.getSerialNumber()), 
											subjectData.getStartDate(), 
											subjectData.getEndDate(), 
											subjectData.getX500name(), 
											subjectData.getPublicKey());
			
			JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			contentSignerBuilder = contentSignerBuilder.setProvider("BC");
			
			ContentSigner contentSigner = contentSignerBuilder.build(issuerData.getPrivateKey());
			
			X509CertificateHolder certHolder = certBuilder.build(contentSigner);
			
			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			converter.setProvider("BC");
			
			return converter.getCertificate(certHolder);
			
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} 
			
		return null;
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
