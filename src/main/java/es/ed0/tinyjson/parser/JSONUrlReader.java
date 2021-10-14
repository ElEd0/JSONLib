/**
 * Created by Ed0 in 30 ene. 2019
 */
package es.ed0.tinyjson.parser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;

/**
 * Class for reading and parsing json objects and arrays from a URL resource.<br>
 * Request headers or extra parameters may be included using {@link es.ed0.tinyjson.parser.JSONUrlReader#addRequestHeader(String, String)}
 * and {@link es.ed0.tinyjson.parser.JSONUrlReader#addParameter(String, String)} respectively
 */
public class JSONUrlReader {
	
	private final static String UTF8 = "UTF-8";
	
	
	private boolean encode = true;
	private String url, method;
	private HashMap<String, String> headers, parameters;
	private int connectionTimeout = 20000, readTimeout = 10000;
	
	/**
	 * Creates a new URL reader for the given URL, using GET method
	 * @param url url to request from
	 */
	public JSONUrlReader(String url) {
		this(url, "GET");
	}
	
	/**
	 * Creates a new URL reader for the given url and method
	 * @param url url to request from
	 * @param method method to use
	 */
	public JSONUrlReader(String url, String method) {
		this.url = url;
		this.method = method;
		headers = new HashMap<String, String>();
		parameters = new HashMap<String, String>();
	}


	/**
	 * Creates a connection to the url with the current configuration and returns the response as a JSONObject
	 * @return response from url
	 * @throws JSONException If the json string cannot be retrieved from the URL or if the parsing of the json failed
	 */
	public JSONObject readJSONObject() throws JSONException {
		return JSONParser.get().parseJSONObject(readString());
	}

	/**
	 * Creates a connection to the url with the current configuration and returns the response as a JSONArray
	 * @return response from url
	 * @throws JSONException If the json string cannot be retrieved from the URL or if the parsing of the json failed
	 */
	public JSONArray readJSONArray() throws JSONException {
		return JSONParser.get().parseJSONArray(readString());
	}
	
	/**
	 * Creates a connection to the url with the current configuration and returns the response as a string
	 * @return response from url
	 * @throws JSONException If connection to the url could not be established or something went wrong reading the response
	 */
	public String readString() throws JSONException {
        if (url == null)
        	return null;
        
        String params = getParameterString();
        String finalUrl = url;
        boolean doOutput = true;
        
        switch (getMethod()) {
        case "POST":
        	break;
        case "GET":
        	doOutput = false;
        	if(params.length() != 0)
        		finalUrl += "?" + params;
        	break;
        }

        try {
        	URL rUrl = new URL(finalUrl);
        	
            final HttpURLConnection connection = (HttpURLConnection) rUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(doOutput);
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            
            if (headers != null)
            	for(Map.Entry<String, String> e : headers.entrySet())
            		connection.setRequestProperty(e.getKey(), e.getValue());

            connection.connect();
            
            if (method.equals("POST")) {
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(params);
                wr.flush();
                wr.close();
            }
            

            final StringBuilder result = new StringBuilder();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
                result.append(line);
            
            connection.disconnect();
            
            return result.toString();   
            
        } catch (IOException e) {
        	throw new JSONException(e);
        }
        
	}

	/**
	 * Returns the parameters in a formated string for use in GET and POST requests.<br>
	 * Eg: <code>key=value&amp;key=value</code>
	 * @return the formatted string containing all parameters
	 * @throws JSONException
	 */
	public String getParameterString() throws JSONException {
		StringBuilder params = new StringBuilder();
        int i = 0;
        for (String key : parameters.keySet()) {
            if (i != 0)
            	params.append("&");
            String value = parameters.get(key);
            if (isEncode())
            	try {
            		value = URLEncoder.encode(value, UTF8);
            	} catch (UnsupportedEncodingException e) {
            		throw new JSONException(e);
            	}
            params.append(key).append("=").append(value);
            i++;
        }
        return params.toString();
	}
	
	/**
	 * Adds a property to the request header
	 * @param key
	 * @param value
	 * @return this URL reader
	 */
	public JSONUrlReader addRequestHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	/**
	 * Adds a parameter to send along the URL<br>
	 * Eg: When using GET as method the parameters will be included with the url like so
	 * <code>url?key1=value&amp;key2=value</code>
	 * @param key
	 * @param value
	 * @return this URL reader
	 */
	public JSONUrlReader addParameter(String key, String value) {
		parameters.put(key, value);
		return this;
	}
	
	/**
	 * Retrieves the current request method.<br>
	 * Default is GET
	 * @return current method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the request method to use. Currently only POST and GET have especific implementations. 
	 * Other methods can be used tho.
	 * @param method new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * True when the included parameters will be translated to application/x-www-form-urlencodedformat.<br>
	 * This will use UTF-8 to obtain the bytes for unsafe characters. 
	 * @return
	 */
	public boolean isEncode() { return encode; }
	/**
	 * If true the included parameters will be translated to application/x-www-form-urlencodedformat.<br>
	 * This will use UTF-8 to obtain the bytes for unsafe characters. 
	 * @param encode
	 */
	public void setEncode(boolean encode) { this.encode = encode; }
	/**
	 * The current connection timeout<br>
	 * Default is 20000
	 * @return current read Timeout
	 */
	public int getConnectionTimeout() { return connectionTimeout; }
	/**
	 * Sets the Request connection timeout, as implemented in {@link java.net.HttpURLConnection#setConnectTimeout}
	 * @param connectionTimeout
	 */
	public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
	/**
	 * The current connection read timeout<br>
	 * Default is 10000
	 * @return current read Timeout
	 */
	public int getReadTimeout() { return readTimeout; }
	/**
	 * Sets the Request connection read timeout, as implemented in {@link java.net.HttpURLConnection#setReadTimeout}
	 * @param readTimeout
	 */
	public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }
	
	
	
}
