package es.ed0.tinyjson.parser;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONEntity;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;

public class Lexer {

	private class Token {
		
		public static final int text = 1;
		public static final int value = 2;
		public static final int json = 4;


		private int type;
		private String raw;
		private int startPosition;

		public Token(int type, String raw, int startPosition) {
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
						throw new JSONException(previousLength + startPosition, "Invalid value " + raw);
					else if(!config.allowsExponential() && (raw.contains("e") || raw.contains("E")))
							throw new JSONException(previousLength + startPosition, "Exponential is not allowed in the configuration!",
									"You can enable it setting to true ParseConfiguration#setAllowExponential (default: true)");
					else
						return num;
				}
				
			case json:
				return new Lexer(previousLength + startPosition, raw, config).parse();
				
				default:
					throw new JSONException(previousLength + startPosition, "Could not parse value " + raw);
			}
		}
		
	}
	
	
	private final int TOKEN_KEY = C.coma, TOKEN_VALUE = C.colon;
	
	private final int STATUS_STOPPED = -1;
	private final int STATUS_READING = 0;
	private final int STATUS_IN_SCOPE = 1;
	private final int STATUS_IN_STRING = 2;
	private final int STATUS_IN_VALUE = 3;
	private final int STATUS_SEARCHING_DIVISOR = 4;

	private ParseConfiguration config;
	private String json;
	private int previousLength;

	private int pointer = 0, lastTokenPointer = 0;
	private int pointerStatus = STATUS_STOPPED;
	
	private int nextExpectedToken = TOKEN_KEY;
	private int lastNonSpaceChar = C.end;
	

	
	public static Lexer build(String json) {
		return new Lexer(json);
	}

	public static Lexer build(String json, ParseConfiguration config) {
		return new Lexer(json, config);
	}
	
	private Lexer(String json) {
		this(0, json, new ParseConfiguration());
	}
	
	private Lexer(String json, ParseConfiguration config) {
		this(0, json, config);
	}

	private Lexer(int previousLength, String json, ParseConfiguration config) {
		this.previousLength = previousLength;
		this.json = json.trim();
		this.config = config;
	}
	
	
	public JSONEntity parse() throws JSONException {		
		if(json.startsWith("{") && json.endsWith("}"))
			return parseObj();
		else if (json.startsWith("[") && json.endsWith("]"))
			return parseArr();
		else
			throw new JSONException("Object is neither a JSONObject nor a JSONArray!");
	}
	
	public JSONObject parseObj() throws JSONException {
		if(!(json.startsWith("{") && json.endsWith("}")))
			throw new JSONException("JSON String must be encapsulated in braces { }");

		json = json.substring(1, json.length() - 1);
		
		final JSONObject obj = new JSONObject();
		
		while(hasChars()) {
			nextExpectedToken = TOKEN_KEY;
			final Token key = nextToken();
			nextExpectedToken = TOKEN_VALUE;
			
			if(key == null) {
				if(hasChars())
					throw new JSONException(lastTokenPointer , "Invalid key (null)");
				continue;
			}
			
			String keyString = key.getString();
			
			if(config.failOnDuplicateMappings() && obj.containsKey(keyString))
				throw new JSONException(lastTokenPointer, "Duplicated key -> " + keyString,
						"You can disable this error setting ParseConfiguration#setFailOnDuplicateMappings to false.");
			
			final Token value = nextToken();

			if(value == null) 
				throw new JSONException(lastTokenPointer, "Invalid value (null)");
			
			final Object realValue = value.getValue();
			
			if(!(!config.parseNulls() && realValue == null)) {
				System.out.println("adding value for key: " + keyString);
				obj.put(keyString, realValue);
			}
			
		}
		
		// if status is reading then json didnt finished as expected, probably , at the end of body
		checkForHappyEnding();
		
		return obj;
	}
	
	public JSONArray parseArr() throws JSONException {
		if(!(json.startsWith("[") && json.endsWith("]")))
			throw new JSONException("JSON String must be encapsulated in braces [ ]");
		
		json = json.substring(1, json.length() - 1);
		
		final JSONArray obj = new JSONArray();
		
		while(hasChars()) {
			// expected token is always key, so it searches for the ',' character
			nextExpectedToken = TOKEN_KEY;
			final Token value = nextToken();
			
			
			if(value == null) {
				if(hasChars())
					throw new JSONException(lastTokenPointer, "Invalid value (null)");
				continue;
			}

			System.out.println("JSONARRAY-> readed token " + value.raw);
			final Object realValue = value.getValue();
			
			if(!(!config.parseNulls() && realValue == null))
				obj.add(realValue);
			
		}
		
		checkForHappyEnding();
		
		return obj;
	}

	/**
	 * Returns the next token for the current json body. or null if end of body was found
	 * @return
	 * @throws JSONException
	 */
	private Token nextToken() throws JSONException {
		lastTokenPointer = pointer;

		final StringBuffer buffer = new StringBuffer();

		
		int openedScopes = 0;
		char scopeCloser = C.end, scopeOpener = C.end;
		int inEscape = 0;
		
		boolean inString = false;
		
		while (hasChars()) {
			char c = nextChar();
			
			if(c != ' ')
				lastNonSpaceChar = c;
			
			if(!config.allowsUnknownEscapes() && (inEscape == 1) && !isEscape(c))
				throw new JSONException(previousLength + pointer, "Unknown escape found -> \\" + c,
						"You can allow this setting to true ParseConfiguration#setAllowUnknownEscapes (default: true)");

			// if last char was the escape '\' indicator inEscape will be 1
			// So at the iteration 2 the escape character has already been processed, return to 0
			if(inEscape == 1)
				inEscape++;
			else if (inEscape >= 2)
				inEscape = 0;

			if(pointerStatus == STATUS_STOPPED)
				pointerStatus = STATUS_READING;

			System.out.println("->"+ pointer + "  Reading: " + c + " in status: " + pointerStatus);
			
			switch (pointerStatus) {

			// points if the divisor for the next token has been found
			// (: for TOKEN_VALUE or , for TOKEN_KEY)
			case STATUS_SEARCHING_DIVISOR: {
				switch(c) {
				case C.end:
					pointerStatus = STATUS_STOPPED;
					if(nextExpectedToken == TOKEN_VALUE)
						throw new JSONException(previousLength + pointer, "Unexpected end of body");
					else
						return null;
				case ' ':
					break;
					//For colons and comas, if they are the specified divisor, it points it out
					// If other character is found it throws exception
				case C.colon:
				case C.coma:
					if(c == nextExpectedToken) {
						pointerStatus = STATUS_READING;
						break;
					}
					default:
						pointerStatus = STATUS_STOPPED;
						throw new JSONException(previousLength + pointer, "Expected to find " + ((char) nextExpectedToken)
								+ " but found " +  c);
					
				}
				break;
				
			}
			
			case STATUS_READING: {
				switch (c) {
				case C.end:
					pointerStatus = STATUS_STOPPED;
					return null;
				case C.colon:
				case C.coma:
					throw new JSONException(previousLength + pointer, "Invalid Character found: " + c);
				case C.quote:
					pointerStatus = STATUS_IN_STRING;
					scopeOpener = c;
					scopeCloser = getOppositeScopeChar(c);
					break;
				case C.json_open: case C.array_open:
					pointerStatus = STATUS_IN_SCOPE;
					scopeOpener = c;
					scopeCloser = getOppositeScopeChar(c);
					openedScopes++;
					// in json entities we save the body start -> '{' or '['
					buffer.append(c);
					break;
				case ' ':
					break;
				default:
					pointerStatus = STATUS_IN_VALUE;
					buffer.append(c);
					break;

				}
				break;
			}
			
			case STATUS_IN_VALUE: {
				//write whatever gets in the way except spaces
				switch(c) {
				case ' ':
				case C.colon:
				case C.coma:
				case C.array_close:
				case C.json_close:
				case C.end:
					pointerStatus = STATUS_STOPPED;
					//if buffer has some text it could be from a value
					if(buffer.length() > 0)
						return new Token(Token.value, buffer.toString(), pointer - buffer.length());
					else
						throw new JSONException(previousLength + pointer, "Invalid char " + c);
				default:
					buffer.append(c);
					break;
				}
				break;				
			}
			
			case STATUS_IN_STRING: {
				switch (c) {
				case C.end:
					pointerStatus = STATUS_STOPPED;
					throw new JSONException(previousLength + pointer, "Unexpected end of body");
				case C.solidus:
					System.out.println("Entered escape");
					inEscape++;
					buffer.append(c);
					break;
				case C.quote:
					if(inEscape == 0 && c == scopeCloser) {
						pointerStatus = STATUS_SEARCHING_DIVISOR;
						return new Token(Token.text, buffer.toString(), pointer - buffer.length());
					}
				default:
					buffer.append(c);
					break;
				}
				break;
			}
			
			case STATUS_IN_SCOPE: {
				switch(c) {
				case C.end:
					pointerStatus = STATUS_STOPPED;
					throw new JSONException(previousLength + pointer, "Unexpected end of body");
				case C.quote:
					inString = !inString;
					System.out.println("Reading inside a scope, inString -> " + inString);
				default:
					if(!inString) {
						if(c == scopeCloser)
							openedScopes--;
						else if (c == scopeOpener)
							openedScopes++;
					}
					
					buffer.append(c);
					
					if(openedScopes == 0) {
						pointerStatus = STATUS_SEARCHING_DIVISOR;
						return new Token(Token.json, buffer.toString(), pointer - buffer.length());
					}
					break;
				}
			}
			
			
			}

		}
		
		if(buffer.length() < 1)
			return null;
		return new Token(Token.value, buffer.toString(), pointer - buffer.length());
	}
	
	
	/**
	 * Checks for any trouble during the last sections of the body and throws the corresponding exception
	 */
	private void checkForHappyEnding() throws JSONException {
		if(pointerStatus == STATUS_READING) {
			// if status is reading then json didnt finished as expected, probably , at the end of body
			if(lastNonSpaceChar == C.coma && !config.allowsArbitraryCommas()) {
				throw new JSONException(lastTokenPointer, "Found ',' at end of body",
						"Allow this via ParseConfiguration#setAllowArbitraryCommas");
			}
		}
	}
	
	/**
	 * Returns true if thus char corresponds to an escape character, being this the character after the \.<br>
	 * This are: b f n r t \ u "
	 * @param c
	 * @return
	 */
	private boolean isEscape(char c) {
		switch(c) {
		case C.backspace:
		case C.formfeed:
		case C.newline:
		case C.carriage:
		case C.tab:
		case C.solidus:
		case C.unicode:
		case C.quote:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Returns the closing character for the given opening character or viceversa<br>
	 * Example: { returns }. ] returns [. " returns " and so on
	 * @param c opening or closing character
	 * @return {@link C#end} if no match found
	 */
	private char getOppositeScopeChar(char c) {
		switch(c) {
		case C.quote: return C.quote;
		case C.small_quote: return C.small_quote;
		case C.json_open: return C.json_close;
		case C.array_open: return C.array_close;
		case C.json_close: return C.json_open;
		case C.array_close: return C.array_open;
		default: return C.end;
		}
	}
	
	private boolean hasChars() {
		if(pointer >= json.length())
			return false;
		return true;
	}
	
	private char nextChar() {
		// System.out.println("Requesting char at pos " + pointer + " for body " +json+" of length " +json.length() );
		if(hasChars())
			return escapeBackslash(json.charAt(pointer++));
		else
			return (char) C.end;
	}
	
	/**
	 * Changes all blank backslash escapes to the space character to prevent errors
	 * @param c
	 * @return
	 */
	private char escapeBackslash(char c) {
		switch(c) {
		case C.escape_backspace:
		case C.escape_formfeed:
		case C.escape_newline:
		case C.escape_carriage:
		case C.escape_tab:
			return ' ';
		case C.escape_solidus:
		default: return c;
		}
	}
	
	
	
	public ParseConfiguration getSettings() {
		return config;
	}
	
	public void setSettings(ParseConfiguration config) {
		this.config = config;
	}
	

}