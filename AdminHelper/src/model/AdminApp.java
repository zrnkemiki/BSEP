package model;
import java.security.Security;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import view.MyFrame;

public class AdminApp {

	public static void main(String[] args) throws Exception {
		
		Security.addProvider(new BouncyCastleProvider());
		MyFrame frame = MyFrame.getInstance();
		frame.setVisible(true); 
		
		//Util.generateKeyStore("D:\\BSEP\\AdminHelper\\recources\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "server", "D:\\BSEP\\AdminHelper\\recources\\client-keystore.jks");
		KeyStoreReader ksr = new KeyStoreReader();
		X509Certificate cert = (X509Certificate) ksr.readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\CAStores\\ca-rs-keystore.jks", "password", "ca-rs");
		System.out.println(cert.getSubjectDN().getName());
		String email = cert.getSubjectDN().getName().split("EMAILADDRESS=")[1];
		email = email.split(",")[0];
		System.out.println(email);
		int start = cert.getSubjectDN().getName().indexOf("E");
		String tmpName = "", name = "";
		if (start > 0) { 
		  tmpName = cert.getSubjectDN().getName().substring(start+3);
		  int end = tmpName.indexOf(",");
		  if (end > 0) {
		    name = tmpName.substring(0, end);
		  }
		  else {
		    name = tmpName; 
		  }
		}
		System.out.println(name + "aaaaaaaaaaaaaaaaaaaaaa");
		
		String dn = cert.getSubjectX500Principal().getName();
		LdapName ldapDN = new LdapName(dn);
		for(Rdn rdn: ldapDN.getRdns()) {
			if(rdn.getType().equalsIgnoreCase("1.2.840.113549.1.9.1"))
		    System.out.println(rdn.getType() + " -> " + rdn.getValue());
		}
		//System.out.println(cert.getSubjectX500Principal());
		//Util.writeCertToFileBase64Encoded(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot.pem");
		//Util.writeCertToPem(cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\ClientCert.pem");
		//Util.readCSRToFileBase64Encoded("D:\\BSEP\\AdminHelper\\recources\\csrClient.pem");
		//Util.generateKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "client", "D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\client-keystore.jks");
		//DataGenerator dataGen = new DataGenerator();
		//------------------------------------------------------------------------------------------------
		//1Pravljenje SIEMAgent Data -- TO-DO - Unos parametara za formiranje Subjekta i Ekstenzija
		/*
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateClientData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateClientExtensions();
		*/
		//------------------------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------------------------
		//1Pravljenje SIEMServer Data -- TO-DO - Unos parametara za formiranje Subjekta i Ekstenzija
		/*
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateSSLData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateServerExtensions();
		*/
		//------------------------------------------------------------------------------------------------
	
		//-------------------------------------------------------------------------------------------------
		//Generisi CSR zahtev -> Opcija 1
		/*
		CSRGenerator csrGen = new CSRGenerator();		
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
			Util.writeCSRToFileBase64Encoded(csr, "D:\\BSEP\\AdminHelper\\recources\\csrs\\csrName.pem");
			Util.writePrivateKeyToFilePem(keyPairSubject, "D:\\BSEP\\AdminHelper\\recources\\csrs\\privateKeyName.key");
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		*/
		//----------------------------------------------------------------------------------------------------
		
		//-----------------------------------------------------------------------------------------------------
		//Generisi KeyStore - params(certFile, privateKeyFile, keyStorePassword, privateKeyPassword, alias, keyStoreFile) -> Opcija 2
		//Util.generateKeyStore("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "client", "D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\client-keystore.jks");
		//-------------------------------------------------------------------------------------------------------------------
		
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
