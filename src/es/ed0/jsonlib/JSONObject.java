/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONObject extends JSONEntity<String> {
	private static final long serialVersionUID = -6961839899528603878L;
	
	private final Map<String, Object> map;
	
	/**
	 * Creates a new empty JSON
	 */
	public JSONObject() {
		super();
		this.map = new HashMap<String, Object>();
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
	 * Adds the given value and maps it to the given key
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		map.put(key, value);
	}
	
	/**
	 * Removes the object represented by the given key
	 * @param key
	 * @return true if the object was found and removed, false otherwise
	 */
	public boolean remove(String key) {
		final int lastLength = this.size();
		map.remove(key);
		return lastLength != this.size();
	}
	
	/**
	 * Returns the JSON as a raw string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");

		for(String key : keySet())
			sb.append("\"" + key + "\":" + JSONParser.getJsonStringValueForObject(get(key)) + ",");
			
		sb.delete(sb.length() - 1, sb.length());
		return sb.append("}").toString();
	}
	

	@Override
	public String toPrettyString(String tabs) {
		final StringBuilder sb = new StringBuilder("{\n");
		int c = 0;
		for(Map.Entry<String, Object> entry : entrySet()) {
			sb.append(tabs + "\t" + entry.getKey() + " : ");
			final Object obj = entry.getValue();
			if(obj instanceof JSONEntity)
				sb.append(((JSONEntity) obj).toPrettyString(tabs + "\t"));
			else
				sb.append(JSONParser.getJsonStringValueForObject(entry.getValue()));
			if(c != this.size() - 1)
				sb.append(",");
			sb.append("\n");
			c++;
		}
		sb.append(tabs + "}");
		return sb.toString();
	}


	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * Returns a Set view of the mappings contained in this JSONObject
	 * @return String Set containing all key-value pairs
	 */
	public Set<Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}
	
	/**
	 * Returns a Set view of the keys contained in this JSONObject
	 * @return String Set containing all keys
	 */
	public Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * Returns a Set view of the values contained in this JSONObject
	 * @return String Set containing all values
	 */
	@Override
	public List<Object> values() {
		return new ArrayList<Object>(map.values());
	}

	/**
	 * Returns a map containing all key-value pairs in this JSONObject
	 * @return
	 */
	public Map<String, Object> asMap() {
		return map;
	}
	
	@Override
	public void clear() {
		map.clear();		
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public int size() {
		return map.size();
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}


	
}
