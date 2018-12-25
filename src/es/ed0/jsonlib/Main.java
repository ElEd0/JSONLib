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
		
		
		
        JSONObject j = JSONParser.parseJSONObject("{\"key1\"   :   \"str\\ning } []  value 1\",\"key2\":\"stringvalue2\",\"int1\":\"12\",\"int2\":\"12.2\"\r\n" + 
        		",\"int3\":12.3,\"boolean1(string)\":\"true\"    ,   \"boolean2\":true,\"boolean3\":false,\"json1\":{\"json_key1\":\"StRing_VaLuE\","
        		+ "\"json_key2\":\"StRing_Va{LuE_part_two\", \"array1\":[3, \"string test\", [12, 13, false, null], \"12.2\", false],\"end\":{\"key2\":\"stringvalue3\"}}}") ;

        System.out.println(j.toString());

		
	}

}
