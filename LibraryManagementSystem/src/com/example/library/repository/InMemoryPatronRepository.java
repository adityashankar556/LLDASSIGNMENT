package com.example.library.repository;

import com.example.library.model.Patron;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPatronRepository implements PatronRepository {
	private final Map<String, Patron> storage = new ConcurrentHashMap<>();

	public void addPatron(Patron p) {
		storage.put(p.getId(), p);
	}

	public Optional<Patron> findById(String id) {
		return Optional.ofNullable(storage.get(id));
	}

	public void updatePatron(Patron p) {
		storage.put(p.getId(), p);
	}

	public List<Patron> findAll() {
		return new ArrayList<>(storage.values());
	}
}