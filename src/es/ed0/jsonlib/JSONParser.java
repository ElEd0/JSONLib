/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.List;
import java.util.Map;


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
	
	
}
