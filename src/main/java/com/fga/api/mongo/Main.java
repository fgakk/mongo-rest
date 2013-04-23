package com.fga.api.mongo;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fga.api.mongo.exception.APIException;
import com.fga.api.mongo.service.DataService;

@Path("/{context}/{subcontext}")
public class Main {
	
	
	
	
	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		
		return "success";
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("insert")
	public Response insert(@PathParam("context")String context, @PathParam("subcontext")String subcontext, @FormParam("data")String data){
	
		Response response = null;
		DataService service = new DataService(context, subcontext);
		try {
			if (service.insert(data)){
				
				response = Response.ok("success").build();
			}
		} catch (APIException e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		
		return response;
	}
	
	public void update(){
		
	}
	
	public void delete(){
		
	}
	
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@PathParam("context")String context, @PathParam("subcontext")String subcontext){
		
		DataService service = new DataService(context, subcontext);
		String response = service.getAll();
		
		return response;
	}
	
	public void query(){
		
	}

}
