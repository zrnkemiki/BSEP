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
		Util.generateKeyStore("D:\\BSEP\\AdminHelper\\recources\\ClientCert.cer", "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key", "password", "password", "server", "D:\\BSEP\\AdminHelper\\recources\\client-keystore.jks");
			
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
				Util.writeCSRToFileBase64Encoded(csr, "D:\\BSEP\\AdminHelper\\recources\\csrClient.pem");
				//Util.writeCertToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\CA-cer.cer");
				Util.writePrivateKeyToFilePem(keyPairSubject, "D:\\BSEP\\AdminHelper\\recources\\privateKeyClient.key");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("CSR nije generisan");
		}
		
*/
	}

}
