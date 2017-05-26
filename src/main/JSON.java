package main;


import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSON {
	
	/**
	 * Method that returns a JSON object given a url
	 * 
	 * @param URL
	 * @return The JSON object parsed from the response
	 */
	public static JSONObject GET(String URL){
		try{
    		JSONObject json = new JSONObject(IOUtils.toString(new URL(URL), Charset.forName("UTF-8")));
        	return json;
    	} catch (Exception e) {
    		System.out.println("Exception while accesing URL");
    	    return null;
    	}
	}
}
