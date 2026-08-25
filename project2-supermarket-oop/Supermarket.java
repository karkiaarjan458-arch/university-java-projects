
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class Supermarket {
    // List to hold every available products in the supermarket
    private static java.util.List<Product> products = new ArrayList<>();
    //  keeping track of all items added to the cart
    private static Map<String, Integer> cart = new HashMap<>();

    public static void main(String[] args) {
        // Initialize products and populate the product list
        addProducts();
        // showing the menu available
        displayMenu();
        // handling the purchase of the user
        handlePurchase();
        // final purchase docket
        displayDocket();
    }

    private static void addProducts() {
        // Adding fresh fruit to the list
        products.add(new FreshFruit("FF01", "Apple", 3.50, 0.1, 20));
        products.add(new FreshFruit("FF02", "Banana", 2.50, 0.05,30));
        products.add(new FreshFruit("FF03", "Orange", 4.00, 0.08,25));
        products.add(new FreshFruit("FF04", "Grapes", 5.00, 0.07,15));
        products.add(new FreshFruit("FF05", "Watermelon",10.00, 0.1, 10));

        // Adding packaged products to the list
        products.add(new Packaged("PK01", "Chocolate", 5.00, 0.1, 10, new Date(2024, 11, 30), 50));
        products.add(new Packaged("PK02", "Eggs", 6.00, 0.05, 12, new Date(2024, 10, 25), 30));
        products.add(new Packaged("PK03", "Bread", 3.00, 0.02, 1, new Date(2024, 11, 10), 40));
        products.add(new Packaged("PK04", "Butter", 4.00, 0.03, 1, new Date(2024, 11, 20), 20));
        products.add(new Packaged("PK05", "Milk", 2.50, 0.04, 1, new Date(2024, 12, 5), 60));
    }

    private static void displayMenu() {
        // Printing the product menu
        System.out.println("Product Menu:");
        // using for loop list of products and display each product's details
        for (Product product : products) {
            System.out.println(product);
            System.out.println("------------------------");
        }
    }

    private static void handlePurchase() {
        // using the  scanner for user input
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            // Prompt user to enter a product ID or 'done' to finish
            System.out.println("Enter the Product ID to add to cart (or 'done' to finish):");
            input = scanner.nextLine();
            // Check if the user has terminated the purchase or not
            if (!input.equals("done")) {
                Product selectedProduct = null;
                // searching the product ID
                for (Product product : products) {
                    if (product.getId().equals(input)) {
                        selectedProduct = product; // Product found
                        break;
                    }
                }

                // informing the user if the product is not found
                if (selectedProduct == null) {
                    System.out.println("Product ID not found. Please enter a valid Product ID.");
                } else {
                    // asking the user for the quantity they want to purchase
                    System.out.println("Enter quantity:");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    // Checking the selected product whether a fresh fruit or not
                    if (selectedProduct instanceof FreshFruit) {
                        FreshFruit fruit = (FreshFruit) selectedProduct;
                        if (quantity > fruit.getStockLevel()) {
                            System.out.println("Insufficient stock for " + fruit.getName());
                        } else {
                            // Reducing stock and adding product to cart
                            fruit.reduceStock(quantity);
                            cart.put(input, cart.getOrDefault(input, 0) + quantity);
                            System.out.println("Added to cart: " + fruit.getName() + " x " + quantity);
                        }
                        // Checking whether the selected product is a packaged item or not
                    } else if (selectedProduct instanceof Packaged) {
                        Packaged packaged = (Packaged) selectedProduct;
                        // Ensuring enough stock is available
                        if (quantity > packaged.getStockLevel()) {
                            System.out.println("Insufficient stock for " + packaged.getName());
                        } else {
                            //  add product to cart
                            packaged.reduceStock(quantity);
                            cart.put(input, cart.getOrDefault(input, 0) + quantity);
                            System.out.println("Added to cart: " + packaged.getName() + " x " + quantity);
                        }
                    }
                }
            }
        } while (!input.equals("done")); // Loop until user indicates they are done

        // Closing the scanner after use
        scanner.close();
    }

    private static void displayDocket() {
        // Initialize total amount and tax variables
        double totalAmount = 0;
        double totalTax = 0;

        // Printing the purchase docket header
        System.out.println("Purchase Docket:");
        //  calculate total amounts for each product
        for (String productId : cart.keySet()) {
            for (Product product : products) {
                if (product.getId().equals(productId)) {
                    int quantity = cart.get(productId);
                    // Calculate discounted price and tax for the current product
                    double discountedPrice = product.getPrice() * (1 - product.getDiscountRate());
                    double tax = discountedPrice * product.getTaxRate() * quantity;
                    double amount = discountedPrice * quantity + tax;

                    // Displaying the details of the product and due amounts
                    System.out.println(product);
                    System.out.printf("Quantity: %d\n", quantity);
                    System.out.printf("Amount Due (with discount and tax): %.2f\n", amount);
                    System.out.println("------------------------");

                    // Accumulate totals for final display
                    totalAmount += amount;
                    totalTax += tax;
                }
            }
        }

        // Display total tax and total due amount
        System.out.printf("Total Amount Due: %.2f\n", totalAmount);
        System.out.printf("Total Tax: %.2f\n", totalTax);
    }
}

