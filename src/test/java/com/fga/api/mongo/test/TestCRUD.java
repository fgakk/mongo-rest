package com.fga.api.mongo.test;

import static org.junit.Assert.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.fga.api.mongo.dao.MongoDAO;
import com.fga.api.mongo.dao.MongoDAOImpl;
import com.fga.api.mongo.exception.APIException;

public class TestCRUD {

	private static final String COLLECTION_NAME = "testCollection";
	private MongoDAO dao = new MongoDAOImpl("test");
	
	@Test
	public void insertBasic() throws JSONException{
		
		JSONObject obj = new JSONObject();
		obj.put("key1", "value1");
		obj.put("key2", true);
		obj.put("key3", 15);
		
		try {
			assertTrue(dao.insert(COLLECTION_NAME, obj.toString()));
		
		} catch (APIException e) {
			fail("Failed to insert record");
		}
			
	
	
	}
	
	
	@Test
	public void getAll(){
		try {
			
			dao.get(COLLECTION_NAME,0);
			
		} catch (APIException e) {
			fail("Failed to get record");
		}
		
	}
	
	@Test
	public void insertComplex() throws JSONException{
		
		JSONObject obj = new JSONObject();
		obj.put("key1", "value1");
		obj.put("key2", true);
		obj.put("key3", 15);
		
		JSONArray arr = new JSONArray();
		JSONObject innerObj = new JSONObject();
		innerObj.put("key1", "test1");
		
		arr.put(innerObj);
		
		obj.put("key4", arr);
		JSONObject obj2 = new JSONObject();
		obj2.put("key1", "value1");
		obj.put("key5", obj2);
		
		try {
			assertTrue(dao.insert(COLLECTION_NAME, obj.toString()));
		
		} catch (APIException e) {
			fail("Failed to insert record");
		}
	}
	
	@Test
	public void get(){
		
		
		try {
		  dao.get(COLLECTION_NAME,25);
			
		} catch (APIException e) {
			fail("Failed to get record");
		}
		
	}
}
