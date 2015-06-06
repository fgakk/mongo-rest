package com.fga.api.mongo.util;

import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;

public final class MapReduceUtil {
	

	/**
	 * @param inputCollection
	 * @param map
	 * @param reduce
	 * @param outputCollection
	 * @param query
	 * @return
	 * @throws JSONException
	 */
	public static MapReduceOutput executeMapReduce(DBCollection inputCollection, String map, String reduce, String outputCollection, String query, String type) throws JSONException{
		
		DBObject dbQuery = null;
		if (query != null){
			JSONObject queryObj = new JSONObject(query);
			dbQuery = new BasicDBObject();
			Iterator<String> iter = queryObj.keys();
			while (iter.hasNext()){
				String key = iter.next();
				if (JSONUtil.checkJSONType(queryObj.getString(key))){
					dbQuery.put(key, JSONUtil.parse(queryObj.getString(key)));
				}else{
					dbQuery.put(key, queryObj.get(key));
				}
				
			}
		}
		
		
		OutputType mtype;
		
		
		if (outputCollection == null || outputCollection.isEmpty()){
			mtype = OutputType.INLINE;
		}else if (type.equals("merge")){
			mtype = OutputType.MERGE;
		}else if (type.equals("replace")){
			mtype = OutputType.REPLACE;
		}else{
			mtype = OutputType.INLINE;
		}
		MapReduceCommand cmd = new MapReduceCommand(inputCollection, map, reduce, outputCollection, mtype, dbQuery);
		MapReduceOutput out = inputCollection.mapReduce(cmd);
		
		
		return out;

	}
	

}
