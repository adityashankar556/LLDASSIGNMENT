package com.example.library.model;

import java.util.*;

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

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		this.email = e;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void addLoan(Loan l) {
		loans.add(l);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Patron))
			return false;
		Patron p = (Patron) o;
		return id.equals(p.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Patron{" + id + "," + name + "}";
	}
}