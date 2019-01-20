/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson;

public class JSONException extends Exception{

	private static final long serialVersionUID = -1821472377122339506L;
	
	public JSONException(String msg) {
		super(msg);
	}
	
	public JSONException(Throwable throwable) {
		super(throwable.getMessage());
		setStackTrace(throwable.getStackTrace());
	}
	
	public JSONException(int position, String msg) {
		this(position, msg, "");
	}
	
	public JSONException(int position, String msg, String suggestion) {
		super("Error parsing at position " + position + ": " + msg + " " + suggestion);
	}
	
	public JSONException() {
		super();
	}

}
