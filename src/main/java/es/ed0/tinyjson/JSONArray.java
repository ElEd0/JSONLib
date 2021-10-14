/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.ed0.tinyjson.parser.JSONParser;

/**
 * Implementation of JSONEntity for JSONArrays
 */
public class JSONArray extends JSONEntity<Integer> {

	final ArrayList<Object> list;
	
	/**
	 * Creates a new empty JSONArray
	 */
	public JSONArray() {
		super();
		list = new ArrayList<Object>();
	}

	@Override
	public Object get(String... keys) {
		final String[] leftOverKeys = new String[keys.length - 1];
		for (int i = 0, len = leftOverKeys.length; i < len; i++)
			leftOverKeys[i] = keys[i+1];
		
 		Object o = null;
		try {
			o = opt(Integer.valueOf(keys[0]));
		} catch (NumberFormatException e) {
			return null;
		}
		if (o == null || keys.length == 1)
			return o;
		else if (o instanceof JSONEntity)
			return ((JSONEntity<?>) o).get(leftOverKeys);
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
			sb.append(JSONParser.getJsonStringValueForObject(obj)).append(",");
		
		if(sb.length() != 1)
			sb.setLength(sb.length() - 1);
		return sb.append("]").toString();
	}

	/**
	 * Returns a prettified version of the JSONArray
	 */
	@Override
	public String toPrettyString() {
		return toPrettyString("");
	}
	
	protected String toPrettyString(String tabs) {
		final StringBuilder sb = new StringBuilder("[\n");
		int c = 0;
		for(Object entry : this) {
			sb.append(tabs).append("\t");
			if(entry instanceof JSONEntity)
				sb.append(((JSONEntity<?>) entry).toPrettyString(tabs + "\t"));
			else
				sb.append(JSONParser.getJsonStringValueForObject(entry));
			if(c != this.size() - 1)
				sb.append(",");
			sb.append("\n");
			c++;
		}
		sb.append(tabs).append("]");
		return sb.toString();
	}

	@Override
	public Object opt(Integer t) {
		try {
			return list.get(t);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public boolean contains(Integer t) {
		return t < this.size() && t >= 0;
	}

	public boolean containsValue(Object value) {
		return list.contains(value);
	}

	@Override
	public int size() {
		return list.size();
	}

	public JSONArray add(Object o) {
		list.add(o);
		return this;
	}
	
	public void addAll(Collection<?> c) {
		list.addAll(c);
	}
	
	@Override
	public JSONArray put(Integer t, Object o) {
		list.add(t, o);
		return this;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public List<Object> values() {
		return list;
	}

	@Override
	public boolean remove(Integer t) {
		final int lastLength = this.size();
		list.remove((int) t);
		return lastLength != this.size();
	}

	@Override
	public void clear() {
		list.clear();
	}

}
