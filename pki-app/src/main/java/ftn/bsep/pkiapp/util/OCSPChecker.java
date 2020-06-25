package ftn.bsep.pkiapp.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.x509.extension.X509ExtensionUtil;



public class OCSPChecker {
	ArrayList<BigInteger> revokedIDs;
	
	
	
	
	public OCSPChecker() {
		this.revokedIDs = new ArrayList<BigInteger>();
	}




	public OCSPChecker(ArrayList<BigInteger> revokedIDs) {
		super();
		this.revokedIDs = revokedIDs;
	}


	public X509Certificate[] makeCertChain(X509Certificate certificate) {
		  byte[] octetBytes = certificate
		            .getExtensionValue(Extension.authorityInfoAccess.getId());

		    DLSequence dlSequence = null;
		    ASN1Encodable asn1Encodable = null;
		    DERTaggedObject derTaggedObject = null;

		    try {
		        ASN1Primitive fromExtensionValue = X509ExtensionUtil
		                .fromExtensionValue(octetBytes);
		        	
		        if (!(fromExtensionValue instanceof DLSequence))
		            return null;
		        dlSequence = (DLSequence) fromExtensionValue;
		        for (int i = 0; i < dlSequence.size(); i++) {
		            asn1Encodable = dlSequence.getObjectAt(i);
		            if (asn1Encodable instanceof DLSequence)
		                break;
		        }
		        if (!(asn1Encodable instanceof DLSequence))
		            return null;
		        dlSequence = (DLSequence) asn1Encodable;
		        for (int i = 0; i < dlSequence.size(); i++) {
		            asn1Encodable = dlSequence.getObjectAt(i);
		            if (asn1Encodable instanceof DERTaggedObject) {
		            	derTaggedObject = (DERTaggedObject) asn1Encodable;
				        byte[] encoded = derTaggedObject.getEncoded();
				        if (derTaggedObject.getTagNo() == 6) {
				            int len = encoded[1];
				            System.out.println(new String(encoded, 2, len));
				        }
		            }
		        }
		        
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		return null;
	}

	public boolean isCertValid(X509Certificate certificate) throws CertificateExpiredException, CertificateNotYetValidException {
		
		
		//certificate.checkValidity();
		
		return false;
	}
}
