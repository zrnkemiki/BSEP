import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemReader;

public class AdminApp {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		CSRGenerator csrGen = new CSRGenerator();
		DataGenerator dataGen = new DataGenerator();
		
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateSubjectData(keyPairSubject);
		
		ArrayList<CSRExtension> extensions = dataGen.generatServerExtensions();
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
		if(csr != null) {
			try {
				Util.writeCSRToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrCA.pem");
				Util.writePemToFileBase64Encoded(csr, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\csrCAPEM.pem");
				Util.writePrivateKeyToFilePem(keyPairSubject, "C:\\Users\\kopan\\eclipse-workspace\\AdminHelper\\recources\\privateKey.pem");
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
		

	}

}
