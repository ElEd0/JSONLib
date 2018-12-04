/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;

public class JSONArray extends ArrayList<Object> implements JSONEntity {
	private static final long serialVersionUID = 5931962894418725776L;

	public JSONArray() {
		super();
	}
	
	/**
	 * Retrieves the Object represented by the given index
	 * @param index as a String
	 * @return Object represented by index, or null if value cant be found
	 */
	public Object get(String key) {
		try {
			return get(Integer.valueOf(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Retrieves the String represented by the given key
	 * @param key
	 * @return Object represented by index, or null if value cant be found
	 */
	public String getString(int index) {
		final Object o = get(index);
		if(o != null)
			return o.toString();
		else
			return null;
	}

	/**
	 * Retrieves the Integer represented by the given key
	 * @param key
	 * @return Integer represented by index, or null if value cant be found
	 */
	public Integer getInt(int index) {
		try {
			return Integer.valueOf(String.valueOf(get(index)));
		}catch (NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	/**
	 * Retrieves the Float/Double represented by the given key
	 * @param key
	 * @return Double value represented by index, or null if value cant be found
	 */
	public Double getDouble(int index) {
		try {
			return Double.valueOf(String.valueOf(get(index)));
		}catch (NumberFormatException | NullPointerException e) {
			return null;
		}
	}

	/**
	 * Returns true if the given index is mapped with true as value, false otherwise
	 * @param key
	 * @return
	 */
	public boolean getBoolean(int index) {
		return Boolean.valueOf(String.valueOf(get(index)));
	}

	/**
	 * Retrieves the JSONObject represented by the given key
	 * @param key
	 * @return JSONObject represented by index, or null if value cant be found
	 */
	public JSONObject getJSONObject(int index) {
		final Object o = get(index);
		if(o instanceof JSONObject)
			return (JSONObject) o;
		else
			return null;
	}
	
	/**
	 * Retrieves the JSONArray represented by the given index
	 * @param key
	 * @return JSONArray represented by index, or null if value cant be found
	 */
	public JSONArray getJSONArray(int index) {
		final Object o = get(index);
		if(o instanceof JSONArray)
			return (JSONArray) o;
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see es.ed0.jsonlib.JSONEntity#get(java.lang.String[])
	 */
	@Override
	public Object get(String... keys) {
		final String[] leftOverKeys = new String[keys.length - 1];
		for(int i=0; i<leftOverKeys.length; i++)
			leftOverKeys[i] = keys[i+1];
		
 		Object o = null;
		try {
			o = get(Integer.valueOf(keys[0]));
		} catch (NumberFormatException e) {
			return null;
		}
		if(o == null || keys.length == 1)
			return o;
		else if (o instanceof JSONEntity) 
			return ((JSONEntity) o).get(leftOverKeys);
		else 
			return null;
	}

	/**
	 * Returns the JSONArray as a raw string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[");

		for(Object obj : this)
			// "value",
			sb.append(obj).append(",");
		
		sb.substring(0, sb.length() - 1);
		return sb.append("]").toString();
	}
	/* (non-Javadoc)
	 * @see es.ed0.jsonlib.JSONEntity#getAsByteArray()
	 */
	@Override
	public byte[] getAsByteArray() {
		return toString().getBytes();
	}
	

}
