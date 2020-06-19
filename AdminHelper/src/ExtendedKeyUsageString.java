import java.util.HashMap;
import java.util.Map;

public class ExtendedKeyUsageString {
	Map<String, String> map;

	public ExtendedKeyUsageString() {
		
		this.map = new HashMap<String, String>();
		this.map.put("1.3.6.1.5.5.7.3.1", "id_kp_serverAuth");
		this.map.put("1.3.6.1.5.5.7.3.2", "id_kp_clientAuth");
	}
	
	public String getKeyUsageString(String key) {
		return this.map.get(key);
	}

}
