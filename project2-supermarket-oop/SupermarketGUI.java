import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SupermarketGUI {
    // GUI components
    private JFrame frame;                //  this is theMain frame of the interface
    private JTextArea textArea;          // using the text area for the summary of the purchase
    private JComboBox<String> productComboBox; // options for selecting the products
    private JTextField quantityField;    // using the field for quantity
    private JButton addButton, checkoutButton; // adding the cart for checking cart

    // Cart to store selected items with quantities
    private Map<String, Integer> cart = new HashMap<>();
    // List of available products
    private java.util.List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SupermarketGUI window = new SupermarketGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // using the constructor initialize gui components
    public SupermarketGUI() {
        initialize();      // Initialize GUI components
        addProducts();     // Adding products to the product list
        updateProductComboBox(); // Updating the dropdown
    }

    private void initialize() {
        // Set up main frame properties
        frame = new JFrame("Supermarket");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // text area for summary of the purchase with a scroll pane
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Purchase Summary"));
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Set up product selection panel with labels and input fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Product Selection"));
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        //options for the product
        productComboBox = new JComboBox<>();
        productComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(new JLabel("Select Product:", JLabel.CENTER));
        panel.add(productComboBox);

        // field to input the quantity
        quantityField = new JTextField();
        quantityField.setColumns(5);
        quantityField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(new JLabel("Enter Quantity:", JLabel.CENTER));
        panel.add(quantityField);

        //  using the "Add to Cart" button
        addButton = new JButton("Add to Cart");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addToCart());
        panel.add(addButton);

        // using the "Checkout" button with action listener
        checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        checkoutButton.setBackground(new Color(40, 167, 69));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.addActionListener(e -> displayDocket());
        panel.add(checkoutButton);
    }

    private void addProducts() {
        // Adding the fresh fruit products in the list
        products.add(new FreshFruit("FF01", "Apple", 3.50, 0.1, 20));
        products.add(new FreshFruit("FF02", "Banana", 2.50, 0.05, 30));
        products.add(new FreshFruit("FF03", "Orange", 4.00, 0.08, 25));
        products.add(new FreshFruit("FF04", "Grapes", 5.00, 0.07, 15));
        products.add(new FreshFruit("FF05", "Watermelon", 10.00, 0.1, 10));

        // Adding the packaged products in the list
        products.add(new Packaged("PK01", "Chocolate", 5.00, 0.1, 10, new Date(2024, 11, 30), 30));
        products.add(new Packaged("PK02", "Eggs", 6.00, 0.05, 12, new Date(2024, 10, 25), 40));
        products.add(new Packaged("PK03", "Bread", 3.00, 0.02, 1, new Date(2024, 11, 10), 50));
        products.add(new Packaged("PK04", "Butter", 4.00, 0.03, 1, new Date(2024, 11, 20), 60));
        products.add(new Packaged("PK05", "Milk", 2.50, 0.04, 1, new Date(2024, 12, 5), 70));
    }

    private void updateProductComboBox() {
        // Adding the products to  the dropdown menu
        for (Product product : products) {
            String item = String.format("%s - %s (%.0f%% off)",
                    product.getId(), product.getName(), product.getDiscountRate() * 100);
            productComboBox.addItem(item);
        }
    }

    private void addToCart() {
        // Get selected product from dropdown and quantity from text field
        String selectedProduct = (String) productComboBox.getSelectedItem();
        if (selectedProduct == null || selectedProduct.isEmpty()) return;

        // Extracting the ID of the product from selected item
        String productId = selectedProduct.split(" - ")[0];
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText()); // Parse quantity
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid quantity."); // Show error if invalid
            return;
        }

        // Find object of the product by ID
        Product selectedProductObj = null;
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                selectedProductObj = product;
                break;
            }
        }

        // display error if product not found
        if (selectedProductObj == null) {
            JOptionPane.showMessageDialog(frame, "Product ID not found.");
            return;
        }

        // Handle stock check for fresh fruit items only
        if (selectedProductObj instanceof FreshFruit) {
            FreshFruit fruit = (FreshFruit) selectedProductObj;
            if (quantity > fruit.getStockLevel()) { // checking the stock
                JOptionPane.showMessageDialog(frame, "Insufficient stock for " + fruit.getName());
                return;
            }
            fruit.reduceStock(quantity); // Reduce stock if sufficient
        }

        // Adding the selected product and quantity into the cart
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        textArea.append("Added to cart: " + selectedProduct + " x " + quantity + "\n");
    }

    private void displayDocket() {
        // using the Variables to track total amount and tax
        double totalAmount = 0;
        double totalTax = 0;

        textArea.setText("");  // erase previous record
        textArea.append("Purchase Docket:\n");

        // Loop through items in the cart to calculate and display totals
        for (String productId : cart.keySet()) {
            for (Product product : products) {
                if (product.getId().equals(productId)) {
                    int quantity = cart.get(productId);
                    double discountedPrice = product.getPrice() * (1 - product.getDiscountRate());
                    double tax = discountedPrice * product.getTaxRate() * quantity;
                    double amount = discountedPrice * quantity + tax;

                    // Display details  of the product and amount due
                    textArea.append(product.toString());
                    textArea.append(String.format("\nQuantity: %d\n", quantity));
                    textArea.append(String.format("Amount Due (with discount and tax): %.2f\n", amount));
                    textArea.append("------------------------\n");

                    totalAmount += amount; // Update total amount
                    totalTax += tax;       // Update total tax
                }
            }
        }

        // Display total tax and amount due
        textArea.append(String.format("Total Amount Due: %.2f\n", totalAmount));
        textArea.append(String.format("Total Tax: %.2f\n", totalTax));
    }
}
