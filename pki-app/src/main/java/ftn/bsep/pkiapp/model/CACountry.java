package ftn.bsep.pkiapp.model;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

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
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

public class CACountry extends CertificateAuthority {

	
	public CACountry() {
		super();
	}
	
	public CACountry(String keyStorePath, String trustStorePath, String password, String alias) {
		super(keyStorePath, trustStorePath, password, alias);
		
	}
	
	@Override
	public X509Certificate signCertificate(PKCS10CertificationRequest csr) throws NoSuchAlgorithmException, CertIOException, OperatorCreationException, CertificateException, InvalidKeyException, NoSuchProviderException, SignatureException {
		
		//potrebni podaci CA-ja(passwordi treba da budu razliciti atributi - 1 za otvaranje keystora a drugi za uzimanje privateKey
				IssuerData issuerData = this.getKeystoreReader().readIssuerFromStore(this.keyStorePath,this.alias, this.password.toCharArray(), this.password.toCharArray());
				Certificate issuerCert = this.getKeystoreReader().readCertificate(this.keyStorePath, this.password, this.alias);
				
				//Datumi vazenja sertifikata (startDate -> trenutno vreme, endDate -> +6meseci)
				Calendar cal = Calendar.getInstance();
				Date startDate = cal.getTime();
				cal.add(Calendar.MONTH, 6);
				Date endDate = cal.getTime();
				//================================================================================================	
				//Pravljenje sertifikata
				
				X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(issuerData.getX500name(), BigInteger.valueOf(System.currentTimeMillis()), startDate, endDate, csr.getSubject(), csr.getSubjectPublicKeyInfo());
		        JcaX509ExtensionUtils issuedCertExtUtils = new JcaX509ExtensionUtils();

		        // Add Extensions
		     // Use BasicConstraints to say that this CA cant issue CA certs
		        issuedCertBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(0));
		        // Add Issuer cert identifier as Extension
		        issuedCertBuilder.addExtension(Extension.authorityKeyIdentifier, false, issuedCertExtUtils.createAuthorityKeyIdentifier((X509Certificate) issuerCert));
		        issuedCertBuilder.addExtension(Extension.subjectKeyIdentifier, false, issuedCertExtUtils.createSubjectKeyIdentifier(csr.getSubjectPublicKeyInfo()));
		        
		        KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature | KeyUsage.cRLSign);
		        issuedCertBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.15"), true, keyUsage);
		       //Potpisivanje sertifikata
		        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
				contentSignerBuilder = contentSignerBuilder.setProvider("BC");
				ContentSigner contentSigner = contentSignerBuilder.build(issuerData.getPrivateKey());  
		        X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(contentSigner);
		        
		        
		        X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider("BC").getCertificate(issuedCertHolder);

		        // Verify the issued cert signature against the root (issuer) cert
		        issuedCert.verify(issuerCert.getPublicKey(), "BC");

		        return issuedCert;
	}
}
