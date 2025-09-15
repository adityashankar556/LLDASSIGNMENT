package com.example.library.service;

import com.example.library.model.*;
import com.example.library.patterns.factory.EntityFactory;
import com.example.library.patterns.strategy.SimpleRecommendationStrategy;
import com.example.library.repository.*;
import java.util.*;

public class LibraryService {
	private final BookRepository bookRepo;
	private final PatronRepository patronRepo;
	private final InventoryService inv;

	private LibraryService() {
		this.bookRepo = new InMemoryBookRepository();
		this.patronRepo = new InMemoryPatronRepository();
		this.inv = new InventoryService(bookRepo, patronRepo);
		this.inv.setRecommendationStrategy(new SimpleRecommendationStrategy());
	}

	private static class Holder {
		private static final LibraryService INST = new LibraryService();
	}

	public static LibraryService getInstance() {
		return Holder.INST;
	}

	public void addBook(String i, String t, String a, int y) {
		bookRepo.addBook(EntityFactory.createBook(i, t, a, y));
	}

	public void addPatron(String id, String n, String e) {
		patronRepo.addPatron(EntityFactory.createPatron(id, n, e));
	}

	public boolean checkout(String isbn, String pid, int d) {
		return inv.checkout(isbn, pid, d);
	}

	public boolean returnBook(String isbn) {
		return inv.returnBook(isbn);
	}

	public void reserve(String isbn, String pid) {
		inv.reserve(isbn, pid);
	}

	public List<Book> recommend(String pid, int lim) {
		return inv.recommendFor(pid, lim);
	}

	public List<Book> searchByTitle(String t) {
		return bookRepo.findByTitle(t);
	}
}