package es.ed0.tinyjson;

import java.util.Map;
import java.util.Map.Entry;

import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.parser.JSONParser;
import es.ed0.tinyjson.parser.ParseConfiguration;

public class Main {

	public static void main(String[] args) {
		
			try {
				new Main();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	
	private Main() throws JSONException {
		
		
		ParseConfiguration config = new ParseConfiguration();
		config.setAllowArbitraryCommas(false);
		config.setAllowExponential(true);
		config.setAllowUnknownEscapes(false);
		config.setAllowUpperCaseValues(true);
		config.setFailOnDuplicateMappings(false);
		config.setParseEscapedAsUnescaped(false);
		config.setParseNulls(true);
		
		

        JSONObject j = JSONParser.get(config).parseJSONObject("{\"key1\"   :   \"str\\\"uing } []  value 1\",\"key2\":\"string \\\"valu\\\"e2\",\"int1\":null,\"int2\":\"12.2\"\r\n" + 
        		",\"keye1\":12.3,\"boolean1(string)\":\"true\"    ,   \"boolean2\":true,\"boolean3\":false,\"json1\":{\"json_key1\":\"StRing_VaLuE\","
        		+ "\"json_key2\":\"StRing_Va{LuE_part_two\", \"array1\":[3, \"string test\", [12, 13, 12E+3, null], \"12.2\", false],\"end\":{\"key2\":\"stringvalue3\"}},\"int2\":10}") ;

        
        System.out.println(j);

		
	}

}
