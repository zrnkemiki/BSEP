import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.ArrayList;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemReader;

public class AdminApp {

	public static void main(String[] args) {
		boolean on = true;
		Security.addProvider(new BouncyCastleProvider());
		KeyStoreWriter ksw = new KeyStoreWriter();
		KeyStoreReader ksr = new KeyStoreReader();
		Certificate cert = ksr.readCertificate("D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", "password", "root");
		ksw.loadKeyStore(null, "password".toCharArray());
		ksw.storeCertificate("root", cert);
		ksw.saveKeyStore("C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\server-truststore.jks", "password".toCharArray());
		/*
		KeyPair keyPairSubject = Util.generateKeyPair();
	
		Security.addProvider(new BouncyCastleProvider());
		CSRGenerator csrGen = new CSRGenerator();
		DataGenerator dataGen = new DataGenerator();
				
		SubjectData subjectData = dataGen.generateClientData(keyPairSubject);
		
		ArrayList<CSRExtension> extensions = dataGen.generatClientExtensions();
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
		if(csr != null) {
			try {
				//Prvi koristi za upis csr
				Util.writeCSRToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrClient.pem");
				//Util.writePemToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrCAPEM.pem");
				Util.writePrivateKeyToFilePem(keyPairSubject, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\privateKeyClient.pem");
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
