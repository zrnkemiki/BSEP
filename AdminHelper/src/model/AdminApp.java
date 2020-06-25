package model;
import java.security.Security;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import view.MyFrame;

public class AdminApp {

	public static void main(String[] args) throws Exception {
		
		Security.addProvider(new BouncyCastleProvider());
		MyFrame frame = MyFrame.getInstance();
		frame.setVisible(true); 
		
	}

}
