package com.example.library.repository;

import com.example.library.model.Patron;
import java.util.*;

public interface PatronRepository {
	void addPatron(Patron p);

	Optional<Patron> findById(String id);

	void updatePatron(Patron p);

	List<Patron> findAll();
}