package com.example.library.app;

import com.example.library.service.LibraryService;
import com.example.library.model.Book;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		LibraryService lib = LibraryService.getInstance();
		lib.addBook("978-0134685991", "Effective Java", "Joshua Bloch", 2018);
		lib.addBook("978-0596009205", "Head First Java", "Kathy Sierra", 2005);
		lib.addBook("978-0321356680", "Java Concurrency in Practice", "Brian Goetz", 2006);
		lib.addPatron("P1", "Alice", "alice@example.com");
		lib.addPatron("P2", "Bob", "bob@example.com");
		boolean ok = lib.checkout("978-0134685991", "P1", 14);
		System.out.println("Checkout success:" + ok);
		boolean ok2 = lib.checkout("978-0134685991", "P2", 7);
		System.out.println("Checkout P2:" + ok2);
		lib.reserve("978-0134685991", "P2");
		lib.returnBook("978-0134685991");
		List<Book> recs = lib.recommend("P1", 5);
		System.out.println("Recommendations:" + recs);
		System.out.println("Search Java:" + lib.searchByTitle("Java"));
	}
}