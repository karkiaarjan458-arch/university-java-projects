# Project 2 – Supermarket OOP System

An object-oriented Java program simulating a supermarket purchasing system, built around inheritance and polymorphism.

## Design
- `Product.java` — parent class holding attributes common to all products (ID, name, price, discount, tax).
- `FreshFruit.java` — subclass of `Product` for fruit items (sold by kg, no tax, tracks stock level).
- `Packaged.java` — subclass of `Product` for packaged items (sold by pack, 10% tax, tracks quantity and use-by date).
- `Supermarket.java` — console driver: builds a product menu, handles purchases, and prints a final docket with totals and tax.
- `SupermarketGUI.java` — Swing-based GUI version of the same purchasing flow.

## How to Run

Console version:
javac Product.java FreshFruit.java Packaged.java Supermarket.java
java Supermarket

GUI version:
javac Product.java FreshFruit.java Packaged.java SupermarketGUI.java
java SupermarketGUI
