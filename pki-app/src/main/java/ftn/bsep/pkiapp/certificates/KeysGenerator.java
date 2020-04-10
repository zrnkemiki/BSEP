package ftn.bsep.pkiapp.certificates;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class KeysGenerator {
	
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
	
	
	public static KeyPair generateKeyPair(String keygenAlg, String secureRndAlg, String secureRndProvider, int keySize) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keygenAlg);
			SecureRandom random = SecureRandom.getInstance(secureRndAlg, secureRndProvider);
			keyGen.initialize(keySize, random);
			
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
