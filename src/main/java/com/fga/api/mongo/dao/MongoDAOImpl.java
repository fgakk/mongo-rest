package com.fga.api.mongo.dao;

import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fga.api.mongo.APIConstants;
import com.fga.api.mongo.exception.APIException;
import com.fga.api.mongo.util.MongoClientFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;


public class MongoDAOImpl implements MongoDAO{


	private static final Logger logger = Logger.getLogger(MongoDAOImpl.class.getName());
	private MongoClientFactory factory = MongoClientFactory.getInstance();
	private DB database;
	
	public MongoDAOImpl(String database) {
		
		this.database = getDatabase(database);
	}
	
	@Override
	public boolean insert(String collectionName, String data) throws APIException {
		
		boolean status = false;
		DBCollection collection = database.getCollection(collectionName);
		
		WriteResult result = collection.insert((DBObject)JSON.parse(data));
		
		if (result != null){
			
			if (result.getError() == null){
				status = true;
			}
		}
		
		return status;
		
	}

	@Override
	public boolean delete() {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update() {
		
		throw new UnsupportedOperationException();
	}


	@Override
	public String get() {
		
		throw new UnsupportedOperationException();
		
	}

	@Override
	public String query(String... queryParams) {
		
		throw new UnsupportedOperationException();
	}
	
	
	private DB getDatabase(String database){
		
		return factory.getMongoClient().getDB(database);
	}

	
	@Override
	public String get(String collectionName, int limit) throws APIException {
		
		JSONArray result = new JSONArray();
		DBCollection collection = database.getCollection(collectionName);
		DBCursor cursor;
		if (limit == 0){
			cursor = collection.find();
		}else if (limit > 0){
			cursor = collection.find().limit(limit);
		}else{
			throw new APIException("Limit was not given as parameter");
		}
		
		try{
			while (cursor.hasNext()){
				DBObject db = cursor.next();
				result.put(new JSONObject(db.toString()));
			}
		
		} catch (JSONException e) {
			logger.severe("Error in the data");
		}finally{
			cursor.close();
		}
		
		
		return result.toString();
	}

}
