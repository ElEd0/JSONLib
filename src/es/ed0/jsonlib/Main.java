package es.ed0.jsonlib;

import es.ed0.jsonlib.JSONException;

public class Main {

	public static void main(String[] args) {
		
			try {
				new Main();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	
	private Main() throws JSONException {
		
		
		
        JSONObject j = JSONParser.parseJSONObjectFromString("{\"key1\"   :   \"string {} []  value 1\",\"key2\":\"stringvalue2\",\"int1\":\"12\",\"int2\":\"12.2\"\r\n" + 
        		",\"int3\":12.3,\"boolean1\":\"true\"    ,   \"boolean2\":true,\"boolean3\":false,\"json1\":{\"json_key1\":\"StRing_VaLuE\","
        		+ "\"json_key2\":\"StRing_VaLuE_part_two\", \"array1\":[3, \"string test\", 23.4, \"12.2\", false],\"end\":{\"key2\":\"stringvalue2\"}}}") ;

        System.out.println(j.size());

        System.out.println("JSON1-----------");
        for(Object o : j.getKeys()) {
        	System.out.println(o + " : " + j.get(o + ""));
        }
        
        JSONObject j2 = j.getJSONObject("json1");


        System.out.println("JSON2-----------");
        for(Object o : j2.getKeys()) {
        	System.out.println(o + " : " + j2.get(o + ""));
        }
        
        JSONArray ja = j2.getJSONArray("array1");

        System.out.println("ARRAY-----------");
        for(Object o : ja) {
        	System.out.println(o);
        }
        
        System.out.println(j.get("json1", "array1", "key2"));
        
        /*
        
        j.put("key1", 123);
        j.put("key2", "string2");


        System.out.println(j.get("key2"));

        */
		
	}

}
