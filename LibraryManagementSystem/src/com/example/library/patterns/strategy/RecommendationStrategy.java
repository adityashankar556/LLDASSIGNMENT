package com.example.library.patterns.strategy;

import com.example.library.model.Book;
import com.example.library.model.Patron;

import java.util.List;

public interface RecommendationStrategy {
	List<Book> recommend(Patron patron, List<Book> availableBooks);
}
