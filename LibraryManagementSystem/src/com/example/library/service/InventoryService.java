package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Patron;
import com.example.library.model.Reservation;
import com.example.library.patterns.observer.Observable;
import com.example.library.patterns.strategy.RecommendationStrategy;
import com.example.library.repository.BookRepository;
import com.example.library.repository.PatronRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InventoryService {
	private final BookRepository bookRepo;
	private final PatronRepository patronRepo;
	private final Map<String, Loan> activeLoans = new ConcurrentHashMap<>();
	private final Map<String, List<Reservation>> reservations = new ConcurrentHashMap<>();
	private final Observable notifier = new Observable();
	private final Logger logger = Logger.getLogger(InventoryService.class.getName());
	private RecommendationStrategy recommendationStrategy;

	public InventoryService(BookRepository bookRepo, PatronRepository patronRepo) {
		this.bookRepo = bookRepo;
		this.patronRepo = patronRepo;
	}

	// Inventory CRUD
	public void addBook(Book b) {
		bookRepo.add(b);
	}

	public void removeBook(String isbn) {
		bookRepo.remove(isbn);
	}

	public void updateBook(Book b) {
		bookRepo.update(b);
	}

	public Optional<Book> findBookByIsbn(String isbn) {
		return bookRepo.findByIsbn(isbn);
	}

	public List<Book> findByTitle(String title) {
		return bookRepo.findByTitle(title);
	}

	public List<Book> findByAuthor(String author) {
		return bookRepo.findByAuthor(author);
	}

	public List<Book> availableBooks() {
		Set<String> borrowed = activeLoans.keySet();
		return bookRepo.findAll().stream().filter(b -> !borrowed.contains(b.getIsbn())).collect(Collectors.toList());
	}

	public boolean checkout(String isbn, String patronId, int days) {
		Optional<Book> maybeBook = bookRepo.findByIsbn(isbn);
		Optional<Patron> maybePatron = patronRepo.findById(patronId);
		if (maybeBook.isEmpty() || maybePatron.isEmpty())
			return false;
		if (activeLoans.containsKey(isbn)) {
			logger.info("Checkout failed; book already borrowed: " + isbn);
			return false;
		}
		Book book = maybeBook.get();
		Patron patron = maybePatron.get();
		Loan loan = new Loan(book, patron, LocalDate.now(), LocalDate.now().plusDays(days));
		activeLoans.put(isbn, loan);
		patron.addLoan(loan);
		logger.info("Checked out: " + loan.getBook().getIsbn() + " to " + patron.getId());
		return true;
	}

	public boolean returnBook(String isbn) {
		Loan loan = activeLoans.remove(isbn);
		if (loan == null) {
			logger.warning("Return failed; loan not found for " + isbn);
			return false;
		}
		loan.setReturnedDate(LocalDate.now());
		logger.info("Book returned: " + isbn + " by " + loan.getPatron().getId());

		List<Reservation> resList = reservations.getOrDefault(isbn, Collections.emptyList());
		if (!resList.isEmpty()) {
			Reservation r = resList.remove(0);
			notifier.addObserver(r);
			notifier.notifyObservers("Book available: " + r.getBook().getTitle() + " (ISBN: " + isbn + ")");
			notifier.removeObserver(r);
		}
		return true;
	}

	public void reserve(String isbn, String patronId) {
		Optional<Book> maybeBook = bookRepo.findByIsbn(isbn);
		Optional<Patron> maybePatron = patronRepo.findById(patronId);
		if (maybeBook.isEmpty() || maybePatron.isEmpty())
			return;
		Reservation r = new Reservation(maybeBook.get(), maybePatron.get());
		reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(r);
		logger.info("Reservation placed for " + isbn + " by " + patronId);
	}

	public void setRecommendationStrategy(RecommendationStrategy strategy) {
		this.recommendationStrategy = strategy;
	}

	public List<Book> recommendFor(String patronId, int limit) {
		Optional<Patron> maybePatron = patronRepo.findById(patronId);
		if (maybePatron.isEmpty() || recommendationStrategy == null)
			return Collections.emptyList();
		List<Book> available = availableBooks();
		List<Book> recs = recommendationStrategy.recommend(maybePatron.get(), available);
		if (recs.size() > limit)
			return recs.subList(0, limit);
		return recs;
	}

	public boolean isBorrowed(String isbn) {
		return activeLoans.containsKey(isbn);
	}
}
