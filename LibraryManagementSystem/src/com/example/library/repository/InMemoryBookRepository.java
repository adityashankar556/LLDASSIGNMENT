package com.example.library.repository;

import com.example.library.model.Book;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
	private final Map<String, Book> storage = new ConcurrentHashMap<>();

	public void addBook(Book b) {
		storage.put(b.getIsbn(), b);
	}

	public void removeBook(String isbn) {
		storage.remove(isbn);
	}

	public void updateBook(Book b) {
		storage.put(b.getIsbn(), b);
	}

	public Optional<Book> findByIsbn(String isbn) {
		return Optional.ofNullable(storage.get(isbn));
	}

	public List<Book> findByTitle(String t) {
		return storage.values().stream().filter(b -> b.getTitle().contains(t)).collect(Collectors.toList());
	}

	public List<Book> findByAuthor(String a) {
		return storage.values().stream().filter(b -> b.getAuthor().contains(a)).collect(Collectors.toList());
	}

	public List<Book> findAll() {
		return new ArrayList<>(storage.values());
	}
}