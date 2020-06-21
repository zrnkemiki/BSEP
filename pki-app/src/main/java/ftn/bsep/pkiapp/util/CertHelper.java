package ftn.bsep.pkiapp.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.io.pem.PemObject;

import ftn.bsep.pkiapp.model.Csr;

public class CertHelper {
	
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
	
	public static void writeCertToFileBase64Encoded(X509Certificate certificate, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(certificate.getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
    }
	
	static void writeCSRToFileBase64Encoded(PKCS10CertificationRequest csr, String fileName) throws Exception {
        FileOutputStream certificateOut = new FileOutputStream(fileName);
        certificateOut.write("-----BEGIN CERTIFICATE-----".getBytes());
        certificateOut.write(Base64.getEncoder().encode(csr.getEncoded()));
        certificateOut.write("-----END CERTIFICATE-----".getBytes());
        certificateOut.close();
    }
	
	
	public static Csr csrStringToCsrObj(String csrString) throws IOException {
		Csr csrObj = new Csr();
		String extension;
		String target = "-----BEGIN CERTIFICATE REQUEST-----";
        String replacement = "";
        csrString = csrString.replace(target, replacement);
        
        target = "-----END CERTIFICATE REQUEST-----";
        replacement = "";
        csrString = csrString.replace(target, replacement);
        
        
        PemObject pemObject = new PemObject("CERTIFICATE REQUEST", Base64.getDecoder().decode(csrString));
       
        PKCS10CertificationRequest csr = new PKCS10CertificationRequest(pemObject.getContent());
        
        System.out.println();
        String[] x500 = csr.getSubject().toString().split(",");
        csrObj = parseX500Name(x500, csrObj);
        
        for(Attribute attr : csr.getAttributes()) {
        	extension = "";
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
            	extension = "KeyUsage: " + keyUsageString + ", Is Critical: " + isCritical;
            		
    			
    		}else if(Extension.basicConstraints.equals(oid)) {
    			DEROctetString str = (DEROctetString) value;
    			BasicConstraints bc = BasicConstraints.getInstance(str.getOctets());
    			extension = bc.toString() + ", Is Critical: " + isCritical;
    			
    		}else if(Extension.extendedKeyUsage.equals(oid)) {
    			ExtendedKeyUsageString kuHelper = new ExtendedKeyUsageString();
    			
    			DEROctetString str = (DEROctetString) value;
    			ExtendedKeyUsage ku = ExtendedKeyUsage.getInstance(str.getOctets());
    			String s = ku.toASN1Primitive().toString();
            	s = s.substring(1, s.length() - 1);

            	String keyUsageString = kuHelper.getKeyUsageString(s);
            	
            	System.out.println("Extended key usage: " + keyUsageString + "| Critical: " + isCritical);
            	extension = "Extended key usage: " + keyUsageString + ", Is Critical: " + isCritical;
    		}else if(Extension.subjectAlternativeName.equals(oid)) {
    			DEROctetString str = (DEROctetString) value;
    			GeneralNames subjectAltName = GeneralNames.getInstance(str.getOctets());
    			extension = "Subject Alternitave Names: " + subjectAltName + " Is Critical: " + isCritical;
    		}
        	csrObj.addExtension(extension);
        	}
		return csrObj;
	}
	
	public static Csr parseX500Name(String[] x500, Csr csr) {
		for(int i = 0; i < x500.length; i++) {
			String[] temp = x500[i].split("=");
			if(temp[0].equals("CN")) {
				csr.setCommonName(temp[1]);
			}else if(temp[0].equals("SURNAME")) {
				csr.setSurname(temp[1]);
			}else if(temp[0].equals("GIVENNAME")) {
				csr.setGivenName(temp[1]);
			}else if(temp[0].equals("O")) {
				csr.setOrganization(temp[1]);
			}else if(temp[0].equals("OU")) {
				csr.setOrganizationUnit(temp[1]);
			}else if(temp[0].equals("C")) {
				csr.setCountry(temp[1]);
			}else if(temp[0].equals("E")) {
				csr.setEmail(temp[1]);
			}else if(temp[0].equals("UID")) {
				csr.setUid(temp[1]);
			}
		}
		
		return csr;
	}

}
