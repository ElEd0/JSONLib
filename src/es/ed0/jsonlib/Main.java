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
		
        JSONObject j = new JSONObject();

        j.put("key1", 123);
        j.put("key2", "string2");

        for(Object o : j.valueArray()) {
        	System.out.println(o);
        }

        System.out.println(j.get("key2").toString());

		
	}

}
