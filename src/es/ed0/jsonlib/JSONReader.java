package es.ed0.jsonlib;

public class JSONReader {

	private static class Token {

		public static final int error = (char) -1;
		public static final int colon = ':';
		public static final int value = 1;
		public static final int coma = ',';
		public static final int json_open = '{';
		public static final int json_close = '}';
		public static final int array_open = '[';
		public static final int array_close = ']';
		public static final int quote = '"';
		public static final int small_quote = '\'';

		private int type;
		private String text;

		public Token(int type) {
			this.type = type;
			this.text = String.valueOf((char) type);
		}

		public Token(int type, String text) {
			this.type = type;
			this.text = text;
		}

	}
	
	
	private final int STATUS_STOPPED = -1;
	private final int STATUS_READING = 0;
	private final int STATUS_IN_SCOPE = 1;
	private final int STATUS_IN_STRING = 2;

	private String json;

	private int pointer = 0;
	private int pointerStatus = STATUS_STOPPED;

	public JSONReader(String json) {
		this.json = json;

	}

	private Token nextToken() {
		boolean tokenEnd = false;

		final StringBuffer buffer = new StringBuffer();

		if(pointerStatus == STATUS_STOPPED)
			pointerStatus = STATUS_READING;
		
		int openedScopes = 0;
		char scopeCloser = Token.error;
		
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
				case Token.quote: case Token.small_quote:
					pointerStatus = STATUS_IN_STRING;
					scopeCloser = getScopeCloserForScopeOpener(c);
					break;
				case Token.json_open: case Token.array_open:
					pointerStatus = STATUS_IN_SCOPE;
					scopeCloser = getScopeCloserForScopeOpener(c);
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
				
				break;
			}
			
			}
			


		}

		return null;
	}
	
	
	private char getScopeCloserForScopeOpener(char c) {
		switch(c) {
		case Token.quote: return Token.quote;
		case Token.small_quote: return Token.small_quote;
		case Token.json_open: return Token.json_close;
		case Token.array_open: return Token.array_close;
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

}
