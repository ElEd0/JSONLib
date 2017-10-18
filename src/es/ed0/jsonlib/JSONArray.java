/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.jsonlib;

import java.util.ArrayList;

public class JSONArray {
	
	private ArrayList<JSONObject> jsons;
	
	public JSONArray() {
		jsons = new ArrayList<JSONObject>();
	}
	
	public JSONArray(String raw) throws JSONException {
		if(!JSONValidator.validateArray(raw))
			throw new JSONException("JsonArray String not valid");
	}
	
	public void put(JSONObject json) {
		jsons.add(json);
	}
	
	public void put(String raw) throws JSONException {
		jsons.add(new JSONObject(raw));
	}
	
	/**
	 * Returns the JSONArray as a raw string
	 */
	public String toString() {
		String ret = "";
		for(JSONObject json : jsons) {
			if(ret.length()!=0)
				ret+=";";
			ret+=json.toString();
		}
		return "["+ret+"]";
	}
	

	/**
	 * Returns a formated string (not valid as raw json string)
	 * @return
	 */
	public String toFancyString() {
		String ret = "";
		ret+="[--------------\n";
		for(int j=0; j<jsons.size(); j++) {
			if(j!=0)
				ret+="---------------\n";
			ret+="  json "+j+" {\n";
			for(int i=0; i<jsons.get(j).length(); i++)
				ret+="  -"+jsons.get(j).getKey(i)+" --> "+jsons.get(j).get(i)+"\n";
			ret+="  }\n";
		}
		ret+="---------------]";
		return ret;
	}
	
	public JSONObject[] getAsArray() {
		JSONObject[] ret = new JSONObject[jsons.size()];
		for(int i=0; i<jsons.size(); i++)
			ret[i]=jsons.get(i);
		return ret;
	}
	
	public byte[] getByteArray() {
		return this.toString().getBytes();
	}
	

}
