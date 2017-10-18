package es.ed0.jsonlib;
import es.ed0.jsonlib.JSONException;
import es.ed0.jsonlib.JSONObject;

public class Main {

	public static void main(String[] args) {
		
			try {
				new Main();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	private Main() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("key1", "value1");
		json.put("key2", 2);
		json.put("key3", 3.2);
		
		String key1 = (String) json.get("key1");
		int key2 = (int) json.get("key2");
		float key3 = (float) json.get("key3");
		System.out.println(key1+" | "+key2+" | "+key3);
		
	}

}
