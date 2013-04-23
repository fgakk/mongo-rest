package com.fga.api.mongo.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.util.JSON;

public class JSONUtil extends JSON{
	
	/**
	 * Check whether a string value is in a JSONObject or JSONArray format to be parsed
	 * @param value
	 * @return
	 */
	public static boolean checkJSONType(String value){
		
		boolean status = false;
		//Check for JSONObject
		try {
			new JSONObject(value);
			status = true;
		} catch (JSONException e) {
			//Second check for JSONArray
			try {
				new JSONArray(value);
				status = true;
			} catch (JSONException e1) {
				
			}
		}
		
		return status;
	}

}
