package com.example.library.model;

import java.time.LocalDate;

public class Loan {
	private final Book book;
	private final Patron patron;
	private final LocalDate checkoutDate;
	private LocalDate dueDate;
	private LocalDate returnedDate;

	public Loan(Book b, Patron p, LocalDate cd, LocalDate dd) {
		this.book = b;
		this.patron = p;
		this.checkoutDate = cd;
		this.dueDate = dd;
	}

	public Book getBook() {
		return book;
	}

	public Patron getPatron() {
		return patron;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate d) {
		this.dueDate = d;
	}

	public LocalDate getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(LocalDate r) {
		this.returnedDate = r;
	}

	public boolean isReturned() {
		return returnedDate != null;
	}
}