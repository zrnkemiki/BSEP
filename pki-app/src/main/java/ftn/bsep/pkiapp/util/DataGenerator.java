package ftn.bsep.pkiapp.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import ftn.bsep.pkiapp.data.IssuerData;
import ftn.bsep.pkiapp.data.RootData;
import ftn.bsep.pkiapp.data.SubjectData;

public class DataGenerator {

	public RootData generateRootData() {
		try {
			KeyPair keyPairSubject = generateKeyPair();

			// Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2022-12-31");

			// Serijski broj sertifikata
			String sn = "1";
			// klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o
			// vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, "DFRoot");
			//builder.addRDN(BCStyle.SURNAME, "Marko");
			//builder.addRDN(BCStyle.GIVENNAME, "Kopanja");
			builder.addRDN(BCStyle.O, "DefenceFirst");
			builder.addRDN(BCStyle.OU, "PKI");
			//builder.addRDN(BCStyle.C, "RS");
			//builder.addRDN(BCStyle.E, "sladicg@uns.ac.rs");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "000001");

			// Kreiraju se podaci za sertifikat, sto ukljucuje:
			// - javni kljuc koji se vezuje za sertifikat
			// - podatke o vlasniku
			// - serijski broj sertifikata
			// - od kada do kada vazi sertifikat
			return new RootData(sn, builder.build(), startDate, endDate, keyPairSubject.getPrivate(),
					keyPairSubject.getPublic());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SubjectData generateCAData(KeyPair keyPairSubject) {
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
			builder.addRDN(BCStyle.CN, "DF-RS-CA");
			builder.addRDN(BCStyle.SURNAME, "Jovan");
			builder.addRDN(BCStyle.GIVENNAME, "Lakovic");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "PKI");
			builder.addRDN(BCStyle.C, "RS");
			builder.addRDN(BCStyle.E, "lakovic.j4@gmail.com");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "111111");

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
	
	public SubjectData generateMUCAData(KeyPair keyPairSubject) {
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
			builder.addRDN(BCStyle.CN, "DF-RS-MU1-CA");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "PKI");
			builder.addRDN(BCStyle.C, "RS");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "876894");

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
	
	public SubjectData generateCACenterData(KeyPair keyPairSubject) {
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
			builder.addRDN(BCStyle.CN, "DF-RS-CA-Center");
			builder.addRDN(BCStyle.SURNAME, "Nikola");
			builder.addRDN(BCStyle.GIVENNAME, "Nikolic");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "PKI");
			builder.addRDN(BCStyle.C, "RS");
			builder.addRDN(BCStyle.E, "nikolic@df.com");
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
	
	public SubjectData generateCAAgentData(KeyPair keyPairSubject) {
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
			builder.addRDN(BCStyle.CN, "DF-RS-CA-Agent");
			builder.addRDN(BCStyle.SURNAME, "Nikola");
			builder.addRDN(BCStyle.GIVENNAME, "Nikolic");
			builder.addRDN(BCStyle.O, "DF");
			builder.addRDN(BCStyle.OU, "PKI");
			builder.addRDN(BCStyle.C, "RS");
			builder.addRDN(BCStyle.E, "nikolic@df.com");
			// UID (USER ID) je ID korisnika
			builder.addRDN(BCStyle.UID, "654321");

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

	public IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, "Nikola Luburic");
		builder.addRDN(BCStyle.SURNAME, "Luburic");
		builder.addRDN(BCStyle.GIVENNAME, "Nikola");
		builder.addRDN(BCStyle.O, "UNS-FTN");
		builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, "nikola.luburic@uns.ac.rs");
		// UID (USER ID) je ID korisnika
		builder.addRDN(BCStyle.UID, "654321");

		// Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
		// - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
		// - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(builder.build(), issuerKey);
	}

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
