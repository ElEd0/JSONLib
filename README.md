====================================
JSONLib tiniest JSON Library (WIP)
====================================
By Ed0

Download
--------

Last releases in the exports folder


Getting Started
---------------

```java
	JSONObject json = new JSONObject();
	
	//Insert objects
	json.put("key1", 234f);
	json.put("key2", "value2");
	
	//Retrieve objects
	Float value1 = (Float) json.get("key1");
	String value2 = (String) json.get("key2");
	
	
	JSONArray array = new JSONArray();
	array.put(json);
	array.put(json2);
	
	JSONObject ob = array.get(0);
	JSONObject[] objects = array.getAsArray();
	
	String raw1 = "{\"key5\":\"value5\",\"key4\":\"value4\"}";
	
	ob = new JSONObject(raw);
	array.put(new JSONObject(raw));
	
	//Use toString to retrieve the raw string
	String arrayAsRaw = array.toString();
	
	//and use it to create JSONObjects or JSONArrays
	JSONArray array2 = new JSONArray(arrayAsRaw);
	
	//remember to handle Exceptions when using raw strings
	JSONObject json;
	try {
		json = new JSONObject(raw);
	} catch (JSONException e) {
		e.printStackTrace();
	}
		
	
	//for pretty printing
	System.out.println(array2.toFancyString());
	
```

