package com.example.library.patterns.factory;

import com.example.library.model.Book;
import com.example.library.model.Patron;

public class EntityFactory {
    public static Book createBook(String isbn, String title, String author, int year) {
        return new Book(isbn, title, author, year);
    }

    public static Patron createPatron(String id, String name, String email) {
        return new Patron(id, name, email);
    }
}
