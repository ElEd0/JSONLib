/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JSONArray extends JSONEntity<Integer> {

	final ArrayList<Object> list;
	
	public JSONArray() {
		super();
		list = new ArrayList<Object>();
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
			sb.append(JSONParser.getJsonStringValueForObject(obj)).append(",");
		
		sb.setLength(sb.length() - 1);
		return sb.append("]").toString();
	}

	@Override
	public String toPrettyString() {
		return toPrettyString("");
	}
	
	public String toPrettyString(String tabs) {
		final StringBuilder sb = new StringBuilder("[\n");
		int c = 0;
		for(Object entry : this) {
			sb.append(tabs + "\t");
			if(entry instanceof JSONEntity)
				sb.append(((JSONEntity) entry).toPrettyString(tabs + "\t"));
			else
				sb.append(JSONParser.getJsonStringValueForObject(entry));
			if(c != this.size() - 1)
				sb.append(",");
			sb.append("\n");
			c++;
		}
		sb.append(tabs + "]");
		return sb.toString();
	}


	@Override
	public Object get(Integer t) {
		return list.get(t);
	}


	public boolean containsValue(Object value) {
		return list.contains(value);
	}


	@Override
	public int size() {
		return list.size();
	}

	public void add(Object o) {
		list.add(o);
	}
	
	public void addAll(Collection<? extends Object> c) {
		list.addAll(c);
	}
	
	@Override
	public void put(Integer t, Object o) {
		list.add(t, o);		
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

	@Override
	public int getOpeningChar() {
		return C.array_open;
	}

	@Override
	public int getClosingChar() {
		return C.array_close;
	}

	@Override
	public boolean isNull(Integer t) {
		return get(t) == null;
	}

}
