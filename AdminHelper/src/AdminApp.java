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
		boolean on = true;
		Security.addProvider(new BouncyCastleProvider());
		
		KeyStoreWriter ksw = new KeyStoreWriter();
		//KeyStoreReader ksr = new KeyStoreReader();
		//Certificate cert = ksr.readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", "password", "root");
		ksw.loadKeyStore(null, "password".toCharArray());
		//ksw.storeCertificate("root", cert);
		PrivateKey privateKey = null;
		X509Certificate cert = null;
		
		
		
		try {
			cert = (X509Certificate) Util.readCertificateFromPem("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\CACSRCert.cer");
		} catch (CertificateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			privateKey = Util.readPrivateKeyFromPem("C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\privateKeyCA.key");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
		
		// create a challenge
		byte[] challenge = new byte[10000];
		ThreadLocalRandom.current().nextBytes(challenge);

		// sign using the private key
		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initSign(privateKey);
		sig.update(challenge);
		byte[] signature = sig.sign();

		// verify signature using the public key
		sig.initVerify(cert.getPublicKey());
		sig.update(challenge);

		boolean keyPairMatches = sig.verify(signature);
	
		System.out.println(keyPairMatches);
		ksw.write("ca-rs", privateKey, "password".toCharArray(),(java.security.cert.Certificate) cert);
		ksw.saveKeyStore("C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\CA-keystore.jks", "password".toCharArray());
		
		
		
		/*
		KeyPair keyPairSubject = Util.generateKeyPair();
	
		Security.addProvider(new BouncyCastleProvider());
		CSRGenerator csrGen = new CSRGenerator();
		DataGenerator dataGen = new DataGenerator();
				
		SubjectData subjectData = dataGen.generateSubjectData(keyPairSubject);
		
		ArrayList<CSRExtension> extensions = dataGen.generateCAExtensions();
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
		if(csr != null) {
			try {
				//Prvi koristi za upis csr
				Util.writeCSRToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrCA.pem");
				//Util.writeCertToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\CA-cer.cer");
				Util.writePrivateKeyToFilePem(keyPairSubject, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\privateKeyCA.key");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("CSR nije generisan");
		}
		
		try {
			Util.readCSRToFileBase64Encoded("C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrCA.pem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

	}

}
