package com.fga.api.mongo.dao;

import com.fga.api.mongo.exception.APIException;

public interface MongoDAO {

	public boolean insert(String collectionName, String data) throws APIException;
	
	public boolean delete();
	
	public boolean update();
	
	public String get(String collectionName, String query);
	
	public String get(String collectionName, int limit) throws APIException;
	
	public String query(String... queryParams);
	
	public String group(String collectionNAme, String keyFields, String reduce) throws APIException;
	
	public boolean mapReduce(String collectionName, String map, String reduce, String out, String query, String type) throws APIException;

	public String get(String collectionName, String query, String sort, int limit);
}
