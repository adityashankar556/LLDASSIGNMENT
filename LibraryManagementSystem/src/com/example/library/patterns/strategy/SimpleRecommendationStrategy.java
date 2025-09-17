package com.example.library.patterns.strategy;

import com.example.library.model.Book;
import com.example.library.model.Patron;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleRecommendationStrategy implements RecommendationStrategy {
	@Override
	public List<Book> recommend(Patron patron, List<Book> availableBooks) {
		Map<String, Long> authorCount = patron.getLoans().stream().map(loan -> loan.getBook().getAuthor())
				.collect(Collectors.groupingBy(a -> a, Collectors.counting()));

		List<String> favoriteAuthors = authorCount.entrySet().stream()
				.sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())).map(Map.Entry::getKey)
				.collect(Collectors.toList());

		List<Book> recommendations = new ArrayList<>();
		for (String author : favoriteAuthors) {
			for (Book b : availableBooks) {
				if (b.getAuthor().equalsIgnoreCase(author) && !recommendations.contains(b)) {
					recommendations.add(b);
				}
			}
		}

		for (Book b : availableBooks) {
			if (recommendations.size() >= 5)
				break;
			if (!recommendations.contains(b))
				recommendations.add(b);
		}
		return recommendations;
	}
}
