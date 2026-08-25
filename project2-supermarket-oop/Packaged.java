import java.util.Date;

public class Packaged extends Product {
    // total number of items in the package
    private int quantity;
    // using the expiry date
    private Date useByDate;
    // finding the stock level of the packaged product
    private int stockLevel;

    //using the Constructor to initialize a packaged product with its attributes
    public Packaged(String id, String name, double price, double discountRate, int quantity, Date useByDate, int stockLevel) {
        // Calling to the superclass (Product) constructor for setting common product attributes
        super(id, name, "Packaged", price, "pack", discountRate, 0.1);
        // intializing the use by date, quantity and stock level
        this.quantity = quantity;
        this.useByDate = useByDate;
        this.stockLevel=stockLevel;
    }

    // retrieving the quantity in the package
    public int getQuantity() {
        return quantity;
    }

    // Get method to retrieve the use-by date of the packaged product
    public Date getUseByDate() {
        return useByDate;
    }

    // Getter method to retrieve the current stock level
    public int getStockLevel() {
        return stockLevel;
    }

    //reducing the stock level
    public void reduceStock(int quantity) {
        this.stockLevel -= quantity;
    }

    // Overriding toString method to provide a representation of the packaged product, including quantity, use-by date, and stock level
    @Override
    public String toString() {
        return super.toString() + String.format("\nQuantity: %d\nUse-By Date: %s\nStock Level: %d packs", quantity, useByDate, stockLevel); // Append details specific to the packaged product
    }
}
