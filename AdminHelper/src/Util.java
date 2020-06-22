import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
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
	
	
	static void writeCSRToFileBase64Encoded(PKCS10CertificationRequest csr, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE REQUEST-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
        certificateOut.write("-----END CERTIFICATE REQUEST-----".getBytes());
        certificateOut.close();
    }
	
	static void writePemToFileBase64Encoded(PKCS10CertificationRequest csr, String fileName) throws Exception {
        //FileOutputStream certificateOut = new FileOutputStream(fileName);
        //certificateOut.write("-----BEGIN CERTIFICATE REQUEST-----".getBytes());
        //certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
        //certificateOut.write("-----END CERTIFICATE REQUEST-----".getBytes());
        //certificateOut.close();
        try (PemWriter writer = new PemWriter(new FileWriter(fileName))) {
            writer.writeObject(new PemObject("CERTIFICATE REQUEST", csr.getEncoded()));
        }
	}
	
	static void writePrivateKeyToFilePem(KeyPair kp, String fileName) throws Exception {
        //FileOutputStream certificateOut = new FileOutputStream(fileName);
        //certificateOut.write("-----BEGIN CERTIFICATE REQUEST-----".getBytes());
        //certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
        //certificateOut.write("-----END CERTIFICATE REQUEST-----".getBytes());
        //certificateOut.close();
        try (PemWriter writer = new PemWriter(new FileWriter(fileName))) {
            writer.writeObject(new PemObject("RSA PRIVATE KEY", kp.getPrivate().getEncoded()));
        }
	}
	
	//static void write
	
	//U PKI ce se prosledjivati csrString
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
        for(Attribute attr : csr.getAttributes()) {
        	DLSequence dl = (DLSequence) attr.getAttributeValues()[0];
        	DLSequence dl2 = (DLSequence) dl.getObjectAt(0);
        	
        	ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) dl2.getObjectAt(0);
        	ASN1Encodable value;
        	boolean isCritical;
        	
        	if(dl2.size() > 2) {
        		isCritical = true;
            	value = dl2.getObjectAt(2);
        	}else {
        		isCritical = false;
        		value = dl2.getObjectAt(1);
        	}
        	
        	if(Extension.keyUsage.equals(oid)) {
        		KeyUsageString kuHelper = new KeyUsageString();
        		String ret = "KeyUsage: ";
        		
        		DEROctetString str = (DEROctetString) value;
            	KeyUsage ku = KeyUsage.getInstance(str.getOctets());
            	String s = ku.toString();
            	s = s.split("x")[1];
            	
            	String keyUsageString = kuHelper.getKeyUsageString(s);
            	
            	ret += keyUsageString + "| Critical: " + isCritical;
            	System.out.println(ret);
            
            	//Napraviti Enum            	
            		
    			
    		}else if(Extension.basicConstraints.equals(oid)) {
    			DEROctetString str = (DEROctetString) value;
    			BasicConstraints bc = BasicConstraints.getInstance(str.getOctets());
    			System.out.println(bc.toString() + "| Critical: " + isCritical);
    			
    		}else if(Extension.extendedKeyUsage.equals(oid)) {
    			ExtendedKeyUsageString kuHelper = new ExtendedKeyUsageString();
    			
    			DEROctetString str = (DEROctetString) value;
    			ExtendedKeyUsage ku = ExtendedKeyUsage.getInstance(str.getOctets());
    			String s = ku.toASN1Primitive().toString();
            	s = s.substring(1, s.length() - 1);

            	String keyUsageString = kuHelper.getKeyUsageString(s);
            	
            	System.out.println("Extended key usage: " + keyUsageString + "| Critical: " + isCritical);
    		}else if(Extension.subjectAlternativeName.equals(oid)) {
    			DEROctetString str = (DEROctetString) value;
    			GeneralNames subjectAltName = GeneralNames.getInstance(str.getOctets());
    			System.out.println(subjectAltName + "| Critical: " + isCritical);
    		}
        	}     
        
    }
	static String[] fromX500ToString(X500Name name) {
		return name.toString().split(",");
	}
}
