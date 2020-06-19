import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

public class CSRGenerator {

	public PKCS10CertificationRequest csr;
	

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
	public PKCS10CertificationRequest generateCSR(SubjectData subjectData, ArrayList<CSRExtension> extensions) throws OperatorCreationException, IOException {
		
		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(subjectData.getX500name(), subjectData.getPublicKey());       
		
		//Dodavanje ektenzija
		for(CSRExtension extension : extensions) {
			ExtensionsGenerator extGen = new ExtensionsGenerator();
			extGen.addExtension(extension.getOid(), extension.isCritical(), extension.getValue());
			p10Builder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, extGen.generate());
			
		}
		
		
		
		JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
		contentSignerBuilder = contentSignerBuilder.setProvider("BC");
		ContentSigner contentSigner = contentSignerBuilder.build(subjectData.getPrivateKey());  
		PKCS10CertificationRequest csr = p10Builder.build(contentSigner);
        return csr;
	}
	

	
	
}




