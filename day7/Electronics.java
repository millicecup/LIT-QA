package day7;

class Electronics extends Product {
    private String brand;
    
    public Electronics(String productId, String name, double price, int stockQuantity, String brand) {
        super(productId, name, price, stockQuantity);
        this.brand = brand;
    }
    
    public String getBrand() { return brand; }
    
    @Override
    public String getProductDetails() {
        return String.format(
            "ID: %s | Product: %s | Brand: %s | Price: $%.2f | Stock: %d",
            productId, name, brand, price, stockQuantity
        );
    }
}