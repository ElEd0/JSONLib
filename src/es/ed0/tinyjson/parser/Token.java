/**
 * Created by Ed0 in 26 dic. 2018
 */
package es.ed0.tinyjson.parser;

import es.ed0.tinyjson.JSONException;

public class Token {

	public static final int text = 1;
	public static final int value = 2;
	public static final int json = 4;


	private ParseConfiguration config;
	private int type;
	private String raw;
	private int startPosition;

	public Token(ParseConfiguration config, int type, String raw, int startPosition) {
		this.config = config;
		this.type = type;
		this.raw = raw;
		this.startPosition = startPosition;
	}
	
	public String toString() {
		return raw;
	}
	
	
	public String getString() throws JSONException {
		if(type != text)
			throw new JSONException("Invalid key " + raw);
		return raw;
	}
	
	
	public Object getValue() throws JSONException {
		switch(this.type) {
		
		case text: return raw;
		
		case value:
			if(config.allowsUpperCaseValues())
				raw = raw.toLowerCase();
			if(raw.equals("null"))// is null
				return null;
			else if (raw.equals("true") || raw.equals("false"))// is boolean
				return Boolean.valueOf(raw);
			else {
				
				// check if value is number, if it is not, no more options left bad boy
				final Number num = JSONParser.parseNumber(raw);
				if(num == null)
					throw new JSONException(startPosition, "Invalid value " + raw);
				else if(!config.allowsExponential() && (raw.contains("e") || raw.contains("E")))
						throw new JSONException(startPosition, "Exponential is not allowed in the configuration!",
								"You can enable it setting to true ParseConfiguration#setAllowExponential (default: true)");
				else
					return num;
			}
			
		case json:
			return new Lexer(startPosition, raw, config).parse();
			
			default:
				throw new JSONException(startPosition, "Could not parse value " + raw);
		}
	}
	
}
