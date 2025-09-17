package com.example.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private final List<Loan> loans = new ArrayList<>();

    public Patron(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Loan> getLoans() { return loans; }
    public void addLoan(Loan loan) { loans.add(loan); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron)) return false;
        Patron patron = (Patron) o;
        return id.equals(patron.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() { return String.format("Patron{id='%s', name='%s', email='%s'}", id, name, email); }
}
