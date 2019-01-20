/**
 * Created by Ed0 in 13 dic. 2018
 */
package es.ed0.tinyjson.parser;

public class ParseConfiguration {
	
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
	 * Allow unknown escape characters. The known types are: \b \f \n \r \t \" \\ \u0000* <b>*not atm</b> <br><br>
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
	/**
	 * If false, all keys mapped to null are ignored during the parsing,
	 * making them disappear from the final JSON string representation.<br>
	 * If true null values will be parsed into the JSON object<br>
	 * In both cases calling JSONObject#get will return null,
	 * but functions like JSONObject#containsKey will have a different result<br><br>
	 * Default: true
	 */
	private boolean PARSE_NULLS = true;
	
	/**
	 * If true it will replace escaped characters found in strings (see {@link ALLOW_UNKNOWN_ESCAPES} for known escapes list)<br>
	 * for its unescaped counterpart. I.e: "key1":"whats\\n up" -> "key1":"whats\n up"
	 * Default: true
	 */
	private boolean PARSE_ESCAPED_INTO_UNESCAPED = true;
	

	/**
	 * SETTERS
	 */
	
	/**
	 * {@link ParseConfiguration#ALLOW_ARBITRARY_COMMAS}
	 */
	public void setAllowArbitraryCommas(boolean key) { ALLOW_ARBITRARY_COMMAS = key; }
	/**
	 * {@link ParseConfiguration#FAIL_ON_DUPLICATE_MAPPING}
	 */
	public void setFailOnDuplicateMappings(boolean key) { FAIL_ON_DUPLICATE_MAPPING = key; }
	/**
	 * {@link ParseConfiguration#ALLOW_UNKNOWN_ESCAPES}
	 */
	public void setAllowUnknownEscapes(boolean key) { ALLOW_UNKNOWN_ESCAPES = key; }
	/**
	 * {@link ParseConfiguration#ALLOW_EXPONENTIAL}
	 */
	public void setAllowExponential(boolean key) { ALLOW_EXPONENTIAL = key; }
	/**
	 * {@link ParseConfiguration#ALLOW_UPPER_CASE_VALUES}
	 */
	public void setAllowUpperCaseValues(boolean key) { ALLOW_UPPER_CASE_VALUES = key; }
	/**
	 * {@link ParseConfiguration#PARSE_NULLS}
	 */
	public void setParseNulls(boolean key) { PARSE_NULLS = key; }
	/**
	 * {@link ParseConfiguration#PARSE_ESCAPED_INTO_UNESCAPED}
	 */
	public void setParseEscapedAsUnescaped(boolean key) { PARSE_ESCAPED_INTO_UNESCAPED = key; }
	

	/**
	 * GETTERS
	 */
	
	/**
	 * {@link ParseConfiguration#ALLOW_ARBITRARY_COMMAS}
	 */
	public boolean allowsArbitraryCommas() { return ALLOW_ARBITRARY_COMMAS; }
	/**
	 * {@link ParseConfiguration#FAIL_ON_DUPLICATE_MAPPING}
	 */
	public boolean failOnDuplicateMappings() { return FAIL_ON_DUPLICATE_MAPPING; }
	/**
	 * {@link ParseConfiguration#ALLOW_UNKNOWN_ESCAPES}
	 */
	public boolean allowsUnknownEscapes() { return ALLOW_UNKNOWN_ESCAPES; }
	/**
	 * {@link ParseConfiguration#ALLOW_EXPONENTIAL}
	 */
	public boolean allowsExponential() { return ALLOW_EXPONENTIAL; }
	/**
	 * {@link ParseConfiguration#ALLOW_UPPER_CASE_VALUES}
	 */
	public boolean allowsUpperCaseValues() { return ALLOW_UPPER_CASE_VALUES; }
	/**
	 * {@link ParseConfiguration#PARSE_NULLS}
	 */
	public boolean parseNulls() { return PARSE_NULLS; }
	/**
	 * {@link ParseConfiguration#PARSE_ESCAPED_INTO_UNESCAPED}
	 */
	public boolean parseEscapedAsUnescaped() { return PARSE_ESCAPED_INTO_UNESCAPED; } 
	
	
	
}