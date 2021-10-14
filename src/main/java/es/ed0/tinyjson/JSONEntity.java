/**
 * Created by Ed0 in 4 dic. 2018
 */
package es.ed0.tinyjson;

import java.util.Iterator;
import java.util.List;

/**
 * Abstract class that defines basic functionality for JSON entities (json objects and arrays)
 * 
 * @param <T> Type of key: String for JSONs, Integer for Arrays
 */
public abstract class JSONEntity<T> implements Iterable<Object> {
	
	
	/**
	 * Searches for a value recursively, this is handy for getting a value inside multiple nested JSONObjects<br>
	 * <b>Example: </b>JSONObject json = json1:{key1:"value0",json2:{json3:{key2:"false",value:"I'm a value!"}}}<br>
	 * json.get("json1", "json2", "json3", "value"); Would return "I'm a value!", or null if any of the nodes was not found
	 * @param keys Ordered keys
	 * @return Object mapped by last key
	 */
	public abstract Object get(String... keys);
	
	
	/**
	 * Retrieves the Object mapped by the given key or index or throws JSONException
	 * if the key is not mapped
	 * @param t
	 * @return
	 * @exception JSONException if the key is not mapped
	 */
	public Object get(T t) throws JSONException {
		if (contains(t))
			return opt(t);
		throw new JSONException("The key or index is not mapped");
	}
	
	/**
	 * Retrieves the Object mapped by the given key or index or null if the key is not mapped
	 * @param t
	 * @return
	 */
	public abstract Object opt(T t);
	
	/**
	 * @return Count of objects inside this json
	 */
	public abstract int size();

	/**
	 * Adds the given object and maps it to the given key or index
	 * @param t Key or index
	 * @param o Object to add
	 * @return this json
	 */
	public abstract JSONEntity<T> put(T t, Object o);
	
	/**
	 * @return true if this json contains no objects
	 */
	public abstract boolean isEmpty();
	
	/**
	 * 
	 * @param t key or index
	 * @return true if the object specified by the given key or index is null, or the mapping does not exist
	 */
	public boolean isNull(T t) {
		return opt(t) == null;
	}
	
	/**
	 * Returns a list with all Objects contained inside this json
	 * @return
	 */
	public abstract List<Object> values();

	/**
	 * Removes the object represented by the given key or index
	 * @param t
	 * @return true if the object was found and removed, false otherwise
	 */
	public abstract boolean remove(T t);
	
	/**
	 * Removes all data inside the json
	 */
	public abstract void clear();

	/**
	 * Returns true if the json has a mapping for the given key or index
	 * @param t
	 */
	public abstract boolean contains(T t);
	
	/**
	 * Returns true if the value is mapped inside the json
	 * @param value
	 */
	public abstract boolean containsValue(Object value);
	
	public Iterator<Object> iterator() {
		return values().iterator();
	}

	/**
	 * Retrieves the String represented by the given key or index
	 * @return String represented by key
	 * @throws JSONException if key is not mapped
	 */
	public String getString(T t) throws JSONException {
		if (!contains(t))
			throw new JSONException("Value for " + t + " not found or is not a String type");
		return String.valueOf(get(t));
	}

	/**
	 * Retrieves the Boolean represented by the given key or index
	 * @return Boolean represented by key, null if key is not mapped
	 */
	public boolean getBoolean(T t) throws JSONException {
		final String stringValue = getString(t);
		if (stringValue.equals("true"))
			return true;
		else if (stringValue.equals("false"))
			return false;
		else
			throw new JSONException("Value for " + t + " not found or is not a boolean type");
	}

