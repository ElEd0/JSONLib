JSONLib
===
Simple, feature-rich and boilerplate-free java JSON Library

[![](https://jitpack.io/v/ElEd0/JSONLib.svg)](https://jitpack.io/#ElEd0/JSONLib)


## Installation

Available using Jitpack from gradle

   ```gradle
   repositories { 
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
        implementation 'com.github.ElEd0:JSONLib:v2.2.2.1'
   }
   ```  


### Getting Started

```java	
	JSONObject json = new JSONObject();
	
	//Insert objects
	json.put("key1", "value1");
	json.put("key2", 123);
	json.put("key3", 12.3);
	json.put("key4", null);
	json.put("key5", false);
	json.put("key6", new JSONObject());
	json.put("key7", new JSONArray());
	
	//Retrieve objects
	String value1 = json.getString("key1");
	int value2 = json.getInt("key2");
	double value3 = json.getDouble("key3");
	long value4 = json.getLong("key4");
	boolean value5 = json.optBoolean("key5", false); // <- set fallback values
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

### Iterate over values

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

### Parsing

```java	
	JSONObject json, fromFile;
	JSONArray array;
	try {
		// parse from String
		json = JSONParser.get().parseJSONObject(jsonString);
		array = JSONParser.get().parseJSONArray(jsonString);
		// parse from file
		fromFile = JSONParser.get().parseJSONObjectFromFile("data.json");
		
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
	//for normal printing
	System.out.println(json.toString());
	
	//for pretty printing
	System.out.println(json.toPrettyString());
```

### Get JSON From HTTP request

```java	
	// EXAMPLE: create a maps API request to obtain route
	JSONUrlReader urlReader = new JSONUrlReader(
				"https://maps.googleapis.com/maps/api/directions/json", "GET");
		
	urlReader.addRequestHeader("Accept-Charset", "UTF-8");
	urlReader.addParameter("origin", origin.lat + "," + origin.lng);
	urlReader.addParameter("destination", destination.lat + "," + destination.lng);
	urlReader.addParameter("key", "api-key");
		
	// retrieve the json response and get the route
	try {
		JSONObject response = urlReader.readJSONObject();
		
	String route = response.getJSONArray("routes").getJSONObject(0)
		.getJSONObject("overview_polyline").getString("points");
		
	} catch (JSONException e) {
		e.printStackTrace();
	}
```

### Customizable Parsing configuration

```java	
	ParseConfiguration config = new ParseConfiguration();
	config.setAllowArbitraryCommas(false);
	config.setAllowExponential(true);
	config.setAllowUnknownEscapes(false);
	config.setAllowUpperCaseValues(true);
	config.setFailOnDuplicateMappings(true);
	config.setParseEscapedAsUnescaped(true);
	config.setParseNulls(true);
		
	JSONObject json = JSONParser.get(config).parseJSONObject(jsonString);
```

