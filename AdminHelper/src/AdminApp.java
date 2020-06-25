import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemReader;

public class AdminApp {

	public static void main(String[] args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		//Util.generateKeyStore("D:\\BSEP\\AdminHelper\\recources\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "server", "D:\\BSEP\\AdminHelper\\recources\\client-keystore.jks");
		//KeyStoreReader ksr = new KeyStoreReader();
		//X509Certificate cert = (X509Certificate) ksr.readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\client-keystore.jks", "password", "ca-rs");
		//System.out.println(cert.getSubjectX500Principal());
		//Util.writeCertToFileBase64Encoded(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot.pem");
		//Util.writeCertToPem(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\ClientCert.pem");
		//Util.readCSRToFileBase64Encoded("D:\\BSEP\\AdminHelper\\recources\\csrClient.pem");
		//Util.generateKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "client", "D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\client-keystore.jks");
		DataGenerator dataGen = new DataGenerator();
		//------------------------------------------------------------------------------------------------
		//Pravljenje SIEMAgent Data -- TO-DO - Unos parametara za formiranje Subjekta i Ekstenzija
		/*
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateClientData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateClientExtensions();
		*/
		//------------------------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------------------------
		//Pravljenje SIEMServer Data -- TO-DO - Unos parametara za formiranje Subjekta i Ekstenzija
		
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateSSLData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateServerExtensions();
		
		//------------------------------------------------------------------------------------------------
	
		//-------------------------------------------------------------------------------------------------
		//Generisi CSR zahtev -> Opcija 1
		
		CSRGenerator csrGen = new CSRGenerator();		
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
			Util.writeCSRToFileBase64Encoded(csr, "D:\\BSEP\\AdminHelper\\recources\\csrs\\SiemCenter\\csrSiemCenter.pem");
			Util.writePrivateKeyToFilePem(keyPairSubject, "D:\\BSEP\\AdminHelper\\recources\\csrs\\SiemCenter\\privateKeySiemCenter.key");
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
		//----------------------------------------------------------------------------------------------------
		
		//-----------------------------------------------------------------------------------------------------
		//Generisi KeyStore - params(certFile, privateKeyFile, keyStorePassword, privateKeyPassword, alias, keyStoreFile) -> Opcija 2
		 //Util.generateKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\CA-RS-Cert.cer", "D:\\BSEP\\AdminHelper\\recources\\CAprivateKey.key", "password", "password", "ca-rs", "D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-rs-keystore.jks");
		//-------------------------------------------------------------------------------------------------------------------
		//KeyStoreReader ksr = new KeyStoreReader();
		//PrivateKey privateKey = ksr.readPrivateKey("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\server-keystore.jks", "password", "ca-rs", "password");
		//Certificate cert = ksr.readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\server-keystore.jks", "password", "ca-rs");
		//Util.writePrivate(privateKey,"D:\\BSEP\\AdminHelper\\recources\\serverKey.key" );
		//Util.writeCertToPem(cert,"D:\\BSEP\\AdminHelper\\recources\\serverCert.cer");
		//Generisi TrustStore - params(Mapa(alias, certFile), KeyStoreFile, password) -> Opcija 3
		//Util.generateTrustStore(certs, keyStoreFile, password);
		
		//----------------------------------------------------------------------------------------------------------------
		//Export cert for Agent in .pem format -> Opcija 4
		/*
		Certificate certificate = Util.readCertificateFromPem(filePath);
		Util.writeCertToPem(Certificate certificate, certFilePath)
		*/
	}

}
