package com.example.library.repository;

import com.example.library.model.Book;
import java.util.*;

public interface BookRepository {
	void addBook(Book b);

	void removeBook(String isbn);

	void updateBook(Book b);

	Optional<Book> findByIsbn(String isbn);

	List<Book> findByTitle(String t);

	List<Book> findByAuthor(String a);

	List<Book> findAll();
}