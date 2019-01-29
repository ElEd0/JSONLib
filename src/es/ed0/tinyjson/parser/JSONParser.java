/**
 * Created by Edo in 18 oct. 2017
 */
package es.ed0.tinyjson.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONEntity;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;

/**
 * Utility class for parsing JSONObject and JSONArray from string or files
 */
public class JSONParser {

	/**
	 * Retrieves a JSONParser with default configuration
	 * @return
	 */
	public static JSONParser get() {
		return get(new ParseConfiguration());
	}
	
	/**
	 * Retrieves a JSONParser with the given configurations
	 * @param config
	 * @return
	 */
	public static JSONParser get(ParseConfiguration config) {
		return new JSONParser(config);
	}
	
	private ParseConfiguration config;
	
	private JSONParser(ParseConfiguration config) {
		this.config = config;
	}
	
	/**
	 * Creates a JSONObject from the given list of key-pair values
	 * @param mappings
	 * @return
	 */
	public JSONObject parseJSONObject(Map<String, ? extends Object> mappings) {
		final JSONObject o = new JSONObject();
		o.putAll(mappings);
		return o;
	}
	
	/**
	 * Creates a JSONArray from teh given list of objects
	 * @param list
	 * @return
	 */
	public JSONArray parseJSONArray(List<? extends Object> list) {
		final JSONArray a = new JSONArray();
		a.addAll(list);
		return a;
	}
	/**
	 * Parse the JSON in the given String
	 * @param raw raw json
	 * @return new JSONObject
	 * @throws JSONException if parsing failed. This could happen by a badly formed json or if the parser detects an
	 * incompatibility with the current ParseConfiguration
	 */
	public JSONObject parseJSONObject(String raw) throws JSONException {
		return new Lexer(0, raw, this.config).parseObj();
	}

	/**
	 * Parse the JSON in the given String
	 * @param raw raw json
	 * @return new JSONArray
	 * @throws JSONException if parsing failed. This could happen by a badly formed json or if the parser detects an
	 * incompatibility with the current ParseConfiguration
	 */
	public JSONArray parseJSONArray(String raw) throws JSONException {
		return new Lexer(0, raw, this.config).parseArr();
	}

	/**
	 * Parse the JSON saved as plain text in the given file
	 * @param raw raw json
	 * @return new JSONObject
	 * @throws JSONException if parsing failed. This could happen by a badly formed json or if the parser detects an
	 * incompatibility with the current ParseConfiguration
	 */
	public JSONObject parseJSONObjectFromFile(String filePath) throws JSONException {
		return parseJSONObject(readFile(filePath));
	}

	/**
	 * Parse the JSON saved as plain text in the given file
	 * @param raw raw json
	 * @return new JSONArray
	 * @throws JSONException if parsing failed. This could happen by a badly formed json or if the parser detects an
	 * incompatibility with the current ParseConfiguration
	 */
	public JSONArray parseJSONArrayFromFile(String filePath) throws JSONException {
		return parseJSONArray(readFile(filePath));
	}
	
	/**
	 * Retrieves the JSONObject from the given URL, using the given request method and the request headers
	 * stored as key-value pairs inside the given JSONObject
	 * @param url Url to access
	 * @param method Request method eg: POST, GET, etc. If null GET will be used
	 * @param headers request headers, stored as key-value pairs. If null no headers will be sent
	 * @return The parsed JSONObject
	 * @throws JSONException If the json string cannot be retrieved from the URL or if the parsing of the json failed
	 */
	public JSONObject parseJSONObjectFromUrl(URL url, String method, JSONObject headers) throws JSONException {
		return parseJSONObject(readUrl(url, method, headers));
	}

	/**
	 * Retrieves the JSONArray from the given URL, using the given request method and the request headers
	 * stored as key-value pairs inside the given JSONObject
	 * @param url Url to access
	 * @param method Request method eg: POST, GET, etc. If null GET will be used
	 * @param headers request headers, stored as key-value pairs. If null no headers will be sent
	 * @return The parsed JSONArray
	 * @throws JSONException If the json string cannot be retrieved from the URL or if the parsing of the json failed
	 */
	public JSONArray parseJSONArrayFromUrl(URL url, String method, JSONObject headers) throws JSONException {
		return parseJSONArray(readUrl(url, method, headers));
	}
	
