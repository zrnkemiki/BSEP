package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import model.CSRExtension;
import model.CSRGenerator;
import model.DataGenerator;
import model.SubjectData;
import model.Util;


public class MyFrame extends JFrame {

	private static MyFrame instance;
	
	
	public static MyFrame getInstance() {
		if(instance == null) {
			instance = new MyFrame();
		}
		
		return instance;
	}
	private MyFrame() {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setTitle("Admin helper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MyMenuBar menu=new MyMenuBar();
        this.setJMenuBar(menu);

  }
	
	public void generateCsrSiemRequest(String fullPath) {
		DataGenerator dataGen = new DataGenerator();
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateClientData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateClientExtensions();
		
		CSRGenerator csrGen = new CSRGenerator();		
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
			try {
				Util.writeCSRToFileBase64Encoded(csr, fullPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Util.writePrivateKeyToFilePem(keyPairSubject, fullPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void generateCsrAgentRequest(String fullPath) {
		DataGenerator dataGen = new DataGenerator();
		KeyPair keyPairSubject = Util.generateKeyPair();
		SubjectData subjectData = dataGen.generateSSLData(keyPairSubject);
		ArrayList<CSRExtension> extensions = dataGen.generateServerExtensions();
		
		CSRGenerator csrGen = new CSRGenerator();		
		PKCS10CertificationRequest csr = null;
		try {
			csr = csrGen.generateCSR(subjectData, extensions);
			try {
				Util.writeCSRToFileBase64Encoded(csr, fullPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Util.writePrivateKeyToFilePem(keyPairSubject, fullPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (OperatorCreationException | IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void generateKeyStore(String certFile,String privateKeyFile, 
			String keyStorePassword, String privateKeyPassword, String alias, String keyStoreFile) {
	
		Util.generateKeyStore(certFile, privateKeyFile, keyStorePassword, 
				privateKeyPassword, alias, keyStoreFile);
		
	}
	
	public void generateTrustStore(String keyStoreFile,String password) {
	
			Util.generateTrustStore(keyStoreFile, password);
		
	}
	
	public void exportCert(String filePath, String certFilePath) {
		
		Certificate certificate;
		try {
			certificate = Util.readCertificateFromPem(filePath);
			Util.writeCertToPem(certificate, certFilePath);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
}
