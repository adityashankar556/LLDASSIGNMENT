package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Patron;
import com.example.library.patterns.factory.EntityFactory;
import com.example.library.patterns.strategy.SimpleRecommendationStrategy;
import com.example.library.repository.BookRepository;
import com.example.library.repository.InMemoryBookRepository;
import com.example.library.repository.InMemoryPatronRepository;
import com.example.library.repository.PatronRepository;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final BookRepository bookRepo;
    private final PatronRepository patronRepo;
    private final InventoryService inventoryService;

    public LibraryService(BookRepository bookRepo, PatronRepository patronRepo) {
        this.bookRepo = bookRepo;
        this.patronRepo = patronRepo;
        this.inventoryService = new InventoryService(bookRepo, patronRepo);
        this.inventoryService.setRecommendationStrategy(new SimpleRecommendationStrategy());
    }

    // Factory-backed convenience methods
    public void addBook(String isbn, String title, String author, int year) {
        Book b = EntityFactory.createBook(isbn, title, author, year);
        bookRepo.add(b);
    }

    public void addPatron(String id, String name, String email) {
        Patron p = EntityFactory.createPatron(id, name, email);
        patronRepo.add(p);
    }

    public List<Book> searchByTitle(String title) { return bookRepo.findByTitle(title); }
    public List<Book> searchByAuthor(String author) { return bookRepo.findByAuthor(author); }

    // NEW: search by ISBN
    public Optional<Book> findByIsbn(String isbn) { return bookRepo.findByIsbn(isbn); }

    public List<Book> availableBooks() { return inventoryService.availableBooks(); }

    // Lending operations
    public boolean checkout(String isbn, String patronId, int days) { return inventoryService.checkout(isbn, patronId, days); }
    public boolean returnBook(String isbn) { return inventoryService.returnBook(isbn); }
    public void reserve(String isbn, String patronId) { inventoryService.reserve(isbn, patronId); }
    public List<Book> recommend(String patronId, int limit) { return inventoryService.recommendFor(patronId, limit); }
}
