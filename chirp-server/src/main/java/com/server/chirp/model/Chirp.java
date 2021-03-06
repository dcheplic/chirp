package com.server.chirp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Chirp {
	private String message;
	private String date;
	private String userId;
	private String id;
	
	//default constructor needed for Gson
	private Chirp() {}
	
	public Chirp(String message, String date, String userId) {
		this.message = message;
		this.date = date;
		this.userId = userId;
		id = UUID.randomUUID().toString();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getId() {
		return id;
	}
	
	public static Chirp fromItem(Item item) {
		return new Chirp(item.getString("message"), item.getString("date"), item.getString("userId"));
	}
	
	public void fillItem(Item item) {
		item.withPrimaryKey("id", getId())
		.withString("message", getMessage())
		.with("date", getDate().toString())
		.with("userId", getUserId());
	}
}
