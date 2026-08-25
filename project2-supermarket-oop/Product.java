public class Product {
    // using the identifier for the product which is ID
    private String id;
    // variable to store name of the product
    private String name;
    // Type/category of the product
    private String type;
    // Price of the product
    private double price;
    // Unit of measurement for the product (e.g., kg, pack)
    private String unit;
    // discount percentage for the products
    private double discountRate;
    // Tax rate for various products
    private double taxRate;

    // Constructor to initialize a new product with its attributes
    public Product(String id, String name, String type, double price, String unit, double discountRate, double taxRate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.unit = unit;
        this.discountRate = discountRate;
        this.taxRate = taxRate;
    }

    // Using the Get method in the program in order to retrieve the product ID
    public String getId() {
        return id;
    }

    // Using the Get method in the program in order to retrieve the product name
    public String getName() {
        return name;
    }

    // Using the Get method in the program in order to retrieve the product price
    public double getPrice() {
        return price;
    }

    // Using the Get method in the program in order to retrieve the product discount rate
    public double getDiscountRate() {
        return discountRate;
    }

    // Using the Get method in the program in order to retrieve the product tax rate
    public double getTaxRate() {
        return taxRate;
    }

    // Overriding toString method to provide a string representation of the product
    @Override
    public String toString() {
        return String.format("ID: %s\nName: %s\nType: %s\nPrice: %.2f %s\nDiscount Rate: %.2f%%\nTax Rate: %.2f%%",
                id, name, type, price, unit, discountRate * 100, taxRate * 100);
    }
}
