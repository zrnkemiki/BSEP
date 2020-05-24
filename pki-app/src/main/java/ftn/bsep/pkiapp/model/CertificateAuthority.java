package ftn.bsep.pkiapp.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import ftn.bsep.pkiapp.keystores.KeyStoreReader;
import ftn.bsep.pkiapp.keystores.KeyStoreWriter;

public class CertificateAuthority {

	// neki ID od authority
	// mozda sertifikat kao polje?
	// .....
	private String id;
	
	private String keyStorePath = "D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-keystore.jks";
	private String trustStorePath = "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\root-truststore.jks";
	private KeyStore keystore;
	private KeyStore truststore;
	private String password = "password";
	private KeyStoreReader keystoreReader;
	private KeyStoreWriter keystoreWriter;
	
	
	public CertificateAuthority() {
		id = "ca-rs";
		this.keystoreReader = new KeyStoreReader();
		this.keystoreWriter = new KeyStoreWriter();
		
	}

	public void loadStores() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		keystore.load(new FileInputStream(keyStorePath), password.toCharArray());
		truststore.load(new FileInputStream(trustStorePath), password.toCharArray());
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
		PrivateKey privateKey = this.keystoreReader.readPrivateKey(keyStorePath, password, id, password);
		return privateKey;
	}
	
	public Certificate getCertificate() {
		return this.keystoreReader.readCertificate(keyStorePath, password, id);
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
	
	
	
	
	
	
}
