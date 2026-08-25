public class FreshFruit extends Product {
    // present stock level of the fresh fruits
    private double stockLevel;

    // initializing  fresh fruits with its attributes
    public FreshFruit(String id, String name, double price, double discountRate, double stockLevel) {
        // Call to the superclass (Product) constructor to set common product attributes
        super(id, name, "Fresh Fruit", price, "kg", discountRate, 0.0);
        // Initialize the stock level for this fresh fruit
        this.stockLevel = stockLevel;
    }

    //using the  Get method to retrieve the current stock level
    public double getStockLevel() {
        return stockLevel;
    }

    // reducing the stock level by a certain quantity
    public void reduceStock(double quantity) {
        this.stockLevel -= quantity; // Decrease stock level by the given quantity
    }

    // Overriding toString method to provide a string representation of the fresh fruit, including stock level
    @Override
    public String toString() {
        return super.toString() + String.format("\nStock Level: %.2f kg", stockLevel); // Append stock level to the product details
    }
}
