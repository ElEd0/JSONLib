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
	json.put("key3", true);
	json.put("key4", null);
	
	//Retrieve objects
	String value1 = json.getString("key1");
	int value2 = json.getInt("key2");
	double value3 = json.getDouble("key3");
	long value4 = json.getLong("key4");
	boolean value5 = json.getBoolean("key5");
	JSONObject value6 = json.getJSONObject("key6");
	JSONArray value7 = json.getJSONArray("key7");
	
	
	JSONArray array = new JSONArray();
	array.add(json);
	array.add(json2);
	
	JSONObject obj = array.getJSONObject(0);
	
	// search for nested values
	// {"json1":{"array1":[12.3, "text", {"key3":"value to return"}]}}
	obj.get("json1", "array1", "2", "key3");
	
	
	// quick creation
	return new JSONObject().put("name", "Foo bar").put("age", 23).toString();
	
	
```

Iterate over values
---------------

```java
	// JSONArrays
	
	for(Object obj : array) {
		System.out.println(obj.toString());
	}
	
	// JSONObjects
	
	for(Map.Entry<String, Object> pair : json.entrySet()) {
		System.out.println(pair.getKey() + " - " + pair.getValue());
	}
	
	// or...
	
	for(String key : json.keySet()) {
		System.out.println(key + " - " + json.getString(key));
	}
```

Parsing
---------------

```java	
	JSONObject json, fromFile;
	JSONArray array;
	try {
		json = JSONParser.parseJSONObject(jsonString);
		array = JSONParser.parseJSONArray(jsonString);
		fromFile = JSONParser.parseJSONObjectFromFile("data.json");
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
	//for normal printing
	System.out.println(json.toString());
	
	//for pretty printing
	System.out.println(json.toPrettyString());
	
```

