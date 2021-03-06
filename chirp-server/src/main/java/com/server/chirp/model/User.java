package com.server.chirp.model;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;

public class User {
	private String email;
	private String password;
	private String handle;
	private long id;
	private ArrayList<Long> watchlist; 
	
	//default constructor, needed by GSON
	private User(){}
	
	public User(String email, String password, String handle, long id) {
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.id = id;
		watchlist = new ArrayList<>();
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getHandle() {
		return handle;
	}
	
	public long getId() {
		return id;
	}
	
	public ArrayList<Long> getWatchlist() {
		List<Long> listCopy = new ArrayList<>();
		for(long id : watchlist)
			listCopy.add(id);
		return watchlist;
	}
	
	public void addToWatchlist(long id) {
		watchlist.add(id);
	}
	
	public static User fromItem(Item item) {
		return new User(item.getString("email"),
				item.getString("password"),
				item.getString("handle"),
				Long.parseLong(item.getString("id")));
	}
	
	public void fillItem(Item item) {
		item.withPrimaryKey("id", getId() + "")
		.withString("email", getEmail())
		.withString("password", getPassword())
		.withString("handle", getHandle());
	}
	
}
