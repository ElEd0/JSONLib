/**
 * Created by Ed0 in 13 dic. 2018
 */
package es.ed0.jsonlib;

public class ParseConfiguration {
	
	private boolean ALLOW_NON_QUOTED_KEYS = false;
	private boolean ALLOW_ARBITRARY_COMMAS = false;
	private boolean FAIL_ON_DUPLICATE_MAPPING = false;
	
	public void setAllowNonQuotedKeys(boolean key) {
		ALLOW_NON_QUOTED_KEYS = key;
	}

	public void setAllowArbitraryCommas(boolean key) {
		ALLOW_ARBITRARY_COMMAS = key;
	}

	public void setFailOnDuplicateMappings(boolean key) {
		FAIL_ON_DUPLICATE_MAPPING = key;
	}
	
	public boolean allowsNonQuotedKeys() {
		return ALLOW_NON_QUOTED_KEYS;
	}
	
	public boolean allowsArbitraryCommas() {
		return ALLOW_ARBITRARY_COMMAS;
	}
	
	public boolean failOnDuplicateMappings() {
		return FAIL_ON_DUPLICATE_MAPPING;
	}

}