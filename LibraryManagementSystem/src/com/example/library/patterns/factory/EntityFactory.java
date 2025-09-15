package com.example.library.patterns.factory;

import com.example.library.model.*;

public class EntityFactory {
	public static Book createBook(String isbn, String t, String a, int y) {
		return new Book(isbn, t, a, y);
	}

	public static Patron createPatron(String id, String n, String e) {
		return new Patron(id, n, e);
	}
}