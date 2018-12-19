/**
 * Created by Ed0 in 4 dic. 2018
 */
package es.ed0.jsonlib;

import java.util.Iterator;
import java.util.List;

/**
 * Abstract class that defines basic functionality for JSON entities (json objects and arrays)
 * 
 * @param <T> Type of key -> String for JSONS, Integer for Arrays
 */
public abstract class JSONEntity<T> implements Iterable<Object> {
	
	
	/**
	 * Searches for a value recursively, this is handy for getting a value inside multiple nested JSONObjects<br>
	 * <b>Example: </b>JSONObject json = json1:{key1:"value0",json2:{json3:{key2:"false",value:"I'm a value!"}}}<br>
	 * json.get("json1", "json2", "json3", "value"); Would return "I'm a value!", or null if any of the nodes was not found
	 * @param keys
	 * @return
	 */
	public abstract Object get(String... keys);
	
	public abstract Object get(T t);
	
	/**
	 * @return Count of objects inside this json
	 */
	public abstract int size();

	public abstract void put(T t, Object o);
	
	public abstract boolean isEmpty();
	
	public abstract boolean isNull(T t);
	/**
	 * Returns a list with all Objects contained inside this json
	 * @return
	 */
	public abstract List<Object> values();

	/**
	 * Removes the object represented by the given key or index
	 * @param key
	 * @return true if the object was found and removed, false otherwise
	 */
	public abstract boolean remove(T t);
	
	/**
	 * Removes all data inside the json
	 */
	public abstract void clear();
	
	
	public Iterator<Object> iterator() {
		return values().iterator();
	}

	/**
	 * Retrieves the String represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public String getString(T t) {
		return String.valueOf(get(t));
	}

	/**
	 * Retrieves the Integer represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public Integer getInt(T t) {
		try {
			return Integer.valueOf(getString(t));
		}catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retrieves the Double represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public Double getDouble(T t) {
		try {
			return Double.valueOf(getString(t));
		}catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retrieves the Long represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public Long getLong(T t) {
		try {
			return Long.valueOf(getString(t));
		}catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retrieves the Boolean represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public Boolean getBoolean(T t) {
		final String stringValue = getString(t);
		if(stringValue.equals("true"))
			return true;
		else if(stringValue.equals("false"))
			return false;
		else
			return null;
	}

	/**
	 * Retrieves the JSONObject represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public JSONObject getJSONObject(T t) {
		final Object o = get(t);
		if(o instanceof JSONObject)
			return (JSONObject) o;
		else
			return null;
	}

	/**
	 * Retrieves the JSONArray represented by the given key or index
	 * @return Object represented by key, null if key is not mapped
	 */
	public JSONArray getJSONArray(T t) {
		final Object o = get(t);
		if(o instanceof JSONArray)
			return (JSONArray) o;
		else
			return null;
	}
	
	
	/**
	 * Returns the String representation of the JSON as a byte array
	 * @return
	 */
	public byte[] getAsByteArray() {
		return this.toString().getBytes();
	};
	
	/**
	 * Returns a prettified JSON string representing this JSONEntity
	 * @return
	 */
	public String toPrettyString() {
		return toPrettyString("");
	}

	/**
	 * Returns a prettified JSON string representing this JSONEntity.
	 * Appending the given String to the beggining of every line
	 * @return
	 */
	protected abstract String toPrettyString(String tabs);
	
	/**
	 * Returns the opening character for this json<br>
	 * '{' for jsonObject or '[' for jsonArray
	 * @see #getClosingChar()
	 * @return char as int
	 */
	public abstract int getOpeningChar();
	
	/**
	 * Returns the closing character for this json<br>
	 * '}' for jsonObject or ']' for jsonArray
	 * @see #getOpeningChar()
	 * @return char as int
	 */
	public abstract int getClosingChar();

	
}
