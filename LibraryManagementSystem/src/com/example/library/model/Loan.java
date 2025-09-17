package com.example.library.model;

import java.time.LocalDate;

public class Loan {
    private final Book book;
    private final Patron patron;
    private final LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public Loan(Book book, Patron patron, LocalDate checkoutDate, LocalDate dueDate) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public Book getBook() { return book; }
    public Patron getPatron() { return patron; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }
    public boolean isReturned() { return returnedDate != null; }

    @Override
    public String toString() {
        return String.format("Loan{book=%s, patron=%s, checkout=%s, due=%s, returned=%s}", book.getIsbn(), patron.getId(), checkoutDate, dueDate, returnedDate);
    }
}
