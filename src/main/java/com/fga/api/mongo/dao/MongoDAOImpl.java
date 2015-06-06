package com.fga.api.mongo.dao;

import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fga.api.mongo.exception.APIException;
import com.fga.api.mongo.util.JSONUtil;
import com.fga.api.mongo.util.MapReduceUtil;
import com.fga.api.mongo.util.MongoClientFactory;
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
	public String get(String collectionName, String query) {
		
		JSONArray result = new JSONArray();
		DBCollection collection = database.getCollection(collectionName);
		DBCursor cursor;
		if (query != null){
			DBObject queryObj = (DBObject) JSONUtil.parse(query);
			cursor = collection.find(queryObj);
		}else{
			cursor = collection.find();
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
	
	@Override
	public String get(String collectionName, String query, String sort, int limit) {
		
		JSONArray result = new JSONArray();
		DBCollection collection = database.getCollection(collectionName);
		DBCursor cursor;
		if (query != null && sort != null){
			DBObject queryObj = (DBObject) JSONUtil.parse(query);
			DBObject sortObj = (DBObject) JSONUtil.parse(sort);
			cursor = collection.find(queryObj).sort(sortObj).limit(limit);
		}else if(sort != null){
			DBObject sortObj = (DBObject) JSONUtil.parse(sort);
			cursor = collection.find().sort(sortObj).limit(limit);
		}else{
			cursor = collection.find().limit(limit);
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

	@Override
	public boolean mapReduce(String collectionName, String map, String reduce, String outputCollection, String query, String type) throws APIException {
		
		boolean result = false;
		DBCollection inputCollection = database.getCollection(collectionName);
		
		try{
			MapReduceUtil.executeMapReduce(inputCollection, map, reduce, outputCollection, query, type);
			result = true;
		}catch(Exception e){
			logger.severe(e.getMessage());
			throw new APIException("Error while processing data", e);
		}
		
		return result;
		

	}

	@Override
	public String group(String collectionName, String keyFields, String reduce) throws APIException {
		
		throw new UnsupportedOperationException();
	}

}
