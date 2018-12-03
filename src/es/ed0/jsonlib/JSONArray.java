/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;

public class JSONArray {
	
	private ArrayList<Object> objects;
	
	public JSONArray() {
		objects = new ArrayList<Object>();
	}
	
	public JSONArray(String raw) throws JSONException {
		objects = JSONValidator.validateAndList(raw);
		if(objects == null)
			throw new JSONException("JSONArray String not valid");		
	}
	
	public boolean add(Object obj) {
		return objects.add(obj);
	}

	public boolean remove(int index) {
		try {
			return objects.remove(index) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean remove(Object obj) {
		return objects.remove(obj);
	}
	
	public Object get(int index) {
		return objects.get(index);
	}
	
	/**
	 * Returns the JSONArray as a raw string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("[");

		for(Object obj : objects)
			// "value",
			sb.append(obj).append(",");
		
		sb.substring(0, sb.length() - 1);
		return sb.append("]").toString();
	}

	public Object[] getValues() {
		return objects.toArray();
	}
	
	public int length() {
		return objects.size();
	}
	
	public byte[] getByteArray() {
		return this.toString().getBytes();
	}
	

}
