README - Library Management System

Features implemented:
- Book CRUD and search by title/author/ISBN.
- Patron CRUD and borrowing history tracking.
- Checkout/return flows with in-memory active loans.
- Reservation system (FIFO) and Observer-style notification on availability.
- Recommendation system using Strategy pattern (simple author-frequency strategy).
- Factory pattern for entity creation.
- Singleton LibraryService to provide centralized operations.
- In-memory repositories using thread-safe maps.
- Logging via java.util.logging in repositories and services.

Design rationale & SOLID mapping:
- Single Responsibility: classes separated into model/repo/service/patterns.
- Open/Closed: new recommendation strategies can be introduced without changing existing code.
- Liskov: repositories implement common interfaces; callers use interface types.
- Interface Segregation: small focused interfaces (e.g., Repository interfaces, Observer).
- Dependency Inversion: services receive repository interfaces in constructors.
