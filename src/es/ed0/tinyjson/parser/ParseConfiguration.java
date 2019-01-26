/**
 * Created by Ed0 in 13 dic. 2018
 */
package es.ed0.tinyjson.parser;

/**
 * Configuration object for JSONParser. Here some parsing setting and parameters can be set to allow a more custom parsing of jsons
 */
public class ParseConfiguration {
	
	private boolean ALLOW_ARBITRARY_COMMAS = false;
	private boolean FAIL_ON_DUPLICATE_MAPPING = false;
	private boolean ALLOW_UNKNOWN_ESCAPES = true;
	private boolean ALLOW_EXPONENTIAL = true;
	private boolean ALLOW_UPPER_CASE_VALUES = true;
	private boolean PARSE_NULLS = true;
	private boolean PARSE_ESCAPED_INTO_UNESCAPED = true;
	
	/**
	 * Allow random commas at the end of json bodies. Example: ["value", 3, true,]<br><br>
	 * Default: false
	 */
	public void setAllowArbitraryCommas(boolean key) { ALLOW_ARBITRARY_COMMAS = key; }
	/**
	 * Parser will throw exception if a duplicate mapping was found<br><br>
	 * Default: false
	 */
	public void setFailOnDuplicateMappings(boolean key) { FAIL_ON_DUPLICATE_MAPPING = key; }
	/**
	 * Allow unknown escape characters. The known types are: \b \f \n \r \t \" \\ \u0000* <b>*not atm</b> <br><br>
	 * Default: true
	 */
	public void setAllowUnknownEscapes(boolean key) { ALLOW_UNKNOWN_ESCAPES = key; }
	/**
	 * Allow exponential in numeric values. Example: 12E+1, 12e+1, 12e-1, 12e1, etc<br><br>
	 * Default: true
	 */
	public void setAllowExponential(boolean key) { ALLOW_EXPONENTIAL = key; }
	/**
	 * Allow boolean or null values to be upper-case. Example: FALSE, NULL, TrUe<br>
	 * Note that this will remove case-sensitivity allowing values with mixed caps.<br>
	 * This will have no affect to the exponential character (E / e) which will allways be case-insensitive<br><br>
	 * Default: true
	 */
	public void setAllowUpperCaseValues(boolean key) { ALLOW_UPPER_CASE_VALUES = key; }
	/**
	 * If false, all keys mapped to null are ignored during the parsing,
	 * making them disappear from the final JSON string representation.<br>
	 * If true null values will be parsed into the JSON object<br>
	 * In both cases calling {@link es.ed0.tinyjson.JSONEntity#get(Object)} with a key that maps to a null value will return null,
	 * but functions like JSONObject#containsKey will have a different result<br><br>
	 * Default: true
	 */
	public void setParseNulls(boolean key) { PARSE_NULLS = key; }
	
	/**
	 * If true it will replace escaped characters found in strings 
	 * (see {@link es.ed0.tinyjson.parser.ParseConfiguration#setAllowUnknownEscapes(boolean)} for known escapes list)<br>
	 * for its unescaped counterpart. I.e: "key1":"whats\\n up" to "key1":"whats\n up"<br><br>
	 * Default: true
	 */
	public void setParseEscapedAsUnescaped(boolean key) { PARSE_ESCAPED_INTO_UNESCAPED = key; }
	
	
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setAllowArbitraryCommas(boolean)
	 */
	public boolean allowsArbitraryCommas() { return ALLOW_ARBITRARY_COMMAS; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setFailOnDuplicateMappings(boolean)
	 */
	public boolean failOnDuplicateMappings() { return FAIL_ON_DUPLICATE_MAPPING; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setAllowUnknownEscapes(boolean)
	 */
	public boolean allowsUnknownEscapes() { return ALLOW_UNKNOWN_ESCAPES; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setAllowExponential(boolean)
	 */
	public boolean allowsExponential() { return ALLOW_EXPONENTIAL; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setAllowUpperCaseValues(boolean)
	 */
	public boolean allowsUpperCaseValues() { return ALLOW_UPPER_CASE_VALUES; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setParseNulls(boolean)
	 */
	public boolean parseNulls() { return PARSE_NULLS; }
	/**
	 * @see es.ed0.tinyjson.parser.ParseConfiguration#setParseEscapedAsUnescaped(boolean)
	 */
	public boolean parseEscapedAsUnescaped() { return PARSE_ESCAPED_INTO_UNESCAPED; } 
	
	
	
}