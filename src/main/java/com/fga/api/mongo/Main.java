package com.fga.api.mongo;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fga.api.mongo.exception.APIException;
import com.fga.api.mongo.service.DataService;

@Path("/{context}/{subcontext}")
public class Main {

	private static final String SUBCONTEXT = "subcontext";
	private static final String CONTEXT = "context";
	private static final String SUCCESS = "success";

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {

		return SUCCESS;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insert")
	public Response insert(@PathParam(CONTEXT) String context,
			@PathParam(SUBCONTEXT) String subcontext,
			@FormParam("data") String data) {

		Response response = null;
		DataService service = new DataService(context, subcontext);
		try {
			if (service.insert(data)) {

				response = Response.ok(SUCCESS).build();
			}
		} catch (APIException e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		return response;
	}

	public void update() {

	}

	public void delete() {

	}

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@PathParam(CONTEXT) String context,
			@PathParam(SUBCONTEXT) String subcontext,
			@QueryParam("query") String query, @QueryParam("sort") String sort,
			@QueryParam("limit") int limit) {

		DataService service = new DataService(context, subcontext);
		String response = service.get(query, sort, limit);

		return response;
	}

	public void query() {

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mapreduce")
	public Response mapreduce(@PathParam(CONTEXT) String context,
			@PathParam(SUBCONTEXT) String subcontext,
			@FormParam("map") String map, @FormParam("reduce") String reduce,
			@FormParam("output") String output,
			@FormParam("query") String query, @FormParam("type") String type) {

		Response response = null;
		DataService service = new DataService(context, subcontext);
		try {
			if (service.executeMapReduce(map, reduce, output, query, type)) {

				response = Response.ok(SUCCESS).build();
			}
		} catch (APIException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

		return response;
	}

}
