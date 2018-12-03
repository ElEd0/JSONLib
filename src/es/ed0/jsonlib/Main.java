package es.ed0.jsonlib;
import java.util.HashMap;

import es.ed0.jsonlib.JSONException;
import es.ed0.jsonlib.JSONObjectOld;

public class Main {

	public static void main(String[] args) {
		
			try {
				new Main();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	
	private Main() throws JSONException {
		
		
		
        JSONObject j = new JSONObject("{\"key1\"   :   \"string {} []  value 1\",\"key2\":\"stringvalue2\",\"int1\":\"12\",\"int2\":\"12.2\"\r\n" + 
        		",\"int3\":12.3,\"boolean1\":\"true\"    ,   \"boolean2\":true,\"boolean3\":false,\"json1\":{\"json_key1\":\"StRing_VaLuE\","
        		+ "\"json_key2\":\"StRing_VaLuE_part_two\", \"array1\":[3, \"string test\", 23.4, \"12.2\", false],\"end\":{}}}") ;

        System.out.println(j.length());
        
        for(Object o : j.getKeys()) {
        	System.out.println(o + " : " + j.get(o + ""));
        }
        
        
        /*
        
        j.put("key1", 123);
        j.put("key2", "string2");


        System.out.println(j.get("key2"));

        */
		
	}

}
