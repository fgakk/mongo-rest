package com.fga.api.mongo.dao;

import com.fga.api.mongo.exception.APIException;

public interface MongoDAO {

	public boolean insert(String collectionName, String data) throws APIException;
	
	public boolean delete();
	
	public boolean update();
	
	public String get();
	
	public String get(String collectionName, int limit) throws APIException;
	
	public String query(String... queryParams);
}
