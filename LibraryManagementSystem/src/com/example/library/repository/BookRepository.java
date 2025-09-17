package com.example.library.repository;

import com.example.library.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void add(Book book);
    void remove(String isbn);
    void update(Book book);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
}
