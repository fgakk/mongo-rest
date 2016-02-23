package com.fga.api.mongo.test;

import static org.testng.Assert.assertNotNull;
import com.fga.api.mongo.util.MongoClientWrapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.testng.annotations.Test;


public class TestConnection {

    @Test
    public void getConnection() {

        MongoClientWrapper mongoClientWrapper = MongoClientWrapper.INSTANCE;
        MongoClient client = mongoClientWrapper.getMongoClient();
        MongoDatabase db = client.getDatabase("testDB");

        assertNotNull(db);

    }

}
