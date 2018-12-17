/**
 * Created by Ed0 in 13 dic. 2018
 */
package es.ed0.jsonlib;

public class ParseConfiguration {
	
	/**
	 * Should non-quoted keys be allowed (not recomended)
	 */
	private boolean ALLOW_NON_QUOTED_KEYS = false;
	/**
	 * Allow random commas at the end of json bodies. Example: ["value", 3, true,]
	 */
	private boolean ALLOW_ARBITRARY_COMMAS = false;
	/**
	 * Parser will throw exception if a duplicate mapping was found
	 */
	private boolean FAIL_ON_DUPLICATE_MAPPING = false;
	/**
	 * Allow unknown escape characters. The known types are: \b \f \n \r \t \" \\ \u0000 
	 */
	private boolean ALLOW_UNKNOWN_ESCAPES = true;
	/**
	 * Allow exponential in numeric values. Example: 12E+1, 12e+1, 12e-1, 12e1, etc
	 */
	private boolean ALLOW_EXPONENTIAL = true;
	
	public void setAllowNonQuotedKeys(boolean key) {
		ALLOW_NON_QUOTED_KEYS = key;
	}

	public void setAllowArbitraryCommas(boolean key) {
		ALLOW_ARBITRARY_COMMAS = key;
	}

	public void setFailOnDuplicateMappings(boolean key) {
		FAIL_ON_DUPLICATE_MAPPING = key;
	}
	
	public void setAllowUnknownEscapes(boolean key) {
		ALLOW_UNKNOWN_ESCAPES = key;
	}
	
	public void setAllowExponential(boolean key) {
		ALLOW_EXPONENTIAL = key;
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

	public boolean allowsUnknownEscapes() {
		return ALLOW_UNKNOWN_ESCAPES;
	}
	
	public boolean allowsExponential() {
		return ALLOW_EXPONENTIAL;
	}
	
	
}