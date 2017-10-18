/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

public class JSONValidator {
	
	public static final int OBJECT = 0, ARRAY = 1, ERROR = -1; 
	
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
