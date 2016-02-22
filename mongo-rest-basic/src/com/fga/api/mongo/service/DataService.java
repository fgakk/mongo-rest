package com.fga.api.mongo.service;

import com.fga.api.mongo.dao.MongoDAO;
import com.fga.api.mongo.dao.MongoDAOImpl;
import com.fga.api.mongo.exception.APIException;

public class DataService {

	private MongoDAO mongoDAO;
	private String subContext;
	
	public DataService(String context, String subContext){
		
		mongoDAO = new MongoDAOImpl(context);
		this.subContext = subContext;
	}
	
	public boolean insert(String data) throws APIException{
		
		return mongoDAO.insert(subContext, data);
	}
	
	public String getAll()  {
		
		String response = null;
		try {
			response = mongoDAO.get(subContext, 0);
		} catch (APIException e) {
			//TODO better handling on DAO part
			//Exception will not happen can be ignored
		}
		
		return response;
	}
	
	public String get(String query, String sort, int limit)  {
		
		String result;
		if (sort != null){
			result = mongoDAO.get(subContext, query, sort, limit);
		}
		else{
			result = mongoDAO.get(subContext, query);
		}
		return  result;
	
	}
	
	public boolean executeMapReduce(String map, String reduce, String output, String query, String type) throws APIException{
		
		return mongoDAO.mapReduce(subContext, map, reduce, output, query, type);
	}
	
	 
}
