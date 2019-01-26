/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONEntity;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;

/**
 * Utility class for parsing JSONObject and JSONArray from string or files
 */
public abstract class JSONParser {
	
	public static JSONObject parseJSONObject(Map<String, ? extends Object> mappings) {
		final JSONObject o = new JSONObject();
		o.putAll(mappings);
		return o;
	}
	
	public static JSONArray parseJSONArray(List<? extends Object> list) {
		final JSONArray a = new JSONArray();
		a.addAll(list);
		return a;
	}
	
	public static JSONObject parseJSONObject(String raw) throws JSONException {
		return new Lexer(raw).parseObj();
	}

	public static JSONObject parseJSONObject(String raw, ParseConfiguration config) throws JSONException {
		return new Lexer(raw, config).parseObj();
	}

	public static JSONArray parseJSONArray(String raw) throws JSONException {
		return new Lexer(raw).parseArr();
	}
	public static JSONArray parseJSONArray(String raw, ParseConfiguration config) throws JSONException {
		return new Lexer(raw, config).parseArr();
	}
	
	public static JSONObject parseJSONObjectFromFile(String filePath) throws JSONException {
		return parseJSONObject(readFile(filePath));
	}

	public static JSONArray parseJSONArrayFromFile(String filePath) throws JSONException {
		return parseJSONArray(readFile(filePath));
	}
	
	private static String readFile(String path) throws JSONException {
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(new File(path));
			int c = 0;
			while((c = fr.read()) != -1)
				sb.append((char) c);
			fr.close();
		} catch (IOException e) {
			throw new JSONException(e);
		}
		return sb.toString();
	}
	/**
	 * Returns the value to write in a json for the given Object, Note this will add any necessary quotes
	 * @param obj Object to convert to safe String
	 * @return the String representation
	 */
	public static String getJsonStringValueForObject(Object obj) {
		if(obj == null)
			return "null";
		else if(obj instanceof Boolean || obj instanceof Integer ||
				obj instanceof Float || obj instanceof Double ||
				obj instanceof Long || obj instanceof JSONEntity)
			return obj.toString();
		else
			return "\"" + escape(obj.toString()) + "\"";
	}

	/**
	 * Unescapes detected escapes into its counterpart<br>
	 * I.e: "this is an\\n safe\" str\"ing blabla" to "this is an\n unsafe" str"ing blabla"
	 * @param escaped escaped String
	 * @return unescaped
	 */
	public static String unescape(String escaped) {
		return escaped
				.replace("\\" + ((char) C.quote), "" + (char) C.quote)
				.replace("\\" + ((char) C.backspace), "" + (char) C.escape_backspace)
				.replace("\\" + ((char) C.carriage), "" + (char) C.escape_carriage)
				.replace("\\" + ((char) C.formfeed), "" + (char) C.escape_formfeed)
				.replace("\\" + ((char) C.newline), "" + (char) C.escape_newline)
				.replace("\\" + ((char) C.tab), "" + (char) C.escape_tab)
				.replace("\\" + ((char) C.solidus), "" + (char) C.escape_solidus);
	}

	/**
	 * Escapes a value into a safe for sending and parsing string.<br>
	 * I.e: "this is an\n unsafe" str"ing blabla" to "this is an\\n unsafe\" str\"ing blabla"
	 * @param unescaped unescaped String
	 * @return escaped
	 */
	public static String escape(String unescaped) {
		return unescaped
				.replace("" + ((char) C.escape_solidus), "\\" + (char) C.solidus)
				.replace("" + ((char) C.quote), "\\" + (char) C.quote)
				.replace("" + ((char) C.escape_backspace), "\\" + (char) C.backspace)
				.replace("" + ((char) C.escape_carriage), "\\" + (char) C.carriage)
				.replace("" + ((char) C.escape_formfeed), "\\" + (char) C.formfeed)
				.replace("" + ((char) C.escape_newline), "\\" + (char) C.newline)
				.replace("" + ((char) C.escape_tab), "\\" + (char) C.tab);
	}
	
	/**
	 * Returns the given raw value in its number form or null if the raw is an invalid number
	 * @param raw raw number as string
	 * @return Integer Long or Double representation of the string
	 */
	public static Number parseNumber(String raw) {
		try { // is number
			return Integer.parseInt(raw);
		} catch (NumberFormatException e) {
			try {
				return Long.parseLong(raw);
			} catch (NumberFormatException e2) {
				try {
					return Double.parseDouble(raw);
				} catch (NumberFormatException e3) {
					return null;
				}
			}
		}
	}
	
}
