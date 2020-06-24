package ftn.bsep.pkiapp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import ftn.bsep.pkiapp.keystores.KeyStoreReader;

@SpringBootApplication
public class PkiAppApplication{

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
	   	

		SpringApplication.run(PkiAppApplication.class, args);
	}

}
