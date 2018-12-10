/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.HashMap;

public class JSONObject extends HashMap<String, Object> implements JSONEntity {
	private static final long serialVersionUID = -6961839899528603878L;
	

	/**
	 * Creates a new empty JSON
	 */
	public JSONObject() {
		super();
	}


	/**
	 * Retrieves the Object represented by the given key
	 * @param key as a String
	 * @return Object represented by key, or null value not mapped
	 */
	public Object get(String key) {
		return JSONParser.escapeQuotes(super.get(key), true);
	}
	
	
	
	/* (non-Javadoc)
	 * @see es.ed0.jsonlib.JSONEntity#get(java.lang.String[])
	 */
	@Override
	public Object get(String... keys) {
		final String[] leftOverKeys = new String[keys.length - 1];
		for(int i=0; i<leftOverKeys.length; i++)
			leftOverKeys[i] = keys[i+1];
		
 		final Object o = get(keys[0]);
		if(o == null || keys.length == 1)
			return o;
		else if (o instanceof JSONEntity) 
			return ((JSONEntity) o).get(leftOverKeys);
		else 
			return null;
	}
	
	/**
	 * Retrieves the String represented by the given key
	 * @param key
	 * @return Object represented by key, null if key is not mapped
	 */
	public String getString(String key) {
		final Object o = get(key);
		if(o != null)
			return o.toString();
		else
			return null;
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
		else {
			try {
				return JSONParser.parseJSONObject(o.toString());
			} catch (JSONException e) {
				return null;
			}
		}
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
		final String[] ret = new String[this.size()];
		int i = 0;
		for(Entry<String, Object> entry : this.entrySet()) {
			ret[i] = entry.getKey();
			i++;
		}
		return ret;
	}

	
	public Object[] getValues() {
		final Object[] ret = new Object[this.size()];
		int i = 0;
		for(Entry<String, Object> entry : this.entrySet()) {
			ret[i] = entry.getValue();
			i++;
		}
		return ret;
	}

	/**
	 * Does basically the same as put
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		super.put(key, value);
	}
	/**
	 * Removes the object represented by the given key
	 * @param key
	 * @return true if the object was found and removed, false otherwise
	 */
	public boolean remove(String key) {
		final int lastLength = this.size();
		super.remove(key);
		return lastLength != this.size();
	}
	
	/**
	 * Returns the JSON as a raw string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");

		for(Entry<String, Object> entry : this.entrySet())
			if(entry.getValue().toString().startsWith("{") || entry.getValue().toString().startsWith("["))
				sb.append("\""+entry.getKey() + "\":" + entry.getValue() + ",");
			else
				sb.append("\""+entry.getKey() + "\":\"" + entry.getValue() + "\",");
			
		
		sb.delete(sb.length() - 1, sb.length());
		return sb.append("}").toString();
	}
	

	public HashMap<String, String> asMap() {
		final HashMap<String, String> ret = new HashMap<>();
		for(Entry<String, Object> entry : this.entrySet())
			ret.put(entry.getKey(), entry.getValue().toString());
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see es.ed0.jsonlib.JSONEntity#getAsByteArray()
	 */
	@Override
	public byte[] getAsByteArray() {
		// TODO Auto-generated method stub
		return toString().getBytes();
	}

	
}
