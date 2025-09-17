package com.example.library.repository;

import com.example.library.model.Patron;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class InMemoryPatronRepository implements PatronRepository {
    private final Map<String, Patron> storage = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(InMemoryPatronRepository.class.getName());

    @Override
    public void add(Patron patron) {
        storage.put(patron.getId(), patron);
        logger.info("Patron added: " + patron);
    }

    @Override
    public Optional<Patron> findById(String id) { return Optional.ofNullable(storage.get(id)); }

    @Override
    public void update(Patron patron) {
        storage.put(patron.getId(), patron);
        logger.info("Patron updated: " + patron);
    }

    @Override
    public List<Patron> findAll() { return new ArrayList<>(storage.values()); }
}
