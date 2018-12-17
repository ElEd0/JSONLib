/**
 * Created by Ed0 in 13 dic. 2018
 */
package es.ed0.jsonlib;

public class ParseConfiguration {
	
	/**
	 * Should non-quoted keys be allowed (not recomended)<br><br>
	 * Default: false
	 */
	private boolean ALLOW_NON_QUOTED_KEYS = false;
	/**
	 * Allow random commas at the end of json bodies. Example: ["value", 3, true,]<br><br>
	 * Default: false
	 */
	private boolean ALLOW_ARBITRARY_COMMAS = false;
	/**
	 * Parser will throw exception if a duplicate mapping was found<br><br>
	 * Default: false
	 */
	private boolean FAIL_ON_DUPLICATE_MAPPING = false;
	/**
	 * Allow unknown escape characters. The known types are: \b \f \n \r \t \" \\ \u0000 <br><br>
	 * Default: true
	 */
	private boolean ALLOW_UNKNOWN_ESCAPES = true;
	/**
	 * Allow exponential in numeric values. Example: 12E+1, 12e+1, 12e-1, 12e1, etc<br><br>
	 * Default: true
	 */
	private boolean ALLOW_EXPONENTIAL = true;
	/**
	 * Allow boolean or null values to be upper-case. Example: FALSE, NULL, TrUe<br>
	 * Note that this will remove case-sensitivity allowing values with mixed caps.<br>
	 * This will have no affect to the exponential character (E / e) which will allways be case-insensitive<br><br>
	 * Default: true
	 */
	private boolean ALLOW_UPPER_CASE_VALUES = true;
	
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

	public void setAllowUpperCaseValues(boolean key) {
		ALLOW_UPPER_CASE_VALUES = key;
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

	public boolean allowsUpperCaseValues() {
		return ALLOW_UPPER_CASE_VALUES;
	}
	
}