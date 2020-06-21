package ftn.bsep.pkiapp.util;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.x509.KeyUsage;

public class KeyUsageString {
	Map<String, String> map;

	public KeyUsageString() {
		
		this.map = new HashMap<String, String>();
		this.map.put("2", "cRLSign");
		this.map.put("10", "dataEncipherment");
		this.map.put("80", "digitalSignature");
		this.map.put("8", "keyAgreement");
		this.map.put("4", "keyCertSign");
		this.map.put("20", "keyEncipherment");
		this.map.put("40", "nonRepudiation");
	}
	
	public String getKeyUsageString(String key) {
		return this.map.get(key);
	}
	
	
	
}
