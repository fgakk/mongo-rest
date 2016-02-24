package com.fga.api.mongo.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import com.fga.api.mongo.dao.MongoDAO;
import com.fga.api.mongo.dao.MongoDAOImpl;
import com.fga.api.mongo.exception.APIException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class TestCRUD {

    private static final String COLLECTION_NAME = "testCollection";
	private static final String COLLECTION_AGG = "testCollectionAgg";
	private static final int LIMIT = 10;
    public static final String KEY_1 = "key1";
    public static final String KEY_2 = "key2";
    public static final String KEY_3 = "key3";
    public static final String VALUE_STRING = "value1";
    public static final int VALUE_INT = 15;
    public static final boolean VALUE_BOOLEAN = true;
    public static final String COUNT = "count";
    public static final String BASE_MULTI_INSERT = "{" + COUNT + ": %d}";
    private MongoDAO dao = new MongoDAOImpl("testDatabase");

	@Test(priority = 1)
	public void insertBasic() throws JSONException {

		JSONObject obj = new JSONObject();
		obj.put(KEY_1, VALUE_STRING);
		obj.put(KEY_2, VALUE_BOOLEAN);
		obj.put(KEY_3, VALUE_INT);

		try {
			assertTrue(dao.insert(COLLECTION_NAME, obj.toString()));

		} catch (APIException e) {
			fail("Failed to insert record");
		}

	}

	@Test(priority = 2)
	public void getAll() throws JSONException {
		try {

			String results = dao.get(COLLECTION_NAME, LIMIT);
            JSONArray arr = new JSONArray(results);
            JSONObject obj = arr.getJSONObject(0);
            assertEquals(obj.get(KEY_1), VALUE_STRING);
            assertEquals(obj.get(KEY_2), VALUE_BOOLEAN);
            assertEquals(obj.get(KEY_3), VALUE_INT);
		} catch (APIException e) {
			fail("Failed to get record");
		}

	}

	@Test(priority = 3)
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

	@Test(priority = 4)
	public void get() throws JSONException, APIException {

		List<String> dataList = new ArrayList<>();
		dataList.add(String.format(BASE_MULTI_INSERT, 1000));
		dataList.add(String.format(BASE_MULTI_INSERT, 2000));
		dataList.add(String.format(BASE_MULTI_INSERT, 3000));
        assertTrue(dao.insert(COLLECTION_AGG, dataList));
		JSONObject queryObj = new JSONObject();
		JSONObject queryValue = new JSONObject();
		queryValue.put("$gte", 1000);
		queryValue.put("$lte", 3000);
		queryObj.put(COUNT, queryValue);
		String results = dao.get(COLLECTION_AGG, queryObj.toString());
		JSONArray arr = new JSONArray(results);
		Assert.assertTrue(arr.length() == 3);
	}


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
