package es.ed0.jsonlib;

import java.util.Map.Entry;

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
		
		
		
        JSONObject j = JSONParser.parseJSONObject("{\"key1\"   :   \"string {} []  value 1\",\"key2\":\"stringvalue2\",\"int1\":\"12\",\"int2\":\"12.2\"\r\n" + 
        		",\"int3\":12.3,\"boolean1\":\"true\"    ,   \"boolean2\":true,\"boolean3\":false,\"json1\":{\"json_key1\":\"StRing_VaLuE\","
        		+ "\"json_key2\":\"StRing_VaLuE_part_two\", \"array1\":[3, \"string test\", 23.4, \"12.2\", false],\"end\":{\"key2\":\"stringvalue3\"}}}") ;

        System.out.println(j.size());

        System.out.println("JSON1-----------");
        for(Object o : j.getKeys()) {
        	System.out.println(o + " : " + j.get(o + ""));
        }
        

        System.out.println("ARRAY1-----------");
        for(Object o : j.getJSONObject("json1").getJSONArray("array1")) {
        	System.out.println(o + " -> " + o.getClass().getName());
        }
        
        System.out.println(j.getJSONObject("json1").getJSONArray("array1").getInt(3));

        
		
	}

}
