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
	Double value1 = json.getDouble("key1");
	String value2 = json.getString("key2");
	
	
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
	
	for(Map<String, Object> pair : json.entrySet()) {
		System.out.println(pair.getKey() + " - " + pair.getValue());
	}
	
	// or...
	
	for(String key : json.keySet()) {
		System.out.println(key + " - " + json.getString(key));
	}
```

Parse to or from String
---------------

```java	
	JSONObject json;
	try {
		json = JSONParser.parseJSONObject(jsonString);
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
	//for normal printing
	System.out.println(json.toString());
	
	//for pretty printing
	System.out.println(json.toPrettyString());
	
```

