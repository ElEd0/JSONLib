/**
 * Created by Ed0 in 4 dic. 2018
 */
package es.ed0.jsonlib;


public interface JSONEntity {

	
	public abstract static class Type {
		public static final String JSON_OBJ = "JSONObject";
		public static final String JSON_ARR = "JSONArray";
	}
	

	/**
	 * Searches for a value recursively, this is handy for getting a value inside multiple nested JSONObjects<br>
	 * <b>Example: </b>JSONObject json = json1:{key1:"value0",json2:{json3:{key2:"false",value:"I'm a value!"}}}<br>
	 * json.get("json1", "json2", "json3", "value"); Would return "I'm a value!", or null if any of the nodes was not found
	 * @param keys
	 * @return
	 */
	public Object get(String... keys);
	
	/**
	 * Returns the String representation of the JSON as a byte array
	 * @return
	 */
	public byte[] getAsByteArray();

	
}
