package com.fga.api.mongo.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public  class MongoClientFactory {

	private static final Logger logger = Logger.getLogger(MongoClientFactory.class.getName());
	private static volatile MongoClientFactory instance;
	private static List<ServerAddress> seeds = new ArrayList<ServerAddress>();
	
	
	static {
		try {
			seeds.add(new ServerAddress("localhost", 27017));
		} catch (UnknownHostException e) {
			logger.severe("Server connection will fail");
		}
	}
	private Mongo mongoClient;
	
	private MongoClientFactory(){
		mongoClient = new Mongo(seeds);
	}
	
	public synchronized static MongoClientFactory getInstance(){
		
		if (instance == null){
			instance = new MongoClientFactory();
				
		}
		
		return instance;
	}
	
	public Mongo getMongoClient() {
		
		if (this.mongoClient == null){
			synchronized (this) {
				if (this.mongoClient == null){
					this.mongoClient = new Mongo(seeds);
				}
			}
		}
		
		return this.mongoClient;
	}
	
}
