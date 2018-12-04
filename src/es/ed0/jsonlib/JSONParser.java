/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class JSONParser {
	
	public static final int OBJECT = 0, ARRAY = 1, ERROR = -1; 
	
	
	
	public abstract static class Configuration {
		
		
		/** True will remove the quotes from non-string types<br>
		 * "key1":"12","key2":"true","key3":"hello"<br>
		 * would result in<br>
		 * "key1":12,"key2":true,"key3":"hello" respectively<br> 
		 */
		public final static boolean REMOVE_QUOTES_FROM_VALUES = false;
	}
	
	public static JSONObject parseJSONObjectFromString(String raw) throws JSONException {
		if(!(raw.startsWith("{") && raw.endsWith("}")))
			throw new JSONException("JSON String must be encapsulated in braces { }");
		final JSONEntity e = parseString(raw);
		if(e instanceof JSONObject)
			return (JSONObject) e;
		throw new JSONException("JSON Could not be parsed: Unknown reasons");
	}

	public static JSONArray parseJSONArrayFromString(String raw) throws JSONException {
		if(!(raw.startsWith("[") && raw.endsWith("]")))
			throw new JSONException("JSON String must be encapsulated in braces [ ]");
		JSONEntity e = parseString(raw);
		if(e instanceof JSONArray)
			return (JSONArray) e;
		throw new JSONException("JSON Could not be parsed: Unknown reasons");
	}
	
	
	
	private static JSONEntity parseString(String raw) throws JSONException {
		final ArrayList<String> strings = new ArrayList<>();
		
		boolean isArray = false;
		
		if(raw.startsWith("{") && raw.endsWith("}"))
			isArray = false;
		else if(raw.startsWith("[") && raw.endsWith("]"))
			isArray = true;
		else
			return null;
		

		//remove head + tail
		raw = raw.substring(1, raw.length()-1).trim();

		char openingChar = 0;
		boolean inScope = false;
		StringBuilder buffer = new StringBuilder();
		int openedScopes = 0;
		
		
		String output = raw;
		
		// iter over characters and map string into an array so they dont interfere
		for(int i=0; i<raw.length(); i++) {
			char c = raw.charAt(i);
			
			//TODO: white space removal 4 optimization
			//if((c == ' ') && !(inScope && getEndingTokenForScope(openingChar) == '"')) continue;
			
			
			//if char is an opening scope and not already inside a scope select opening char
			if(!inScope && (c == '{' || c == '[' || c == '"')) {
				openingChar = c;
				inScope = true;
				
				System.out.println("opening scope with char "+c+" at pos "+i);
				
			}
			

			if(c == openingChar) {
				//if inside a string scope and " char found again close it
				if(inScope && openingChar == '"' && openedScopes > 0)
					openedScopes--;
				else
					openedScopes++;
			} else if (c == getEndingTokenForScope(openingChar)) {
				openedScopes--;
			}
			
			if(inScope)
				buffer.append(c);
			
			if(inScope && openedScopes == 0) {
				// validate scope and clear buffer
				
				System.out.println("saving string: "+buffer.toString()+" to ;s"+strings.size()+" at pos "+i);
				
				strings.add(buffer.toString());
				output = replaceFirst(output, buffer.toString(), ";s" + (strings.size() - 1));
				buffer.setLength(0);
				inScope = false;
				openingChar = 0;
				
				System.out.println("Output is now: "+output+"\n");
			}			
			
		} /* end for */
		
		output = output.replace(" ", "")
				.replace("\n", "")
				.replace("\r", "")
				.replace("\r\n", "");
		
		
		System.out.println("\nOutput: "+output+"\n\n");
		
		
		JSONEntity result = null;
		if(isArray)
			result = new JSONArray();
		else
			result = new JSONObject();

		String[] entries = output.split(",");
		for(String entry : entries) {
			String[] kandv = entry.split(":");
			
			if((isArray && kandv.length != 1) ||
					!isArray && kandv.length != 2)
				return null;
			
			//change string and jsonObjects mappings to original values
			for(int i=0; i<kandv.length; i++) 
				if(kandv[i].startsWith(";s"))
					try {
						kandv[i] = strings.get(Integer.valueOf(kandv[i].replace(";s", "")));
					} catch (NumberFormatException e) {
						throw new JSONException("Error formatting string");
					}
			
			String value = null;
			if(isArray) {
				value = kandv[0];
			} else {
				//if is jsonobject remove quotes from key
				kandv[0] = getValueFromString(kandv[0]);
				value = kandv[1];
			}
			
			//System.out.println("adding value: "+kandv[1]);
			
			Object toAdd = null;
			
			if(value.startsWith("{"))
				toAdd = parseJSONObjectFromString(value);
			else if(value.startsWith("["))
				toAdd = parseJSONArrayFromString(value);
			else
				toAdd = (Configuration.REMOVE_QUOTES_FROM_VALUES ?
						getValueFromString(value) : value);
			
			if(isArray)
				((JSONArray) result).add(toAdd);
			else
				((JSONObject) result).add(kandv[0], toAdd);
			
		}
		
		return result;
	}
	
	/**
	 * Utility method for replacing the first occurance of <code>search</code> within <code>string</code> with <code>replaceWith</code>
	 * in a given String object
	 * @param string The String to search on
	 * @param search Target
	 * @param replaceWith Replacement
	 */
	public static String replaceFirst(String string, String search, String replaceWith) {
		final int index = string.indexOf(search) + search.length();
		return string.substring(0, index).replace(search, replaceWith)
				+ string.substring(index, string.length());
	}
	
	/**
	 * Removes the quotes if any from the value
	 * @param seq
	 * @return
	 */
	public static String getValueFromString(String seq) {
		if(seq.startsWith("\"") && seq.endsWith("\""))
			return seq.substring(seq.indexOf("\"") + 1, seq.length() - 1);
		return seq;
	}

	/**
	 * Removes the quotes if any from the value, 
	 * unless removeNonStringQuotes specified false and seq is a numerical or boolean value
	 * in which case it will return the quoted value
	 * @param seq
	 * @return
	 */
	public static String escapeQuotes(String seq, boolean removeNonStringQuotes) {
		if(seq.startsWith("\"") && seq.endsWith("\"")) {
			final String parsed = seq.substring(seq.indexOf("\"") + 1, seq.length() - 1);
			if(!removeNonStringQuotes) {
				try {
					Double.parseDouble(parsed);
				} catch (NumberFormatException e) {
					try {
						Integer.parseInt(parsed);
					} catch (NumberFormatException e2) {
						if(!(parsed.equals("true") || parsed.equals("false")))
							return parsed;
					}
				}
				
				return seq;
			}
			return parsed;
		}
		return seq;
	}
	
	public static char getEndingTokenForScope(char c) {
		switch(c) {
			case '{': return '}';
			case '[': return ']';
			case '"': return '"';
			case '\'': return '\'';
			default: return 0;
		}
		
	}
	
	
	public static boolean validateJSON(String raw) {
		if(!(raw.startsWith("{") && raw.endsWith("}")))
			return false;
		String raw1 = raw.substring(1, raw.length()-1);
		String[] entries = raw1.split(",");
		for(String entry : entries) {
			String[] kandv = entry.split(":");
			if(kandv.length<2)
				return false;
			if(!(checkAsterisks(kandv[0]) && checkAsterisks(kandv[1])))
				return false;
		}
		
		return true;
	}
	
	
	public static boolean validateArray(String raw) {
		if(!(raw.startsWith("[") && raw.endsWith("]")))
			return false;
		String raw1 = raw.substring(1, raw.length()-1);
		String[] entries = raw1.split(";");
		for(String entry : entries) {
			if(!validateJSON(entry))
				return false;
		}
		return true;
	}
	
	/**
	 * Return type of object (jsonObject or jsonArray) as int</br>
	 * Constants:</br>
	 * JSONValidator.OBJECT, JSONValidator.ARRAY, JSONValidator.ERROR 
	 * @param raw
	 * @return
	 */
	public static int jsonType(String raw) {
		if(raw.startsWith("{"))
			return JSONParser.OBJECT;
		else if (raw.startsWith("["))
			return JSONParser.ARRAY;
		return JSONParser.ERROR;
	}
	
	private static boolean checkAsterisks(String ch) {
		return ch.startsWith("\"") && ch.endsWith("\"");
	}

}
