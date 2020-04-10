package ftn.bsep.pkiapp.util;

public enum ExtensionASNIdentifiers {

	// ok ovo je definitivno bespotrebno jer moze OID da se cita
	// iz Extension klase : Extension.authorityInfoAccess..........
	AUTHORITY_INFO_ACCESS("1.3.6.1.5.5.7.1.1"),
	AUTHORITY_KEY_IDENTIFIER("2.5.29.35"),
	BASIC_CONSTRAINTS("2.5.29.19"),
	CERTIFICATE_POLICIES_EXT("2.5.29.32"),
	CRL_DISTRIBUTION_POINTS("2.5.29.31"),
	EXT_KEY_USAGE("2.5.29.37"),
	ISSUER_ALT_NAME("2.5.29.18"),
	KEY_USAGE("2.5.29.15"),
	NAME_CONSTRAINT("2.5.29.30"),
	OCSP_NOCHECK("1.3.6.1.5.5.7.48.4"),
	POLICY_CONSTRAINTS("2.5.29.36"),
	POLOCY_MAPPINGS("2.5.29.33"),
	PRIVATE_KEY_USAGE_PERIOD("2.5.29.16"),
	SUBJECT_ALT_NAME("2.5.29.17"),
	SUBJECT_DIRECTORY_ATTRIBUTES("2.5.29.9"),
	SUBJECT_KEY_IDENTIFIER("2.5.29.14");
	
	private String oid;
	
	ExtensionASNIdentifiers(String oidVal) {
		this.oid = oidVal;
	}
	
	public String getOid() {
		return oid;
	}
	
}
