package ftn.bsep.pkiapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class ValidClientApp {

	
	public static void main(String[] args) {
		launchApi();
	}
	private static SSLSocketFactory getSocketFactory() 
	{
	    try 
	    {
	    	KeyStore keyStore2 = null;
			try {
				keyStore2 = KeyStore.getInstance("JKS", "SUN");
			} catch (KeyStoreException | NoSuchProviderException e) {
				e.printStackTrace();
			}
			BufferedInputStream in = null;
			try {
				in = new BufferedInputStream(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\server-truststore.jks"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				keyStore2.load(in, "password".toCharArray());
			} catch (NoSuchAlgorithmException | CertificateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PKIXParameters pkixParams = null;
			try {
				pkixParams = new PKIXParameters(keyStore2);
				pkixParams.setRevocationEnabled(true);
				
			} catch (KeyStoreException | InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(pkixParams.isRevocationEnabled());
			Security.setProperty("ocsp.enable", "true");
	        SSLContext context = SSLContext.getInstance("TLS");
	        KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        //char[] keyStorePassword = "password".toCharArray(); 
	        char[] keyStorePassword = "password".toCharArray();
	        keyStore.load(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\client-keystore.jks"), keyStorePassword);
	        keyMgrFactory.init(keyStore, keyStorePassword);


	        TrustManagerFactory trustStrFactory = TrustManagerFactory.getInstance("SunX509");
	        KeyStore trustStore = KeyStore.getInstance("JKS");
	        //char[] trustStorePassword = "password".toCharArray();   
	        char[] trustStorePassword = "password".toCharArray(); 
	        trustStore.load(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\newCerts\\server-truststore.jks"), trustStorePassword);
	        trustStrFactory.init(trustStore);

	        context.init(keyMgrFactory.getKeyManagers(), 
	                trustStrFactory.getTrustManagers(), null);

	        return context.getSocketFactory();
	    } 
	    catch (Exception e) 
	    {
	        System.err.println("Failed to create a server socket factory...");
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static void launchApi() 
	{
	    try 
	    {
	        HostnameVerifier hv = new HostnameVerifier() 
	        {
	            public boolean verify(String urlHostname, SSLSession session)
	            {
	                return true;
	            }};

	        HttpsURLConnection.setDefaultHostnameVerifier(hv);

	        
	        URL url = new URL("https://localhost:9005/ok");

	        HttpsURLConnection.setDefaultSSLSocketFactory(getSocketFactory());
	        HttpsURLConnection urlConn = (HttpsURLConnection)url.openConnection();

	    
	        urlConn.setRequestMethod("GET");
	        urlConn.setRequestProperty("Accept", "application/json;charset=UTF8");
	        urlConn.setRequestProperty("Cache-Control", "no-cache");

	        urlConn.connect();

	        BufferedReader in = 
	            new BufferedReader(
	                    new InputStreamReader(urlConn.getInputStream()));
	        String inputLine = null;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}
}
