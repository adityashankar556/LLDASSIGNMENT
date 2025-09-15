package com.example.library.patterns.strategy;

import com.example.library.model.*;
import java.util.*;

public interface RecommendationStrategy {
	List<Book> recommend(Patron p, List<Book> available);
}