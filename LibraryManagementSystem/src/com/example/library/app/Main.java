package com.example.library.app;

import com.example.library.model.Book;
import com.example.library.repository.InMemoryBookRepository;
import com.example.library.repository.InMemoryPatronRepository;
import com.example.library.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		var bookRepo = new InMemoryBookRepository();
		var patronRepo = new InMemoryPatronRepository();
		var lib = new LibraryService(bookRepo, patronRepo);

		seedSampleData(lib);

		Scanner sc = new Scanner(System.in);
		while (true) {
			printMenu();
			String choice = sc.nextLine().trim();
			try {
				switch (choice) {
				case "1" -> addBook(lib, sc);
				case "2" -> listBooks(lib);
				case "3" -> searchTitle(lib, sc);
				case "4" -> searchAuthor(lib, sc); // NEW
				case "5" -> searchIsbn(lib, sc); // NEW
				case "6" -> addPatron(lib, sc);
				case "7" -> checkout(lib, sc);
				case "8" -> returnBook(lib, sc);
				case "9" -> reserveBook(lib, sc);
				case "10" -> recommend(lib, sc);
				case "11" -> {
					System.out.println("Exiting...");
					sc.close();
					return;
				}
				default -> System.out.println("Unknown option");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private static void seedSampleData(LibraryService lib) {
		lib.addBook("978-0134685991", "Effective Java", "Joshua Bloch", 2018);
		lib.addBook("978-0596009205", "Head First Java", "Kathy Sierra", 2005);
		lib.addBook("978-0321356680", "Java Concurrency in Practice", "Brian Goetz", 2006);
		lib.addPatron("P1", "Alice", "alice@example.com");
		lib.addPatron("P2", "Bob", "bob@example.com");
	}

	private static void printMenu() {
		System.out.println("\n=== Library CLI ===");
		System.out.println("1. Add book");
		System.out.println("2. List all books (available only)");
		System.out.println("3. Search books by title");
		System.out.println("4. Search books by author");
		System.out.println("5. Search book by ISBN");
		System.out.println("6. Add patron");
		System.out.println("7. Checkout book");
		System.out.println("8. Return book");
		System.out.println("9. Reserve book");
		System.out.println("10. Recommend for patron");
		System.out.println("11. Exit");
		System.out.print("Choose> ");
	}

	private static void addBook(LibraryService lib, Scanner sc) {
		System.out.print("ISBN: ");
		String isbn = sc.nextLine().trim();
		System.out.print("Title: ");
		String title = sc.nextLine().trim();
		System.out.print("Author: ");
		String author = sc.nextLine().trim();
		System.out.print("Year: ");
		int year = Integer.parseInt(sc.nextLine().trim());
		lib.addBook(isbn, title, author, year);
		System.out.println("Book added.");
	}

	private static void listBooks(LibraryService lib) {
		List<Book> books = lib.availableBooks();
		System.out.println("Available books (not borrowed):");
		books.forEach(b -> System.out.println("  " + b));
	}

	private static void searchTitle(LibraryService lib, Scanner sc) {
		System.out.print("Title contains: ");
		String q = sc.nextLine().trim();
		List<Book> found = lib.searchByTitle(q);
		if (found.isEmpty()) {
			System.out.println("No books found with title containing: " + q);
		} else {
			System.out.println("Found books:");
			found.forEach(b -> System.out.println("  " + b));
		}
	}

	private static void searchAuthor(LibraryService lib, Scanner sc) {
		System.out.print("Author contains: ");
		String q = sc.nextLine().trim();
		List<Book> found = lib.searchByAuthor(q);
		if (found.isEmpty()) {
			System.out.println("No books found by author containing: " + q);
		} else {
			System.out.println("Found books by author:");
			found.forEach(b -> System.out.println("  " + b));
		}
	}

	private static void searchIsbn(LibraryService lib, Scanner sc) {
		System.out.print("ISBN: ");
		String isbn = sc.nextLine().trim();
		Optional<Book> maybe = lib.findByIsbn(isbn);
		if (maybe.isPresent()) {
			System.out.println("Book found: " + maybe.get());
		} else {
			System.out.println("No book found with ISBN " + isbn);
		}
	}

	private static void addPatron(LibraryService lib, Scanner sc) {
		System.out.print("Patron id: ");
		String id = sc.nextLine().trim();
		System.out.print("Name: ");
		String name = sc.nextLine().trim();
		System.out.print("Email: ");
		String email = sc.nextLine().trim();
		lib.addPatron(id, name, email);
		System.out.println("Patron added.");
	}

	private static void checkout(LibraryService lib, Scanner sc) {
		System.out.print("ISBN: ");
		String isbn = sc.nextLine().trim();
		System.out.print("Patron id: ");
		String pid = sc.nextLine().trim();
		System.out.print("Days: ");
		int days = Integer.parseInt(sc.nextLine().trim());
		boolean ok = lib.checkout(isbn, pid, days);
		System.out.println(ok ? "Checked out." : "Checkout failed.");
	}

	private static void returnBook(LibraryService lib, Scanner sc) {
		System.out.print("ISBN: ");
		String isbn = sc.nextLine().trim();
		boolean ok = lib.returnBook(isbn);
		System.out.println(ok ? "Returned." : "Return failed.");
	}

	private static void reserveBook(LibraryService lib, Scanner sc) {
		System.out.print("ISBN: ");
		String isbn = sc.nextLine().trim();
		System.out.print("Patron id: ");
		String pid = sc.nextLine().trim();
		lib.reserve(isbn, pid);
		System.out.println("Reserved.");
	}

	private static void recommend(LibraryService lib, Scanner sc) {
		System.out.print("Patron id: ");
		String pid = sc.nextLine().trim();
		System.out.print("Limit: ");
		int limit = Integer.parseInt(sc.nextLine().trim());
		var recs = lib.recommend(pid, limit);
		System.out.println("Recommendations:");
		recs.forEach(b -> System.out.println("  " + b));
	}
}