	private static String readUrl(URL url, String method, JSONObject headers) throws JSONException {
        if(url == null)
        	return null;
        if(method == null)
        	method = "GET";
        try {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            if(headers != null)
            	for(Map.Entry<String, Object> e : headers.entrySet())
            		connection.setRequestProperty(e.getKey(), e.getValue().toString());

            connection.connect();

            final StringBuilder result = new StringBuilder();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
                result.append(line);
            
            return result.toString();        	
        } catch (IOException e) {
        	throw new JSONException(e);
        }
	}
	
	private static String readFile(String path) throws JSONException {
		StringBuilder sb = new StringBuilder();
		try {
			final FileReader fr = new FileReader(new File(path));
			int c = 0;
			while((c = fr.read()) != -1)
				sb.append((char) c);
			fr.close();
		} catch (IOException e) {
			throw new JSONException(e);
		}
		return sb.toString();
	}
	/**
	 * Returns the value to write in a json for the given Object, Note this will add any necessary quotes
	 * @param obj Object to convert to safe String
	 * @return the String representation
	 */
	public static String getJsonStringValueForObject(Object obj) {
		if(obj == null)
			return "null";
		else if(obj instanceof Boolean || obj instanceof Integer ||
				obj instanceof Float || obj instanceof Double ||
				obj instanceof Long || obj instanceof JSONEntity)
			return obj.toString();
		else
			return "\"" + escape(obj.toString()) + "\"";
	}

	/**
	 * Unescapes detected escapes into its counterpart<br>
	 * I.e: "this is an\\n safe\" str\"ing blabla" to "this is an\n unsafe" str"ing blabla"
	 * @param escaped escaped String
	 * @return unescaped
	 */
	public static String unescape(String escaped) {
		return escaped
				.replace("\\" + ((char) C.quote), "" + (char) C.quote)
				.replace("\\" + ((char) C.backspace), "" + (char) C.escape_backspace)
				.replace("\\" + ((char) C.carriage), "" + (char) C.escape_carriage)
				.replace("\\" + ((char) C.formfeed), "" + (char) C.escape_formfeed)
				.replace("\\" + ((char) C.newline), "" + (char) C.escape_newline)
				.replace("\\" + ((char) C.tab), "" + (char) C.escape_tab)
				.replace("\\" + ((char) C.solidus), "" + (char) C.escape_solidus);
	}

	/**
	 * Escapes a value into a safe for sending and parsing string.<br>
	 * I.e: "this is an\n unsafe" str"ing blabla" to "this is an\\n unsafe\" str\"ing blabla"
	 * @param unescaped unescaped String
	 * @return escaped
	 */
	public static String escape(String unescaped) {
		return unescaped
				.replace("" + ((char) C.escape_solidus), "\\" + (char) C.solidus)
				.replace("" + ((char) C.quote), "\\" + (char) C.quote)
				.replace("" + ((char) C.escape_backspace), "\\" + (char) C.backspace)
				.replace("" + ((char) C.escape_carriage), "\\" + (char) C.carriage)
				.replace("" + ((char) C.escape_formfeed), "\\" + (char) C.formfeed)
				.replace("" + ((char) C.escape_newline), "\\" + (char) C.newline)
				.replace("" + ((char) C.escape_tab), "\\" + (char) C.tab);
	}
	
	/**
	 * Returns the given raw value in its number form or null if the raw is an invalid number
	 * @param raw raw number as string
	 * @return Integer Long or Double representation of the string
	 */
	public static Number parseNumber(String raw) {
		try { // is number
			return Integer.parseInt(raw);
		} catch (NumberFormatException e) {
			try {
				return Long.parseLong(raw);
			} catch (NumberFormatException e2) {
				try {
					return Double.parseDouble(raw);
				} catch (NumberFormatException e3) {
					return null;
				}
			}
		}
	}
	
}
