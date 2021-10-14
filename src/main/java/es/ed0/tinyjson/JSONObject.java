/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.ed0.tinyjson.parser.JSONParser;

/**
 * Implementation of JSONEntity for JSONObjects
 */
public class JSONObject extends JSONEntity<String> {
	
	private final Map<String, Object> map;
	
	/**
	 * Creates a new empty JSON
	 */
	public JSONObject() {
		super();
		this.map = new LinkedHashMap<String, Object>();
	}

	
	@Override
	public Object get(String... keys) {
		final String[] leftOverKeys = new String[keys.length - 1];
		for (int i = 0, len = leftOverKeys.length; i < len; i++)
			leftOverKeys[i] = keys[i + 1];
		
 		final Object o = opt(keys[0]);
		if (o == null || keys.length == 1)
			return o;
		else if (o instanceof JSONEntity) 
			return ((JSONEntity<?>) o).get(leftOverKeys);
		else 
			return null;
	}

	@Override
	public Object opt(String t) {
		return map.get(t);
	}

	@Override
	public JSONObject put(String key, Object value) {
		map.put(key, value);
		return this;
	}
	
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

		for (Map.Entry<String, Object> entry : entrySet())
			sb.append("\"").append(entry.getKey()).append("\":")
					.append(JSONParser.getJsonStringValueForObject(entry.getValue())).append(",");
		
		if(sb.length() != 1)
			sb.delete(sb.length() - 1, sb.length()); // remove last comma
		return sb.append("}").toString();
	}
	

	@Override
	protected String toPrettyString(String tabs) {
		final StringBuilder sb = new StringBuilder("{\n");
		int c = 0;
		for(Map.Entry<String, Object> entry : entrySet()) {
			sb.append(tabs).append("\t\"").append(entry.getKey()).append("\" : ");
			final Object obj = entry.getValue();
			if (obj instanceof JSONEntity)
				sb.append(((JSONEntity<?>) obj).toPrettyString(tabs + "\t"));
			else
				sb.append(JSONParser.getJsonStringValueForObject(entry.getValue()));
			if (c != this.size() - 1)
				sb.append(",");
			sb.append("\n");
			c++;
		}
		sb.append(tabs).append("}");
		return sb.toString();
	}

	@Override
	public boolean contains(String key) {
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

	public void putAll(Map<? extends String, ?> m) {
		map.putAll(m);
	}

	
}
