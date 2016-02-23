package com.fga.api.mongo.util;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

public enum MongoClientWrapper {

    INSTANCE;

    private MongoClient mongoClient;

    MongoClientWrapper(){
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add(new ServerAddress("127.0.0.1", 27017));
        mongoClient = new MongoClient(seeds);
    }

    public MongoClient getMongoClient() {
        return this.mongoClient;
    }

}
