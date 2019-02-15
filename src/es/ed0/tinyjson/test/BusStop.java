package es.ed0.tinyjson.test;

import es.ed0.tinyjson.serialization.Ignore;
import es.ed0.tinyjson.serialization.Key;

//@JsonArray
public class BusStop {

	private int stopId, routeId;
	@Key("location")
	private String direction;
	
	private double lat, lng;

	public BusStop() {
		
	}

	public BusStop(int stopId, String direction, double lat, double lng, int routeId) {
		this.stopId = stopId;
		this.direction = direction;
		this.lat = lat;
		this.lng = lng;
		this.routeId = routeId;
	}
	
	public int getStopId() {
		return stopId;
	}

	public void setStopId(int stopId) {
		this.stopId = stopId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
}
