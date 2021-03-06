package com.server.chirp.storage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.server.chirp.model.User;
import com.server.chirp.storage.UserStorage;
import com.server.chirp.util.StorageException;
import com.server.chirp.util.UserAppException;

public class InMemoryUserStorage implements UserStorage{
	
	Map<String, User> users;
	
	public InMemoryUserStorage() {
		users = new HashMap<>();
	}

	@Override
	public User[] getUsers() throws StorageException {
		ArrayList<User> result = new ArrayList<User>(users.values());
		User[] returner = new User[result.size()];
		for(int i = 0; i < result.size(); i++)
			returner[i] = result.get(i);
		return returner;
	}

	@Override
	public User findUserById(String id) throws StorageException {
		return users.get(id);
	}


	@Override
	public User findUserByEmail(String email) throws StorageException {
		for(User u : users.values())
			if(u.getEmail().equals(email))
				return u;
		return null;
	}

	@Override
	public User findUserByHandle(String handle) throws StorageException {
		for(User u : users.values())
			if(u.getHandle().equals(handle))
				return u;
		return null;
	}

	@Override
	public void addUser(User user) throws StorageException {
		users.put(user.getId() + "", user);
	}

	@Override
	public void updateUser(String id, String email, String handle) throws StorageException {
		users.get(id).setEmail(email);
		users.get(id).setHandle(handle);
	}
	
	@Override
	public void updatePassword(String id, String password) {
		users.get(id).setPassword(password);
	}

	@Override
	public void deleteUser(String id) throws StorageException {
		users.remove(id);
	}

	@Override
	public ArrayList<Long> getWatchList(String userId) throws UserAppException {
		return users.get(userId).getWatchlist();
	}

	@Override
	public void addUserToWatchlist(String watcherId, String watchedId) throws UserAppException {
		User user = findUserById(watcherId);
		user.addToWatchlist(Long.parseLong(watchedId));
	}
	
	

}
