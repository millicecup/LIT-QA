package day7;

class Book extends Product {
    private String author;
    
    public Book(String productId, String name, double price, int stockQuantity, String author) {
        super(productId, name, price, stockQuantity);
        this.author = author;
    }
    
    public String getAuthor() { return author; }
    
    @Override
    public String getProductDetails() {
        return String.format(
            "ID: %s | Product: %s | Author: %s | Price: $%.2f | Stock: %d",
            productId, name, author, price, stockQuantity
        );
    }
}