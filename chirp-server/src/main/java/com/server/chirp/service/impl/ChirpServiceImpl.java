package com.server.chirp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.server.chirp.model.Chirp;
import com.server.chirp.model.User;
import com.server.chirp.service.ChirpService;
import com.server.chirp.storage.ChirpStorage;
import com.server.chirp.util.UserAppException;

public class ChirpServiceImpl implements ChirpService{
	
	private ChirpStorage storage;
	
	public ChirpServiceImpl(ChirpStorage storage) {
		this.storage = storage;
	}

	@Override
	public List<Chirp> getChirps() throws UserAppException {
		return storage.getChirps();
	}

	@Override
	public List<Chirp> findChirpsByMessage(String message) throws UserAppException {
		return storage.findChirpsByMessage(message);
	}

	@Override
	public List<Chirp> findChirpsByDate(Date date) throws UserAppException {
		return storage.findChirpsByDate(date.toString());
	}

	@Override
	public List<Chirp> findChirpsByUser(String id) throws UserAppException {
		return storage.findChirpsByUser(id);
	}

	@Override
	public void addChirp(String message, Date date, String userId) throws UserAppException {
		Chirp chirp = new Chirp(message, date, userId);
		storage.addChirp(chirp);
	}
}
