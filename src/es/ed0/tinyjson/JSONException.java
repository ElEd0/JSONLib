/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson;

/**
 * Exception thrown when the parsing of a JSONObject or JSONArray fails
 */
public class JSONException extends Exception {

	private static final long serialVersionUID = -1821472377122339506L;
	
	/**
	 * Creates a new Exception with the given message
	 * @param msg Exception message
	 */
	public JSONException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a new JSONException for the given exception. Useful for wrapping exceptions
	 * @param throwable Original exception
	 */
	public JSONException(Throwable throwable) {
		this(throwable.getMessage(), throwable);
	}
	
	/**
	 * Creates a new JSONException caused by the given throwable. Useful for wrapping exceptions
	 * @param msg exception message
	 * @param throwable cause of exception
	 */
	public JSONException(String msg, Throwable throwable) {
		super(msg + ". Caused by " + throwable.getClass().getName() + ": " + throwable.getLocalizedMessage());
	}
	
	/**
	 * Creates a new Exception for a json parsing error in the given position
	 * @param position json char position in which parsing failed
	 * @param msg exception message
	 */
	public JSONException(int position, String msg) {
		this(position, msg, "");
	}
	/**
	 * Creates a new Exception for a json parsing error in the given position 
	 * and concatenates the given suggestion for fixing the parsing error
	 * @param position json char position in which parsing failed
	 * @param msg exception message
	 * @param suggestion Fixing suggestion
	 */
	public JSONException(int position, String msg, String suggestion) {
		super("Error parsing at position " + position + ": " + msg + " " + suggestion);
	}
	
	public JSONException() {
		super();
	}

}
