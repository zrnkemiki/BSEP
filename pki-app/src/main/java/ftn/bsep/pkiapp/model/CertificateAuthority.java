package ftn.bsep.pkiapp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCSException;

import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

public abstract class CertificateAuthority {

	// neki ID od authority
	// mozda sertifikat kao polje?
	// .....
	protected String alias;
	protected String keyStorePath = "D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks";
	protected String trustStorePath = "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\root-truststore.jks";
	protected KeyStore keystore;
	protected KeyStore truststore;
	protected String password = "password";
	protected KeyStoreReader keystoreReader;
	protected KeyStoreWriter keystoreWriter;
	protected ContentSigner contentSigner;
	
	
	public CertificateAuthority() {
		alias = "ca-rs";
		this.keystoreReader = new KeyStoreReader();
		this.keystoreWriter = new KeyStoreWriter();
		//LOAD STORES
		try {
			this.loadStores();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Content Signer
		try {
			this.buildContentSigner();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		}
		
	}
	
	public CertificateAuthority(String keyStorePath, String trustStorePath, String password, String alias) {
		this.alias = alias;
		this.keyStorePath = keyStorePath;
		this.trustStorePath = trustStorePath;
		this.password = password;
		this.keystoreReader = new KeyStoreReader();
		this.keystoreWriter = new KeyStoreWriter();
	}

	public void buildContentSigner() throws OperatorCreationException {
		JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider("BC");
        this.contentSigner = builder.build(this.getPrivateKey());
	}
	
	public void loadStores() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		System.out.println(this.keyStorePath);
		System.out.println(this.password);
		this.keystore.load(new FileInputStream(this.keyStorePath), this.password.toCharArray());
		truststore.load(new FileInputStream(trustStorePath), password.toCharArray());
	}

	//provera potpisa CSR -> dokazuje da entitet koji je poslao csr ima privatni kljuc
	public boolean checkCSRSigniture(PKCS10CertificationRequest csr) throws OperatorCreationException, PKCSException {
		
		JcaContentVerifierProviderBuilder builder = new JcaContentVerifierProviderBuilder().setProvider("BC");
		return csr.isSignatureValid(builder.build(csr.getSubjectPublicKeyInfo()));
		
	}
	
	public String getCSRData(PKCS10CertificationRequest csr) {
		String data = "";
		data = csr.getSubject().toString();
		//petlja trenutno ne radi nista posto ne dodajemo atribute u csr
		for(Attribute a : csr.getAttributes()) {
			data += " ";
			data += a.toString();
		}
		return data;
	}
	
	public X509Certificate signCertificate(PKCS10CertificationRequest csr) throws NoSuchAlgorithmException, CertIOException, OperatorCreationException, CertificateException, InvalidKeyException, NoSuchProviderException, SignatureException {
		//potrebni podaci CA-ja
		IssuerData issuerData = this.getKeystoreReader().readIssuerFromStore("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks","ca-rs", "password".toCharArray(),  "password".toCharArray());
		Certificate issuerCert = this.getKeystoreReader().readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks", "password", "ca-rs");
		
		//Datumi vazenja sertifikata (startDate -> trenutno vreme, endDate -> +6meseci)
		Calendar cal = Calendar.getInstance();
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 6);
		Date endDate = cal.getTime();
		//================================================================================================	
		//Pravljenje sertifikata
		
		X509v3CertificateBuilder issuedCertBuilder = new X509v3CertificateBuilder(issuerData.getX500name(), BigInteger.valueOf(333333), startDate, endDate, csr.getSubject(), csr.getSubjectPublicKeyInfo());
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
        //========================================================================================================================
        //Potpisivanje sertifikata
        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
		contentSignerBuilder = contentSignerBuilder.setProvider("BC");
		ContentSigner contentSigner = contentSignerBuilder.build(this.getPrivateKey());  
        X509CertificateHolder issuedCertHolder = issuedCertBuilder.build(contentSigner);
        
        
        X509Certificate issuedCert  = new JcaX509CertificateConverter().setProvider("BC").getCertificate(issuedCertHolder);

        // Verify the issued cert signature against the root (issuer) cert
        issuedCert.verify(issuerCert.getPublicKey(), "BC");

        return issuedCert;
		
	}
	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public KeyStoreReader getKeystoreReader() {
		return keystoreReader;
	}


	public void setKeystoreReader(KeyStoreReader keystoreReader) {
		this.keystoreReader = keystoreReader;
	}


	public KeyStoreWriter getKeystoreWriter() {
		return keystoreWriter;
	}


	public void setKeystoreWriter(KeyStoreWriter keystoreWriter) {
		this.keystoreWriter = keystoreWriter;
	}


	public String getKeyStore() {
		return keyStorePath;
	}


	public void setKeyStore(String keyStore) {
		this.keyStorePath = keyStore;
	}


	public String getTrustStore() {
		return trustStorePath;
	}


	public void setTrustStore(String trustStore) {
		this.trustStorePath = trustStore;
	}
	
	public PrivateKey getPrivateKey() {
		PrivateKey privateKey = this.keystoreReader.readPrivateKey(keyStorePath, password, alias, password);
		return privateKey;
	}
	
	public Certificate getCertificate() {
		return this.keystoreReader.readCertificate(keyStorePath, password, alias);
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getTrustStorePath() {
		return trustStorePath;
	}

	public void setTrustStorePath(String trustStorePath) {
		this.trustStorePath = trustStorePath;
	}

	public KeyStore getKeystore() {
		return keystore;
	}

	public void setKeystore(KeyStore keystore) {
		this.keystore = keystore;
	}

	public KeyStore getTruststore() {
		return truststore;
	}

	public void setTruststore(KeyStore truststore) {
		this.truststore = truststore;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ContentSigner getContentSigner() {
		return contentSigner;
	}

	public void setContentSigner(ContentSigner contentSigner) {
		this.contentSigner = contentSigner;
	}
	
	
	
	
	
	
	
}
