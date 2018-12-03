/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;
import java.util.HashMap;


public class JSONValidator {
	
	public static final int OBJECT = 0, ARRAY = 1, ERROR = -1; 
	
	
	
	public static HashMap<String, Object> validateAndMap(String raw) throws JSONException {

		final HashMap<String, Object> mappings = new HashMap<>();
		
		final ArrayList<String> strings = new ArrayList<>();
		
		//detect if wrong head + tail
		if(!(raw.startsWith("{") && raw.endsWith("}")))
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
			
			if((c == ' ') && !(inScope && getEndingTokenForScope(openingChar) == '"'))
				continue;
			
			
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
				output = output.replace(buffer.toString(), ";s" + (strings.size() - 1));
				buffer.setLength(0);
				inScope = false;
				openingChar = 0;
			}
			
			
			
		} /* end for */
		
		output = output.replace(" ", "")
				.replace("\n", "")
				.replace("\r", "")
				.replace("\r\n", "");
		
		//System.out.println(output);

		String[] entries = output.split(",");
		for(String entry : entries) {
			String[] kandv = entry.split(":");
			if(kandv.length<2)
				return null;
			
			//change string and jsonObjects mappings to original values
			for(int i=0; i<2; i++) 
				if(kandv[i].startsWith(";s"))
					kandv[i] = strings.get(Integer.valueOf(kandv[i].replace(";s", "")));
			
			kandv[0] = getValueFromString(kandv[0]);
			
			if(kandv[1].startsWith("{"))
				mappings.put(kandv[0], new JSONObject(kandv[1]));
			else if(kandv[1].startsWith("["))
				mappings.put(kandv[0], new JSONArray(kandv[1]));
			else
				mappings.put(kandv[0], getValueFromString(kandv[1]));
		}
		
		return mappings;
	}
	
	
	public static ArrayList<Object> validateAndList(String raw) {		
		
		
		return null;
	}

	
	private static String getValueFromString(String seq) {
		if(seq.startsWith("\"") && seq.endsWith("\""))
			return seq.substring(seq.indexOf("\"") + 1, seq.length() - 1);
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
			return JSONValidator.OBJECT;
		else if (raw.startsWith("["))
			return JSONValidator.ARRAY;
		return JSONValidator.ERROR;
	}
	
	private static boolean checkAsterisks(String ch) {
		return ch.startsWith("\"") && ch.endsWith("\"");
	}

}
