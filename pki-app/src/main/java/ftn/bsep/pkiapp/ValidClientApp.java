package ftn.bsep.pkiapp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
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
	        SSLContext context = SSLContext.getInstance("TLS");

	        KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        //char[] keyStorePassword = "password".toCharArray(); 
	        char[] keyStorePassword = "secret".toCharArray();
	        keyStore.load(new FileInputStream("C:\\Users\\Laptop\\Desktop\\mutual tls\\client\\src\\test\\resources\\identity.jks"), keyStorePassword);
	        keyMgrFactory.init(keyStore, keyStorePassword);


	        TrustManagerFactory trustStrFactory = TrustManagerFactory.getInstance("SunX509");
	        KeyStore trustStore = KeyStore.getInstance("JKS");
	        //char[] trustStorePassword = "password".toCharArray();   
	        char[] trustStorePassword = "secret".toCharArray(); 
	        trustStore.load(new FileInputStream("C:\\Users\\Laptop\\Desktop\\mutual tls\\client\\src\\test\\resources\\truststore.jks"), trustStorePassword);
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


	        URL url = new URL("https://localhost:9005/b");

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
