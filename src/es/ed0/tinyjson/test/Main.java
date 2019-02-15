package es.ed0.tinyjson.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;
import es.ed0.tinyjson.parser.JSONParser;
import es.ed0.tinyjson.parser.JSONUrlReader;
import es.ed0.tinyjson.parser.ParseConfiguration;
import es.ed0.tinyjson.serialization.JSONSerializer;

public class Main {

	public static void main(String[] args) {
		try {
			new Main();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Main() throws JSONException {
		
		JSONSerializer serial = new JSONSerializer();
		List<Student> studs = new ArrayList<Student>();
		studs.add(new Student("ab0", "holi", "year2", true, new int[] {0, 1, 2, 3, 4}, new int[] {5, 4, 3, 2, 1}));
		studs.add(new Student("ab1", "holi1", "year1", false, new int[] {0, 1, 2, 3, 4}, new int[] {5, 4, 3, 2, 1}));
		studs.add(new Student("ab2", "holi2", "year2", false, new int[] {0, 1, 2, 3, 4}, new int[] {5, 4, 3, 2, 1}));
		studs.add(new Student("ab3", "holi3", "year3", true, new int[] {0, 1, 2, 3, 4}, new int[] {5, 4, 3, 2, 1}));
		studs.add(new Student("ab4", "holi4", "year4", true, new int[] {0, 1, 2, 3, 4}, new int[] {5, 4, 3, 2, 1}));
		
		JSONObject pojo = serial.serializeToJSONObject(new Pojo());
		
		pojo.put("name", "jsonobject value");
		pojo.put("routes", new JSONArray()
				.add(serial.serializeToJSONObject(new Route(1, "ruta1", 7, 2)))
				.add(serial.serializeToJSONObject(new Route(2, "ruta2", 6, 3)))
				.add(serial.serializeToJSONObject(new Route(3, "ruta3", 5, 4))));
		
		System.out.println(pojo.toPrettyString());
		
		Pojo newPojo = serial.deserialize(pojo, Pojo.class);
		
		System.out.println(serial.serializeToJSONObject(newPojo).toPrettyString());
		
		System.out.println();
		
		//JSONArray des = serial.serializeToArray(studs);
		
		//System.out.println(serialized.toString());
//		
//		System.out.println(new JSONObject().put("key1", i).get("key1").getClass().getSimpleName());
//		
//		if( true) return;
//		
//		List<BusStop> stops = new ArrayList<BusStop>();
//		stops.add(new BusStop(2, "direccion norte 13 14", 39.89765, -0.12578, 9));
//		stops.add(new BusStop(3, "direccion norte 13 14", 39.19765, -0.12578, 8));
//		stops.add(new BusStop(4, "direccion norte 13 14", 39.29765, -0.12578, 7));
//		stops.add(new BusStop(5, "direccion norte 13 14", 39.39765, -0.12578, 6));
//		stops.add(new BusStop(6, "direccion norte 13 14", 39.49765, -0.12578, 5));
//		
//		
//		JSONArray stopsArray = JSONSerializer.serializeToArray(stops);
//		
//		System.out.println("Deserialized array: ");
//		System.out.println(stopsArray.toPrettyString());
//		System.out.println("again: ");
//		System.out.println(JSONSerializer.serializeToArray(
//				JSONSerializer.deserializeFromArray(stopsArray, BusStop.class)).toPrettyString());
//		
//		
//		System.out.println("----------------------------------------------");
//		
//		
//		BusStop stop = new BusStop(2, "direccion norte 13 14", 39.89765, -0.12578, 9);
//		
//		JSONObject stopJson = JSONSerializer.serialize(stop);
//		
//		System.out.println(stopJson.toString());
//		
//		BusStop deserialized = JSONSerializer.deserialize(stopJson, BusStop.class);
//		
//		System.out.println("Deserialized bus stop: ");
//		System.out.println("ID: " + deserialized.getStopId());
//		System.out.println("Direction: " + deserialized.getDirection());
//		System.out.println("Lat: " + deserialized.getLat());
//		System.out.println("Lng: " + deserialized.getLng());
//		System.out.println("routeId: " + deserialized.getRouteId());
		
	}

}
