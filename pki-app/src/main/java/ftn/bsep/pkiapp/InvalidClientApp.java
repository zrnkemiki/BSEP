package ftn.bsep.pkiapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class InvalidClientApp {
	

	public static void main(String[] args) {
		launchApi();
	}
	private static SSLSocketFactory getSocketFactory() 
	{
	    try 
	    {
	        SSLContext context = SSLContext.getInstance("TLS");

	        KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        char[] keyStorePassword = "password".toCharArray(); 
	        keyStore.load(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\https-example.jks"), keyStorePassword);
	        keyMgrFactory.init(keyStore, keyStorePassword);


	        TrustManagerFactory trustStrFactory = TrustManagerFactory.getInstance("SunX509");
	        KeyStore trustStore = KeyStore.getInstance("JKS");
	        char[] trustStorePassword = "password".toCharArray();          
	        trustStore.load(new FileInputStream("D:\\BSEP\\pki-app\\src\\main\\resources\\client-truststore.jks"), trustStorePassword);
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
	        //  Uncomment this if your server cert is not signed by a trusted CA              
	        HostnameVerifier hv = new HostnameVerifier() 
	        {
	            public boolean verify(String urlHostname, SSLSession session)
	            {
	                return true;
	            }};

	        HttpsURLConnection.setDefaultHostnameVerifier(hv);


	        URL url = new URL("https://localhost:9003/hello");

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