	/**
	 * Retrieves the value mapped by the given key or index in byte form
	 * @return byte mapped by key, null if key is not mapped
	 */
	public byte getByte(T t) throws JSONException {
		try {
			return Byte.parseByte(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not a byte type");
		}
	}

	/**
	 * Retrieves the value mapped by the given key or index in short form
	 * @return short mapped by key, null if key is not mapped
	 */
	public short getShort(T t) throws JSONException {
		try {
			return Short.parseShort(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not a byte type");
		}
	}
	/**
	 * Retrieves the Integer represented by the given key or index
	 * @return Integer represented by key, null if key is not mapped
	 */
	public int getInt(T t) throws JSONException {
		try {
			return Integer.parseInt(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not an int type");
		}
	}

	/**
	 * Retrieves the value mapped by the given key or index in float form
	 * @return byte mapped by key, null if key is not mapped
	 */
	public float getFloat(T t) throws JSONException {
		try {
			return Float.parseFloat(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not a byte type");
		}
	}
	/**
	 * Retrieves the Double represented by the given key or index
	 * @return Double represented by key, null if key is not mapped
	 */
	public double getDouble(T t) throws JSONException {
		try {
			return Double.parseDouble(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not a double type");
		}
	}

	/**
	 * Retrieves the Long represented by the given key or index
	 * @return Long represented by key, null if key is not mapped
	 */
	public long getLong(T t) throws JSONException {
		try {
			return Long.parseLong(getString(t));
		} catch (NumberFormatException e) {
			throw new JSONException("Value for " + t + " not found or is not a long type");
		}
	}

	/**
	 * Retrieves the JSONObject represented by the given key or index
	 * @return JSONObject represented by key, null if key is not mapped
	 */
	public JSONObject getJSONObject(T t) throws JSONException {
		final Object o = get(t);
		if(o instanceof JSONObject)
			return (JSONObject) o;
		else
			throw new JSONException("Value for " + t + " not found or is not a JSONObject");
	}

	/**
	 * Retrieves the JSONArray represented by the given key or index
	 * @return JSONArray represented by key, null if key is not mapped
	 */
	public JSONArray getJSONArray(T t) throws JSONException {
		final Object o = get(t);
		if(o instanceof JSONArray)
			return (JSONArray) o;
		else
			throw new JSONException("Value for " + t + " not found or is not a JSONArray");
	}

	/**
	 * Retrieves the String represented by the given key or index
	 * @return String represented by key, null if key is not mapped
	 */
	public String optString(T t) {
		return optString(t, "");
	}

	/**
	 * Retrieves the Integer represented by the given key or index
	 * @return Integer represented by key, null if key is not mapped
	 */
	public int optInt(T t) {
		return optInt(t, 0);
	}

	/**
	 * Retrieves the Double represented by the given key or index
	 * @return Double represented by key, null if key is not mapped
	 */
	public double optDouble(T t) {
		return optDouble(t, 0);
	}

	/**
	 * Retrieves the Long represented by the given key or index
	 * @return Long represented by key, null if key is not mapped
	 */
	public long optLong(T t) {
		return optLong(t, 0);
	}

	/**
	 * Retrieves the Boolean represented by the given key or index
	 * @return Boolean represented by key, null if key is not mapped
	 */
	public boolean optBoolean(T t) {
		return optBoolean(t, false);
	}

	/**
	 * Retrieves the JSONObject represented by the given key or index
	 * @return JSONObject represented by key, null if key is not mapped
	 */
	public JSONObject optJSONObject(T t) {
		return optJSONObject(t, null);
	}

	/**
	 * Retrieves the JSONArray represented by the given key or index
	 * @return JSONArray represented by key, null if key is not mapped
	 */
	public JSONArray optJSONArray(T t) {
		return optJSONArray(t, null);
	}
	/**
	 * Retrieves the String represented by the given key or index
	 * @return String represented by key, default value if not found
	 */
	public String optString(T t, String defValue) {
		final String s = String.valueOf(opt(t));
		return s == null ? defValue : s;
	}


	/**
	 * Retrieves the Boolean represented by the given key or index
	 * @return Boolean represented by key, default value if not found or is not a boolean
	 */
	public boolean optBoolean(T t, boolean defValue) {
		final String stringValue = optString(t);
		if(stringValue.equals("true"))
			return true;
		else if(stringValue.equals("false"))
			return false;
		else
			return defValue;
	}

	/**
	 * Retrieves the Integer represented by the given key or index
	 * @return Integer represented by key, default value if not found or is not an int
	 */
	public byte optByte(T t, byte defValue) {
		try {
			return Byte.parseByte(optString(t));
		} catch (NumberFormatException e) {
			return defValue;
		}
	}
	
	/**
	 * Retrieves the Integer represented by the given key or index
	 * @return Integer represented by key, default value if not found or is not an int
	 */
	public int optInt(T t, int defValue) {
		try {
			return Integer.parseInt(optString(t));
		} catch (NumberFormatException e) {
			return defValue;
		}
	}

	/**
	 * Retrieves the Double represented by the given key or index
	 * @return Double represented by key, default value if not found or is not a double
	 */
	public double optDouble(T t, double defValue) {
		try {
			return Double.parseDouble(optString(t));
		}catch (NumberFormatException e) {
			return defValue;
		}
	}

	/**
	 * Retrieves the Long represented by the given key or index
	 * @return Long represented by key, default value if not found or is not a long
	 */
	public long optLong(T t, long defValue) {
		try {
			return Long.parseLong(optString(t));
		}catch (NumberFormatException e) {
			return defValue;
		}
	}


	/**
	 * Retrieves the JSONObject represented by the given key or index
	 * @return JSONObject represented by key, default value if not found or is not a JSONObject
	 */
	public JSONObject optJSONObject(T t, JSONObject defValue) {
		final Object o = opt(t);
		if(o instanceof JSONObject)
			return (JSONObject) o;
		else
			return defValue;
	}

	/**
	 * Retrieves the JSONArray represented by the given key or index
	 * @return JSONArray represented by key, default value if not found or is not a JSONObject
	 */
	public JSONArray optJSONArray(T t, JSONArray defValue) {
		final Object o = opt(t);
		if(o instanceof JSONArray)
			return (JSONArray) o;
		else
			return defValue;
	}
	
	/**
	 * Returns the String representation of the JSON as a byte array
	 * @return
	 */
	public byte[] getAsByteArray() {
		return this.toString().getBytes();
	};

	/**
	 * Returns this JSONEntity as a JSONObject if class types match
	 * @return this JSONEntity casted as a JSONObject
	 * @throws JSONException if types don't match
	 */
	public JSONObject getAsJSONObject() throws JSONException {
		if (this instanceof JSONObject) {
			return (JSONObject) this;
		}

		throw new JSONException("JSONEntity cannot be returned as JSONObject");
	}

	/**
	 * Returns this JSONEntity as a JSONArray if class types match
	 * @return this JSONEntity casted as a JSONArray
	 * @throws JSONException if types don't match
	 */
	public JSONArray getAsJSONArray() throws JSONException {
		if (this instanceof JSONArray) {
			return (JSONArray) this;
		}

		throw new JSONException("JSONEntity cannot be returned as JSONArray");
	}

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
		
}
