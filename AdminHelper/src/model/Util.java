package model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

public class Util {

	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeCSRToFileBase64Encoded(PKCS10CertificationRequest csr, String fileName) throws Exception {
		FileOutputStream certificateOut = new FileOutputStream(fileName);
		certificateOut.write("-----BEGIN CERTIFICATE REQUEST-----".getBytes());
		certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
		certificateOut.write("-----END CERTIFICATE REQUEST-----".getBytes());
		certificateOut.close();
	}

	static void writeCertToFileBase64Encoded(Certificate certificate, String fileName) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
	      byte[] certBytes = certificate.getEncoded();
	      fos.write(certBytes);
	      fos.close();

	}
	
	public static void writeCertToPem(Certificate certificate, String fileName) throws CertificateEncodingException, IOException {
		try (PemWriter writer = new PemWriter(new FileWriter(fileName))) {
			writer.writeObject(new PemObject("CERTIFICATE", certificate.getEncoded()));
		}
	}

	public static void writePrivateKeyToFilePem(KeyPair kp, String fileName) throws Exception {

		try (PemWriter writer = new PemWriter(new FileWriter(fileName))) {
			writer.writeObject(new PemObject("RSA PRIVATE KEY", kp.getPrivate().getEncoded()));
		}
	}


	static void readCSRToFileBase64Encoded(String fileName) throws Exception {

		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		String csrString = new String(encoded, StandardCharsets.US_ASCII);

		String target = "-----BEGIN CERTIFICATE REQUEST-----";
		String replacement = "";
		csrString = csrString.replace(target, replacement);

		target = "-----END CERTIFICATE REQUEST-----";
		replacement = "";
		csrString = csrString.replace(target, replacement);

		PemObject pemObject = new PemObject("CERTIFICATE REQUEST", Base64.getDecoder().decode(csrString));

		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(pemObject.getContent());

		String[] x500 = fromX500ToString(csr.getSubject());
		for (Attribute attr : csr.getAttributes()) {
			DLSequence dl = (DLSequence) attr.getAttributeValues()[0];
			DLSequence dl2 = (DLSequence) dl.getObjectAt(0);

			ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) dl2.getObjectAt(0);
			ASN1Encodable value;
			boolean isCritical;

			if (dl2.size() > 2) {
				isCritical = true;
				value = dl2.getObjectAt(2);
			} else {
				isCritical = false;
				value = dl2.getObjectAt(1);
			}

			if (Extension.keyUsage.equals(oid)) {
				KeyUsageString kuHelper = new KeyUsageString();
				String ret = "KeyUsage: ";

				DEROctetString str = (DEROctetString) value;
				KeyUsage ku = KeyUsage.getInstance(str.getOctets());
				String s = ku.toString();
				s = s.split("x")[1];

				String keyUsageString = kuHelper.getKeyUsageString(s);

				ret += keyUsageString + "| Critical: " + isCritical;
				System.out.println(ret);

				// Napraviti Enum

			} else if (Extension.basicConstraints.equals(oid)) {
				DEROctetString str = (DEROctetString) value;
				BasicConstraints bc = BasicConstraints.getInstance(str.getOctets());
				System.out.println(bc.toString() + "| Critical: " + isCritical);

			} else if (Extension.extendedKeyUsage.equals(oid)) {
				ExtendedKeyUsageString kuHelper = new ExtendedKeyUsageString();

				DEROctetString str = (DEROctetString) value;
				ExtendedKeyUsage ku = ExtendedKeyUsage.getInstance(str.getOctets());
				String s = ku.toASN1Primitive().toString();
				s = s.substring(1, s.length() - 1);

				String keyUsageString = kuHelper.getKeyUsageString(s);

				System.out.println("Extended key usage: " + keyUsageString + "| Critical: " + isCritical);
			} else if (Extension.subjectAlternativeName.equals(oid)) {
				DEROctetString str = (DEROctetString) value;
				GeneralNames subjectAltName = GeneralNames.getInstance(str.getOctets());
				System.out.println(subjectAltName + "| Critical: " + isCritical);
			}else if (Extension.authorityInfoAccess.equals(oid)) {
				DEROctetString str = (DEROctetString) value;
				ASN1Sequence ad =  DERSequence.getInstance(str.getOctets());
				for(int i = 0; i < ad.size(); i++) {
					System.out.println(ad.getObjectAt(i));
					AccessDescription acd = AccessDescription.getInstance(ad.getObjectAt(i));
					System.out.println(acd.getAccessMethod());
					System.out.println(acd.getAccessLocation());
				}
				//
				
			}
		}

	}

	static String[] fromX500ToString(X500Name name) {
		return name.toString().split(",");
	}

	public static Certificate readCertificateFromPem(String filePath) throws IOException, CertificateException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		String certString = new String(encoded, StandardCharsets.US_ASCII);

		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate cert = null;
		// cert = cf.generateCertificate(bis);
		// System.out.println(cert.toString());
		while (bis.available() > 0) {
			cert = cf.generateCertificate(bis);
			System.out.println(cert.toString());
		}
		return cert;

	}

	public static PrivateKey readPrivateKeyFromPem(String filePath)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		String certString = new String(encoded, StandardCharsets.US_ASCII);

		StringReader sreader = new StringReader(certString);
		PemReader preader = new PemReader(sreader);
		byte[] requestBytes = preader.readPemObject().getContent();
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(requestBytes);
		PrivateKey privKey = (PrivateKey) kf.generatePrivate(keySpec);

		System.out.println(privKey);
		return privKey;
	}

	public static void generateKeyStore(String certFile, String keyFile, String passwordKS, String passKey, String alias,
			String keyStoreFile) {
		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, passwordKS.toCharArray());

		PrivateKey privateKey = null;
		X509Certificate cert = null;

		try {
			cert = (X509Certificate) Util.readCertificateFromPem(certFile);
		} catch (CertificateException | IOException e1) {
			e1.printStackTrace();
		}

		try {
			privateKey = Util.readPrivateKeyFromPem(keyFile);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			if(checkPrivateKeyAndCert(cert,privateKey)) {
				ksw.write(alias, privateKey, passKey.toCharArray(),(java.security.cert.Certificate) cert);
				ksw.saveKeyStore(keyStoreFile, passwordKS.toCharArray());
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			e.printStackTrace();
		}
	}

	public static void generateTrustStore(String keyStoreFile, String password) {
		KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, password.toCharArray());
		X509Certificate cert = null;
		Map<String, String> certs= new HashMap<String,String>();
		certs.put("root", "D:\\newWorkspace\\AdminHelper\\recources\\CAs\\DFRoot.cer");
		certs.put("ca-rs", "D:\\newWorkspace\\AdminHelper\\recources\\CAs\\CA-RS-Cert.cer");
		for(Map.Entry<String, String> pair : certs.entrySet()) {
			try {
				cert = (X509Certificate) Util.readCertificateFromPem(pair.getValue());
				ksw.storeCertificate(pair.getKey(), cert);
			} catch (CertificateException | IOException e1) {
				e1.printStackTrace();
			}
		}
		ksw.saveKeyStore(keyStoreFile, password.toCharArray());
		
	}
	public static boolean checkPrivateKeyAndCert(X509Certificate cert, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		boolean isMatch = false;
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

		isMatch = sig.verify(signature);
		return isMatch;
	}
}
