package com.example.library.model;

import com.example.library.patterns.observer.Observer;

import java.time.LocalDateTime;

public class Reservation implements Observer {
	private final Book book;
	private final Patron patron;
	private final LocalDateTime reservedAt;
	private boolean fulfilled = false;

	public Reservation(Book book, Patron patron) {
		this.book = book;
		this.patron = patron;
		this.reservedAt = LocalDateTime.now();
	}

	public Book getBook() {
		return book;
	}

	public Patron getPatron() {
		return patron;
	}

	public LocalDateTime getReservedAt() {
		return reservedAt;
	}

	public boolean isFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}

	@Override
	public void update(String message) {
		System.out.println("[Notification] To: " + patron.getEmail() + " - " + message);
	}
}
