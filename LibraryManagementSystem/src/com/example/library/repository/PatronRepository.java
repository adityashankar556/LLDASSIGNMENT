package com.example.library.repository;

import com.example.library.model.Patron;
import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    void add(Patron patron);
    Optional<Patron> findById(String id);
    void update(Patron patron);
    List<Patron> findAll();
}
