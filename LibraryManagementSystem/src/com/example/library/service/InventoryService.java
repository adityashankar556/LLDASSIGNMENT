package com.example.library.service;

import com.example.library.model.*;
import com.example.library.patterns.observer.Observable;
import com.example.library.repository.*;
import com.example.library.patterns.strategy.RecommendationStrategy;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InventoryService {
	private final BookRepository bookRepo;
	private final PatronRepository patronRepo;
	private final Map<String, Loan> activeLoans = new ConcurrentHashMap<>();
	private final Map<String, List<Reservation>> reservations = new ConcurrentHashMap<>();
	private final Observable notifier = new Observable();
	private RecommendationStrategy recStrat;

	public InventoryService(BookRepository br, PatronRepository pr) {
		this.bookRepo = br;
		this.patronRepo = pr;
	}

	public void addBook(Book b) {
		bookRepo.addBook(b);
	}

	public void removeBook(String isbn) {
		bookRepo.removeBook(isbn);
	}

	public void updateBook(Book b) {
		bookRepo.updateBook(b);
	}

	public Optional<Book> findBookByIsbn(String isbn) {
		return bookRepo.findByIsbn(isbn);
	}

	public List<Book> availableBooks() {
		Set<String> borrowed = activeLoans.keySet();
		return bookRepo.findAll().stream().filter(b -> !borrowed.contains(b.getIsbn())).collect(Collectors.toList());
	}

	public boolean checkout(String isbn, String pid, int days) {
		Optional<Book> ob = bookRepo.findByIsbn(isbn);
		Optional<Patron> op = patronRepo.findById(pid);
		if (ob.isEmpty() || op.isEmpty())
			return false;
		if (activeLoans.containsKey(isbn))
			return false;
		Loan l = new Loan(ob.get(), op.get(), LocalDate.now(), LocalDate.now().plusDays(days));
		activeLoans.put(isbn, l);
		op.get().addLoan(l);
		return true;
	}

	public boolean returnBook(String isbn) {
		Loan l = activeLoans.remove(isbn);
		if (l == null)
			return false;
		l.setReturnedDate(LocalDate.now());
		List<Reservation> rl = reservations.getOrDefault(isbn, new ArrayList<>());
		if (!rl.isEmpty()) {
			Reservation r = rl.remove(0);
			notifier.addObserver(r);
			notifier.notifyObservers("Book available:" + r.getBook().getTitle());
			notifier.removeObserver(r);
		}
		return true;
	}

	public void reserve(String isbn, String pid) {
		Optional<Book> ob = bookRepo.findByIsbn(isbn);
		Optional<Patron> op = patronRepo.findById(pid);
		if (ob.isEmpty() || op.isEmpty())
			return;
		Reservation r = new Reservation(ob.get(), op.get());
		reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(r);
	}

	public void setRecommendationStrategy(RecommendationStrategy s) {
		this.recStrat = s;
	}

	public List<Book> recommendFor(String pid, int limit) {
		Optional<Patron> op = patronRepo.findById(pid);
		if (op.isEmpty() || recStrat == null)
			return Collections.emptyList();
		List<Book> recs = recStrat.recommend(op.get(), availableBooks());
		return recs.size() > limit ? recs.subList(0, limit) : recs;
	}
}