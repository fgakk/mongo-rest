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
	 
}
