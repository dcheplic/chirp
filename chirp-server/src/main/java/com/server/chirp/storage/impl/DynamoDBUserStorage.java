package com.server.chirp.storage.impl;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.server.chirp.model.User;
import com.server.chirp.storage.UserStorage;
import com.server.chirp.util.DynamoDBConnectionManager;
import com.server.chirp.util.StorageException;

public class DynamoDBUserStorage implements UserStorage{

	private DynamoDB getDB() {
		return new DynamoDBConnectionManager().get().getDB();
	}

	private Table getTable(DynamoDB database) {
		if(database.getTable("users") == null)
			return null;
		return database.getTable("users");
	}

	@Override
	public List<User> getUsers() throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		ArrayList<User> result = new ArrayList<User>();
		for(Item item : collection)
			result.add(User.fromItem(item));
		return result;
	}

	@Override
	public User findUserById(String id) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		User user = null;
		for(Item item : collection)
			if(User.fromItem(item).getId().toString() == id)
				user = User.fromItem(item);
		return user;
	}

	@Override
	public List<User> findUserByName(String name) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		ArrayList<User> result = new ArrayList<User>();
		for(Item item : collection) {
			if(User.fromItem(item).getName() == name)
				result.add(User.fromItem(item));
		}
		return result;
	}

	@Override
	public User findUserByEmail(String email) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		User user = null;
		for(Item item : collection)
			if(User.fromItem(item).getEmail() == email)
				user = User.fromItem(item);
		return user;
	}

	@Override
	public User findUserByHandle(String handle) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		User user = null;
		for(Item item : collection)
			if(User.fromItem(item).getHandle() == handle)
				user = User.fromItem(item);
		return user;
	}

	@Override
	public void addUser(User u) throws StorageException {
		Table table = getTable(getDB());
		Item item = new Item();
		u.fillItem(item);
		table.putItem(item);
	}

	@Override
	public void updateUser(String id, String name, String email, String handle) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		User user = null;
		for(Item item : collection) {
			if(User.fromItem(item).getId().toString() == id) {
				user = User.fromItem(item);
				user.setName(name);
				user.setEmail(email);
				user.setHandle(handle);
			}
		}
		Item item = new Item();
		user.fillItem(item);
		table.putItem(item);
	}

	@Override
	public void updatePassword(String id, String password) {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		User user = null;
		for(Item item : collection) {
			if(User.fromItem(item).getId().toString() == id) {
				user = User.fromItem(item);
				user.setPassword(password);
			}
		}
		Item item = new Item();
		user.fillItem(item);
		table.putItem(item);
	}

	@Override
	public void deleteUser(String id) throws StorageException {
		Table table = getTable(getDB());
		ItemCollection<ScanOutcome> collection = table.scan();
		for(Item item : collection) {
			if(User.fromItem(item).getId().toString() == id) {
				DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("id", id);
				table.deleteItem(deleteItemSpec);
			}
		}
	}

}
