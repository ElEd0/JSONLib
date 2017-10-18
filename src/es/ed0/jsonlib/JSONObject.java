/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

public class JSONObject {
	
	private String body = "";
	
	/**
	 * Generates a new JSON from the raw string given
	 * @param Raw JSON string
	 * @throws JSONException if the given string is not a valid raw JSON string
	 */
	public JSONObject(String json) throws JSONException {
		if(!JSONValidator.validateJSON(json))
			throw new JSONException("Json string not valid!");
		else
			body=json.substring(1, json.length()-1); //remove braquets from json
	}
	
	/**
	 * Creates a new empty JSON
	 */
	public JSONObject() {
	}
	
	
	/**
	 * Adds the given object identified by the given key to this JSON
	 * If the given key is already mapped it will change the value
	 * @param key
	 * @param object
	 */
	public void put(String key, Object obj) {
		String value = String.valueOf(obj);
		this.remove(key);
		if(body.length()!=0)
			body+=",";
		body+="\""+key+"\":\""+value+"\"";
	}
	
	/**
	 * It gets the Object represented by the given key
	 * @param key
	 * @return Object represented by key, null if key is not mapped
	 */
	public Object get(String key) {
		String[] entries = body.split(",");
		if(entries.length==0)
			return null;
		for(String entry : entries) {
			String[] kandv = entry.split(":");
			if(kandv.length<2) return null;
			if(getValueFromString(kandv[0]).equals(key))
				return getValueFromString(kandv[1]);
			
		}
		
		return null;
	}
	/**
	 * Returns the Object represented by the key at <code>index</code>
	 * Starting at 0
	 * @param index
	 * @return Object represented by key at <code>index</code>, null if key is not mapped
	 */
	public Object get(int index) {

		String[] entries = body.split(",");
		if(entries.length==0)
			return null;
		String key;
		try {
			String[] kandv = entries[index].split(":");
			key=getValueFromString(kandv[0]);
		}catch(IndexOutOfBoundsException e) {
			return null;
		}
		
		return this.get(key);
		
	}
	/**
	 * Returns the key at <code>index</code>
	 * Starting at 0
	 * @param index
	 * @return null if key doesn't exist
	 */
	public Object getKey(int index) {

		String[] entries = body.split(",");
		if(entries.length==0)
			return null;
		try {
			String[] kandv = entries[index].split(":");
			return getValueFromString(kandv[0]);
		}catch(IndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	/**
	 * Removes the object represented by the given key
	 * @param key
	 * @return true if the object was found and removed, false otherwise
	 */
	public boolean remove(String key) {
		if(this.get(key)==null) return false;
		
		String[] entries = body.split(",");
		String temp = "";
		boolean could = false;
		
		for(String entry : entries) {
			if(!(entry.contains("\""+key+"\""))) {
				if(temp.length()!=0) temp+=",";
				temp+=entry;
			}else could=true;
		}
		body=temp;
		return could;
	}
	
	/**
	 * Return number of Objects mapped in this JSON
	 * @return 
	 */
	public int length() {
		return body.split(",").length;
	}
	
	/**
	 * Returns the JSON as a raw string
	 */
	public String toString() {
		return "{"+body+"}";
	}
	
	/**
	 * Returns a formated string (not valid as raw json string)
	 * @return
	 */
	public String toFancyString() {
		String ret = "";
		ret+="{\n";
		for(int i=0; i<length(); i++)
			ret+="-"+getKey(i)+" --> "+get(i)+"\n";
		ret+="}";
		return ret;
	}
	
	public byte[] getByteArray() {
		return this.toString().getBytes();
	}
	
	
	private String getValueFromString(String seq) {
		return seq.substring(seq.indexOf("\"")+1, seq.length()-1);
	}
	
}
