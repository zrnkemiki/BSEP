package ftn.bsep.pkiapp.controllers;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.certificates.CSRGenerator;
import ftn.bsep.pkiapp.certificates.CertificateGenerator;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;
import ftn.bsep.pkiapp.model.CACountry;
import ftn.bsep.pkiapp.model.CertificateAuthority;
import ftn.bsep.pkiapp.model.Csr;
import ftn.bsep.pkiapp.services.CSRService;
import ftn.bsep.pkiapp.util.CertHelper;
import ftn.bsep.pkiapp.util.DataGenerator;

@RestController
@RequestMapping("/country-ca")
public class CountryCAController {
	@Autowired
	CSRService csrService;
	
	CertificateAuthority ca = new CACountry("D:\\BSEP\\pki-app\\src\\main\\resources\\rootStores\\DFRoot-keystore.jks", null, "password", "root");
	CSRGenerator csrGen = new CSRGenerator();
	DataGenerator dataGen = new DataGenerator();
	PKCS10CertificationRequest csr = null;
	
	@GetMapping("/gen-root")
	public String genRoot() throws Exception {
		RootData rootData = dataGen.generateRootData();
		CertificateGenerator certGen = new CertificateGenerator();
		X509Certificate rootCert = certGen.generateSelfSignedCertificate(rootData);
		
		
		return "Ok";
	}
	
	
	//TEST CONTROLER
	@PostMapping(value = "/csrData")
	public ResponseEntity<?> csrDataSubmit(@RequestBody()String csrString) throws IOException, OperatorCreationException, PKCSException {
		PKCS10CertificationRequest csr = CertHelper.csrStringToCsrPKCS(csrString);
		Csr csrDTO = null;
		boolean isCSRValid = CertHelper.checkCSRSigniture(csr);
		
		if(isCSRValid) {
			System.out.println("CSR potpis je validan");
			csrDTO = CertHelper.csrStringToCsrObj(csrString);
			csrDTO.setCsrStringReq(csrString);
		}else {
			System.out.println("CSR potpis NIJE validan");
		}
		
		try {
			//TO-DO
			System.out.println(csrString);
			
			System.out.println(csrDTO.getCommonName());
			System.out.println(csrDTO.getExtensions().get(0));
			csrDTO = csrService.saveCSR(csrDTO);
			csrDTO.getId();
			csrDTO = csrService.findByID(csrDTO.getId());
			System.out.println("Ovo je string iz baze: " + csrDTO.getCsrStringReq());
			
			
            return new ResponseEntity<Csr>(
            		csrDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<Csr> csrs = csrService.findAll();



		try {
			return new ResponseEntity<>(csrs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getCSR/{param}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Csr> getCSR(@PathVariable("param") Long id) throws Exception {
		Csr csr = csrService.findByID(id);
		try {
			return new ResponseEntity<>(csr, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/generateCertificate/{param}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Csr> generateCertificate(@PathVariable("param") Long id) throws Exception {
		System.out.println("A");
		Csr csr = csrService.findByID(id);
		
		PKCS10CertificationRequest csrPkcs = CertHelper.csrStringToCsrPKCS(csr.getCsrStringReq());
		X509Certificate cert = ca.signCertificate(csrPkcs);
		CertHelper.writeCertToFileBase64Encoded((Certificate)cert, "D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\ClientCert.cer");
		
		try {
			return new ResponseEntity<>(csr, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
