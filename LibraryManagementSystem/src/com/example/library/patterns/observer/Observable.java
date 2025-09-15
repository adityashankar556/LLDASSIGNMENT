package com.example.library.patterns.observer;

import java.util.*;

public class Observable {
	private final List<Observer> obs = new ArrayList<>();

	public void addObserver(Observer o) {
		obs.add(o);
	}

	public void removeObserver(Observer o) {
		obs.remove(o);
	}

	public void notifyObservers(String msg) {
		for (Observer o : new ArrayList<>(obs)) {
			o.update(msg);
		}
	}
}