package es.ed0.jsonlib;

public class JSONReader {

	private class Token {
		
		public static final int text = 1;
		public static final int value = 2;
		public static final int json = 4;
		public static final int array = 5;

		public static final int error = (char) -1;
		public static final int colon = ':';
		public static final int coma = ',';
		public static final int json_open = '{';
		public static final int json_close = '}';
		public static final int array_open = '[';
		public static final int array_close = ']';
		public static final int quote = '"';
		public static final int small_quote = '\'';

		private int type;
		private String raw;

		public Token(int type) {
			this.type = type;
			this.raw = "null";
		}

		public Token(int type, String raw) {
			this.type = type;
			this.raw = raw;
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
				else
					try { // is number
						return Integer.parseInt(raw);
					} catch (NumberFormatException e) {
						try {
							return Long.parseLong(raw);
						} catch (NumberFormatException e2) {
							try {
								return Double.parseDouble(raw);
							} catch (NumberFormatException e3) {
								throw generateJSONException("Invalid value " + raw
										+ ". Value case sensitivity can be changed in ParseConfiguration#setAllowUpperCaseValues");
							}
						}
					}
				
			case json:
				return JSONParser.parseJSONObject(raw);
				
			case array:
				return JSONParser.parseJSONArray(raw);
				
				default:
					throw generateJSONException("Could not parse value " + raw);
			}
		}
		
	}
	
	
	
	private final int STATUS_STOPPED = -1;
	private final int STATUS_READING = 0;
	private final int STATUS_IN_SCOPE = 1;
	private final int STATUS_IN_STRING = 2;
	private final int STATUS_IN_VALUE = 3;

	private ParseConfiguration config;
	private String json;

	private int pointer = 0, lastTokenPointer = 0;
	private int pointerStatus = STATUS_STOPPED;

	public JSONReader(String json) {
		this(json, new ParseConfiguration());
	}
	
	public JSONReader(String json, ParseConfiguration config) {
		this.json = json;
		this.config = config;
	}
	
	
	
	public JSONEntity parse() throws JSONException {
		
		if(json.startsWith("{"))
			return parseObj();
		else if (json.startsWith("["))
			return parseArr();
		else
			throw new JSONException("Object is neither a JSONObject nor a JSONArray!");
	}
	
	private JSONObject parseObj() {
		final JSONObject obj = new JSONObject();
		
		while(hasChars()) {
			final Token key = nextToken(),
					value = nextToken();
			
			
			
			
			
			
		}
		
		
		return obj;
	}
	
	private JSONArray parseArr() {
		return null;
	}

	private Token nextToken() {
		lastTokenPointer = pointer;
		
		boolean tokenEnd = false;

		final StringBuffer buffer = new StringBuffer();

		if(pointerStatus == STATUS_STOPPED)
			pointerStatus = STATUS_READING;
		
		int openedScopes = 0;
		char scopeCloser = Token.error, scopeOpener = Token.error;
		
		while (!tokenEnd) {
			char c = nextChar();
			
			
			switch (pointerStatus) {
			
			case STATUS_READING: {
				switch (c) {
				case Token.error:
				case Token.colon:
				case Token.coma:
					pointerStatus = STATUS_STOPPED;
					return new Token(c);
				case Token.quote:
					pointerStatus = STATUS_IN_STRING;
					scopeCloser = getOppositeScopeChar(c);
					break;
				case Token.json_open: case Token.array_open:
					pointerStatus = STATUS_IN_SCOPE;
					scopeOpener = c;
					scopeCloser = getOppositeScopeChar(c);
					openedScopes++;
					buffer.append(c);
					break;
				case ' ':
					break;
				default:
					buffer.append(c);
					break;

				}
			}
			
			case STATUS_IN_STRING: {
				switch (c) {
				case Token.quote: case Token.small_quote:
					if(c == scopeCloser)
						pointerStatus = STATUS_READING;
					return new Token(Token.value, buffer.toString());
				default:
					buffer.append(c);
					break;
				}
			}
			
			case STATUS_IN_SCOPE: {
				if(c == scopeCloser)
					openedScopes--;
				else if (c == scopeOpener)
					openedScopes++;
				
				buffer.append(c);
				
				if(openedScopes == 0) {
					pointerStatus = STATUS_STOPPED;
					return new Token(Token.value, buffer.toString());
				}
				break;
			}
			
			}
			


		}

		return null;
	}
	
	
	private JSONException generateJSONException(String msg) {
		return new JSONException("Error parsing at position " + lastTokenPointer + ": " + msg);
	}
	
	private char getOppositeScopeChar(char c) {
		switch(c) {
		case Token.quote: return Token.quote;
		case Token.small_quote: return Token.small_quote;
		case Token.json_open: return Token.json_close;
		case Token.array_open: return Token.array_close;
		case Token.json_close: return Token.json_open;
		case Token.array_close: return Token.array_open;
		default: return Token.error;
		}
	}
	
	private boolean hasChars() {
		if(pointer >= json.length())
			return false;
		return true;
	}
	
	private char nextChar() {
		if(hasChars())
			return json.charAt(pointer++);
		else
			return (char) Token.error;
	}
	
	public ParseConfiguration getSettings() {
		return config;
	}

}
