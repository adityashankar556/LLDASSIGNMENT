# LibraryCoreDynamic
In-memory, pure core-Java Library Management System with:
- SOLID principles applied
- Design patterns: Factory, Strategy, Observer, Repository
- CLI interactive menu (Main.java)
- Logging via java.util.logging

## How to compile and run

From the project root:

```bash
javac -d out src/com/example/library/**/*.java
java -cp out com.example.library.app.Main
```

## Features
- Add/Search/Update books
- Add patrons
- Checkout/Return/Reserve books
- Simple recommendation system
