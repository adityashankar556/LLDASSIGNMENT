package com.example.library.model;

import com.example.library.patterns.observer.Observer;

public class Reservation implements Observer {
	private final Book book;
	private final Patron patron;

	public Reservation(Book b, Patron p) {
		this.book = b;
		this.patron = p;
	}

	public Book getBook() {
		return book;
	}

	public Patron getPatron() {
		return patron;
	}

	@Override
	public void update(String msg) {
		System.out.println("[Notification] To:" + patron.getEmail() + " - " + msg);
	}
}