package com.fga.api.mongo.exception;

public class APIException extends Exception{


	public APIException(String msg, Throwable e){
		super(msg, e);
	}

	public APIException(String msg) {
		super(msg);
	}
}
