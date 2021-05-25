import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;
import es.ed0.tinyjson.parser.JSONParser;
import es.ed0.tinyjson.serialization.JSONSerializer;

import java.io.File;

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

        JSONObject pojo = serial.serializeToJSONObject(new Pojo());

        pojo.put("name", "jsonobject value");
        pojo.put("routes", new JSONArray()
                .add(serial.serializeToJSONObject(new Route(1, "ruta1", 7, 2)))
                .add(serial.serializeToJSONObject(new Route(2, "ruta2", 6, 3)))
                .add(serial.serializeToJSONObject(new Route(3, "ruta3", 5, 4))));

        System.out.println(pojo.toPrettyString());

        Pojo newPojo = serial.deserialize(pojo, Pojo.class);

        JSONObject deserialized = serial.serializeToJSONObject(newPojo);

        System.out.println(deserialized.toPrettyString());

        JSONParser.get().saveJSONToFile(deserialized, new File("pojo.json"));

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
