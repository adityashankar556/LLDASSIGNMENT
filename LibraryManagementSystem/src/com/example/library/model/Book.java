package com.example.library.model;

import java.util.Objects;

public class Book {
	private final String isbn;
	private String title;
	private String author;
	private int publicationYear;

	public Book(String isbn, String title, String author, int publicationYear) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String a) {
		this.author = a;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int y) {
		this.publicationYear = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Book))
			return false;
		Book b = (Book) o;
		return isbn.equals(b.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	@Override
	public String toString() {
		return String.format("Book{isbn='%s',title='%s'}", isbn, title);
	}
}