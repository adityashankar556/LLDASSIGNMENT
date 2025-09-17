package com.example.library.repository;

import com.example.library.model.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> storage = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(InMemoryBookRepository.class.getName());

    @Override
    public void add(Book book) {
        storage.put(book.getIsbn(), book);
        logger.info("Book added: " + book);
    }

    @Override
    public void remove(String isbn) {
        Book removed = storage.remove(isbn);
        logger.info("Book removed: " + removed);
    }

    @Override
    public void update(Book book) {
        storage.put(book.getIsbn(), book);
        logger.info("Book updated: " + book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) { return Optional.ofNullable(storage.get(isbn)); }

    @Override
    public List<Book> findByTitle(String title) {
        return storage.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return storage.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() { return new ArrayList<>(storage.values()); }
}
