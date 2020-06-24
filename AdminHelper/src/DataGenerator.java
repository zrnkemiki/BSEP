

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;


public class DataGenerator {

	

	public SubjectData generateSubjectData(KeyPair keyPairSubject) {
		try {

			// Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			// Serijski broj sertifikata
			String sn = "1";
			// klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o
			// vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, "Marko Kopanja");
			builder.addRDN(BCStyle.SURNAME, "Marko");
			builder.addRDN(BCStyle.GIVENNAME, "Kopanja");
			builder.addRDN(BCStyle.O, "UNS-FTN");
			builder.addRDN(BCStyle.OU, "Katedra za informatiku");
			builder.addRDN(BCStyle.C, "RS");
			builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "123456");

			// Kreiraju se podaci za sertifikat, sto ukljucuje:
			// - javni kljuc koji se vezuje za sertifikat
			// - podatke o vlasniku
			// - serijski broj sertifikata
			// - od kada do kada vazi sertifikat
			return new SubjectData(builder.build(), sn, startDate, endDate, keyPairSubject.getPublic(), keyPairSubject.getPrivate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SubjectData generateSSLData(KeyPair keyPairSubject) {
		try {
			// Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			// Serijski broj sertifikata
			String sn = "6";
			// klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o
			// vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, "SIEMCenter");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "SIEM");
			builder.addRDN(BCStyle.C, "RS");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "8265433");

			// Kreiraju se podaci za sertifikat, sto ukljucuje:
			// - javni kljuc koji se vezuje za sertifikat
			// - podatke o vlasniku
			// - serijski broj sertifikata
			// - od kada do kada vazi sertifikat
			return new SubjectData(builder.build(), sn, startDate, endDate, keyPairSubject.getPublic(), keyPairSubject.getPrivate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SubjectData generateClientData(KeyPair keyPairSubject) {
		try {
			// Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			// Serijski broj sertifikata
			String sn = "6";
			// klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o
			// vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, "SIEMAgent");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "SIEM");
			builder.addRDN(BCStyle.C, "RS");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "213432");

			// Kreiraju se podaci za sertifikat, sto ukljucuje:
			// - javni kljuc koji se vezuje za sertifikat
			// - podatke o vlasniku
			// - serijski broj sertifikata
			// - od kada do kada vazi sertifikat
			return new SubjectData(builder.build(), sn, startDate, endDate, keyPairSubject.getPublic(), keyPairSubject.getPrivate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<CSRExtension> generateCAExtensions() {
		
		ArrayList<CSRExtension> extensions = new ArrayList<CSRExtension>();
		// Use BasicConstraints to say that this Cert is CA
		CSRExtension ext = new CSRExtension(Extension.basicConstraints, true, new BasicConstraints(true));
        extensions.add(ext);
        
        //KeyUsage for CA
        KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.cRLSign | KeyUsage.digitalSignature);
        ext = new CSRExtension(Extension.keyUsage, true, keyUsage);
        extensions.add(ext);
		return extensions;
		
	}

public ArrayList<CSRExtension> generateServerExtensions() {
		
		ArrayList<CSRExtension> extensions = new ArrayList<CSRExtension>();

		CSRExtension ext = new CSRExtension(Extension.basicConstraints, true, new BasicConstraints(false));
        extensions.add(ext);

        ext = new CSRExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.nonRepudiation | KeyUsage.keyEncipherment | KeyUsage.keyAgreement));
        extensions.add(ext);
        GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost"));
        ext = new CSRExtension(Extension.subjectAlternativeName, true, subjectAltName.toASN1Primitive());
        extensions.add(ext);
        
        ext = new CSRExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        
        extensions.add(ext);
        return extensions;
	}

public ArrayList<CSRExtension> generateOCSPExtensions(){
	ArrayList<CSRExtension> extensions = new ArrayList<CSRExtension>();

	CSRExtension ext = new CSRExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.nonRepudiation | KeyUsage.keyEncipherment | KeyUsage.keyAgreement));
    extensions.add(ext);
    
    ext = new CSRExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_OCSPSigning));
    extensions.add(ext);
    return extensions;
}

//TO-DO
public ArrayList<CSRExtension> generateClientExtensions() {
	
	ArrayList<CSRExtension> extensions = new ArrayList<CSRExtension>();

	CSRExtension ext = new CSRExtension(Extension.basicConstraints, true, new BasicConstraints(false));
    extensions.add(ext);
    
    //KeyUsage for CA
    ext = new CSRExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.nonRepudiation | KeyUsage.keyEncipherment | KeyUsage.keyAgreement));
    extensions.add(ext);
    
    ext = new CSRExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
    extensions.add(ext);
    
    AccessDescription caIssuers = new AccessDescription(AccessDescription.id_ad_caIssuers,
            new GeneralName(GeneralName.uniformResourceIdentifier, new DERIA5String("http://localhost:9005/ca.cer")));
    AccessDescription ocsp = new AccessDescription(AccessDescription.id_ad_ocsp,
            new GeneralName(GeneralName.uniformResourceIdentifier, new DERIA5String("http://localhost:9005/b")));
     
    ASN1EncodableVector aia_ASN = new ASN1EncodableVector();
    aia_ASN.add(caIssuers);
    aia_ASN.add(ocsp);
    ext = new CSRExtension(Extension.authorityInfoAccess, false, new DERSequence(aia_ASN));
    extensions.add(ext);
	return extensions;
}

	private KeyPair generateKeyPair() {
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
}
