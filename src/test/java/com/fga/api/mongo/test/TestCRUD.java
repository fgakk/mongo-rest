package com.fga.api.mongo.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fga.api.mongo.dao.MongoDAO;
import com.fga.api.mongo.dao.MongoDAOImpl;
import com.fga.api.mongo.exception.APIException;


public class TestCRUD {

	private static final String COLLECTION_NAME = "rawstats";
	private static final String BATTERY_AVG = "battery_avg";
	private MongoDAO dao = new MongoDAOImpl("smartstats");

	@Test
	public void insertBasic() throws JSONException {

		JSONObject obj = new JSONObject();
		obj.put("key1", "value1");
		obj.put("key2", true);
		obj.put("key3", 15);

		try {
			assertTrue(dao.insert(COLLECTION_NAME, obj.toString()));

		} catch (APIException e) {
			fail("Failed to insert record");
		}

	}

	@Test
	public void getAll() {
		try {

			dao.get(COLLECTION_NAME, 0);

		} catch (APIException e) {
			fail("Failed to get record");
		}

	}

	@Test
	public void insertComplex() throws JSONException {

		JSONObject obj = new JSONObject();
		obj.put("key1", "value1");
		obj.put("key2", true);
		obj.put("key3", 15);

		JSONArray arr = new JSONArray();
		JSONObject innerObj = new JSONObject();
		innerObj.put("key1", "test1");

		arr.put(innerObj);

		obj.put("key4", arr);
		JSONObject obj2 = new JSONObject();
		obj2.put("key1", "value1");
		obj.put("key5", obj2);

		try {
			assertTrue(dao.insert(COLLECTION_NAME, obj.toString()));

		} catch (APIException e) {
			fail("Failed to insert record");
		}
	}

	@Test
	public void get() throws JSONException {

		JSONObject queryObj = new JSONObject();
		JSONObject queryValue = new JSONObject();
		queryValue.put("$gte", 1368997200000L);
		queryValue.put("$lte", 1369083600000L);
		queryObj.put("_id", queryValue);
		System.out.println(dao.get(BATTERY_AVG, queryObj.toString()));

	}

	@Ignore
	public void mapReduce() {

		long start = 1368748800000L;
		long end = 1368835200000L;
		JSONObject queryObj = new JSONObject();
		JSONObject queryValue = new JSONObject();
		try {
			queryValue.put("$gte", start);
			queryValue.put("$lte", end);
			queryObj.put("date", queryValue);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String map = "function() { "
				+ "var date = new Date(this.date); "
				+ "var key = date.getDate() + ':' + (date.getMonth()+1) + ':' + date.getFullYear() + ':' + date.getHours();"
				+ "var hour = date.getHours(); "
				+ "emit(key, this.batteryLevel);}";

		String reduce = "function(key, values) { " + "var sum = 0; "
				+ "var avg = 0;" + "var size = values.length; "
				+ "for (var idx = 0; idx < size; idx++) {"
				+ "sum += values[idx];" + "}" + "avg = sum / size;" +

				"return  avg;} ";
		try {
			dao.mapReduce(COLLECTION_NAME, map, reduce, null,
					queryObj.toString(), null);
		} catch (APIException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Ignore
	public void mapReduceSecondTest() {

		String map = "function() { "
				+ "var date = new Date(this.date);"
				+ "var key = date.getDate() + ':' + (date.getMonth()+1) + ':' + date.getFullYear();"
				+ "var hour = date.getHours(); " + "emit(key, this.date);}";

		String reduce = "function(key, values) { "
				+ "var min = Math.min.apply(Math, values); "
				+ "var max = Math.max.apply(Math, values);"
				+ "var size = values.length; " + "var diff = max - min;" +

				"return  (diff/60000);} ";
		try {
			dao.mapReduce(COLLECTION_NAME, map, reduce, "mapreduceout2", null,
					null);
		} catch (APIException e) {
			fail(e.getMessage());
		}
	}

}
