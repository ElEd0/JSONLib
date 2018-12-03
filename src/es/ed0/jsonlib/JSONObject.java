/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.HashMap;
import java.util.Map.Entry;

public class JSONObject {
		
	
	private HashMap<String, Object> mappings;

	/**
	 * Creates a new empty JSON
	 */
	public JSONObject() {
		mappings = new HashMap<String, Object>();
	}

	/**
	 * Generates a new JSON from the raw string given
	 * @param Raw JSON string
	 * @throws JSONException if the given string is not a valid raw JSON string
	 */
	public JSONObject(String json) throws JSONException {
		mappings = JSONValidator.validateAndMap(json);
		if(mappings == null)
			throw new JSONException("JSON string not valid!");
		
	}
	
	
	
	/**
	 * Adds the given object identified by the given key to this JSON
	 * If the given key is already mapped it will change the value
	 * @param key
	 * @param object
	 */
	public void put(String key, Object obj) {
		mappings.put(key, obj);
	}

	/**
	 * Retrieves the Object represented by the given key
	 * @param key
	 * @return Object represented by key, null if key is not mapped
	 */
	public Object get(String key) {
		return mappings.get(key);
	}
	
	/**
	 * Retrieves the String represented by the given key
	 * @param key
	 * @return Object represented by key, null if key is not mapped
	 */
	public String getString(String key) {
		return get(key).toString();
	}

	/**
	 * Retrieves the Integer represented by the given key
	 * @param key
	 * @return Integer represented by key, null if key is not mapped
	 */
	public Integer getInt(String key) {
		try {
			return Integer.valueOf(String.valueOf(get(key)));
		}catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retrieves the Float/Double represented by the given key
	 * @param key
	 * @return Double value represented by key, null if key is not mapped
	 */
	public Double getDouble(String key) {
		try {
			return Double.valueOf(String.valueOf(get(key)));
		}catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Returns true if the given key is mapped with "true" as value, false otherwise
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return Boolean.valueOf(String.valueOf(get(key)));
	}

	/**
	 * Retrieves the JSONObject represented by the given key
	 * @param key
	 * @return JSONObject represented by key, null if key is not mapped
	 */
	public JSONObject getJSONObject(String key) {
		final Object o = get(key);
		if(o instanceof JSONObject)
			return (JSONObject) o;
		else
			return null;
	}

	/**
	 * Retrieves the JSONArray represented by the given key
	 * @param key
	 * @return JSONArray represented by key, null if key is not mapped
	 */
	public JSONArray getJSONArray(String key) {
		final Object o = get(key);
		if(o instanceof JSONArray)
			return (JSONArray) o;
		else
			return null;
	}
	
	
	public String[] getKeys() {
		final String[] ret = new String[this.length()];
		int i = 0;
		for(Entry<String, Object> entry : mappings.entrySet()) {
			ret[i] = entry.getKey();
			i++;
		}
		return ret;
	}

	
	public Object[] getValues() {
		final Object[] ret = new Object[this.length()];
		int i = 0;
		for(Entry<String, Object> entry : mappings.entrySet()) {
			ret[i] = entry.getValue();
			i++;
		}
		return ret;
	}

	/**
	 * Removes the object represented by the given key
	 * @param key
	 * @return true if the object was found and removed, false otherwise
	 */
	public boolean remove(String key) {
		final int lastLength = this.length();
		mappings.remove(key);
		return lastLength != this.length();
	}
	
	/**
	 * Return number of Objects mapped in this JSON
	 * @return 
	 */
	public int length() {
		return mappings.size();
	}
	
	/**
	 * Returns the JSON as a raw string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");

		for(Entry<String, Object> entry : mappings.entrySet())
			if(entry.getValue().toString().startsWith("{") || entry.getValue().toString().startsWith("["))
				sb.append("\""+entry.getKey() + "\":" + entry.getValue() + ",");
			else
				sb.append("\""+entry.getKey() + "\":\"" + entry.getValue() + "\",");
			
		
		sb.delete(sb.length() - 1, sb.length());
		return sb.append("}").toString();
	}
	
	
	public byte[] getByteArray() {
		return this.toString().getBytes();
	}
	
}
