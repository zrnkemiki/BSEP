package ftn.bsep.pkiapp.certificates;

import java.security.PrivateKey;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.model.CertificateAuthority;

public class CSRGenerator {

	public PKCS10CertificationRequest csr;
	public KeysGenerator kg;

	public CSRGenerator() {
		
	}
	
	public CSRGenerator(SubjectData subjectData) {
		
	}
	
	public CSRGenerator(PKCS10CertificationRequest csr) {
		super();
		this.csr = csr;
	}

	public PKCS10CertificationRequest getCsr() {
		return csr;
	}

	public void setCsr(PKCS10CertificationRequest csr) {
		this.csr = csr;
	}
	
	//mogu i da se dodaju ekstenzije u csr - al mozda mozemo da izbegnemo pogadjanjem specificnih endpointova
	public static PKCS10CertificationRequest generateCSR(SubjectData subjectData) throws OperatorCreationException {
		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(subjectData.getX500name(), subjectData.getPublicKey());       
     
		JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
		contentSignerBuilder = contentSignerBuilder.setProvider("BC");
		ContentSigner contentSigner = contentSignerBuilder.build(subjectData.getPrivateKey());  
		PKCS10CertificationRequest csr = p10Builder.build(contentSigner);
        return csr;
	}
	
	
}
