import es.ed0.tinyjson.serialization.Ignore;
import es.ed0.tinyjson.serialization.Key;

public class Route {

	@Ignore
	public static final int PICK_UP = 0, DROP_OFF = 1;
	
	public int routeId, pd, bus;
	@Key("name")
	public String name;

	public Route() {}

	public Route(int routeId, String name, int pd, int bus) {
		this.routeId = routeId;
		this.name = name;
		this.pd = pd;
		this.bus = bus;
	}

	
}
