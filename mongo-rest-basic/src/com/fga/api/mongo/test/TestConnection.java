package com.fga.api.mongo.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fga.api.mongo.util.MongoClientFactory;
import com.mongodb.DB;
import com.mongodb.Mongo;


public class TestConnection {

	@Test
	public void getConnection() {

		MongoClientFactory factory = MongoClientFactory.INSTANCE;
		Mongo client = factory.getMongoClient();
		DB db = client.getDB("testDB");

		assertNotNull(db);

	}

}
