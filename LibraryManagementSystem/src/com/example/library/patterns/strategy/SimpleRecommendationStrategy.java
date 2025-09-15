package com.example.library.patterns.strategy;

import com.example.library.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleRecommendationStrategy implements RecommendationStrategy {
	public List<Book> recommend(Patron p, List<Book> available) {
		Map<String, Long> ac = p.getLoans().stream().map(l -> l.getBook().getAuthor())
				.collect(Collectors.groupingBy(a -> a, Collectors.counting()));
		List<String> fav = ac.entrySet().stream().sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
				.map(Map.Entry::getKey).toList();
		List<Book> recs = new ArrayList<>();
		for (String a : fav) {
			for (Book b : available) {
				if (b.getAuthor().equalsIgnoreCase(a) && !recs.contains(b))
					recs.add(b);
			}
		}
		for (Book b : available) {
			if (recs.size() >= 5)
				break;
			if (!recs.contains(b))
				recs.add(b);
		}
		return recs;
	}
}