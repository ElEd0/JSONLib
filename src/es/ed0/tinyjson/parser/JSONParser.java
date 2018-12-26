/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson.parser;

import java.util.List;
import java.util.Map;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONEntity;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;


public class JSONParser {	
	
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
		return Lexer.build(raw).parseObj();
	}

	public static JSONObject parseJSONObject(String raw, ParseConfiguration config) throws JSONException {
		return Lexer.build(raw, config).parseObj();
	}

	public static JSONArray parseJSONArray(String raw) throws JSONException {
		return Lexer.build(raw).parseArr();
	}
	public static JSONArray parseJSONArray(String raw, ParseConfiguration config) throws JSONException {
		return Lexer.build(raw, config).parseArr();
	}
	
	/**
	 * Returns the value to write in a json for the given Object, Note this will add any necessary quotes
	 * @param obj
	 * @return
	 */
	public static String getJsonStringValueForObject(Object obj) {
		if(obj == null)
			return "null";
		else if(obj instanceof Boolean || obj instanceof Integer ||
				obj instanceof Float || obj instanceof Double ||
				obj instanceof Long || obj instanceof JSONEntity)
			return obj.toString();
		else
			return "\"" + obj.toString() + "\"";
	}
	
	
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
