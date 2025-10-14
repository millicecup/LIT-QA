package day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static List<Product> products = new ArrayList<>();
    private static List<Product> cart = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        loadProductsFromFile();
        
        System.out.println("WELCOME TO ECOMMERCE SYSTEM");
        System.out.println("=" .repeat(30));
        
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    saveCartToFile();
                    break;
                case 5:
                    addNewProduct();
                    break;
                case 6:
                    updateStorageFile();
                    break;
                case 7:
                    System.out.println("Thank you for shopping!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void loadProductsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("day7/storage.txt"));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts[0].startsWith("B")) {
                    // Book format: ID,Name,Price,Stock,Author
                    products.add(new Book(parts[0], parts[1], 
                                        Double.parseDouble(parts[2]), 
                                        Integer.parseInt(parts[3]), 
                                        parts[4]));
                } else if (parts[0].startsWith("E")) {
                    // Electronics format: ID,Name,Price,Stock,Brand
                    products.add(new Electronics(parts[0], parts[1], 
                                               Double.parseDouble(parts[2]), 
                                               Integer.parseInt(parts[3]), 
                                               parts[4]));
                }
            }
            reader.close();
            System.out.println("Products loaded from storage.txt");
        } catch (IOException e) {
            System.out.println("Error reading storage.txt: " + e.getMessage());
            System.out.println("Using default products...");
            initializeDefaultProducts();
        }
    }
    
    private static void initializeDefaultProducts() {
        products.add(new Book("B001", "Java Programming", 45.99, 15, "Herbert Schildt"));
        products.add(new Book("B002", "Clean Code", 32.50, 8, "Robert Martin"));
        products.add(new Electronics("E001", "Gaming Laptop", 1299.99, 3, "ASUS"));
        products.add(new Electronics("E002", "Wireless Headphones", 199.99, 12, "Sony"));
    }
    
    private static void showMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. View Products");
        System.out.println("2. Add to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Save Cart to File");
        System.out.println("5. Add New Product");
        System.out.println("6. Update Storage File");
        System.out.println("7. Exit");
        System.out.print("Choose option: ");
    }
    
    private static void viewProducts() {
        System.out.println("\nAVAILABLE PRODUCTS:");
        System.out.println("-" .repeat(30));
        
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getProductDetails());
        }
    }
    
    private static void addToCart() {
        viewProducts();
        System.out.print("\nEnter product number to add to cart: ");
        int productIndex = scanner.nextInt() - 1;
        
        if (productIndex >= 0 && productIndex < products.size()) {
            Product selectedProduct = products.get(productIndex);
            if (selectedProduct.isInStock()) {
                cart.add(selectedProduct);
                selectedProduct.reduceStock(1);
                System.out.println("Added to cart: " + selectedProduct.getName());
            } else {
                System.out.println("Product out of stock!");
            }
        } else {
            System.out.println("Invalid product number!");
        }
    }
    
    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }
        
        System.out.println("\nYOUR CART:");
        System.out.println("-" .repeat(30));
        
        double totalAmount = 0;
        for (int i = 0; i < cart.size(); i++) {
            Product item = cart.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
            totalAmount += item.getPrice();
        }
        
        System.out.println("-" .repeat(30));
        System.out.println("Total Amount: $" + String.format("%.2f", totalAmount));
    }
    
    private static void addNewProduct() {
        System.out.println("\nADD NEW PRODUCT:");
        System.out.println("1. Book");
        System.out.println("2. Electronics");
        System.out.print("Choose type: ");
        
        int type = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        
        System.out.print("Enter Stock Quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        
        Product newProduct = null;
        String storageEntry = "";
        
        if (type == 1) {
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            
            newProduct = new Book(id, name, price, stock, author);
            products.add(newProduct);
            
            // Format for storage.txt: ID,Name,Price,Stock,Author
            storageEntry = String.format("%s,%s,%.2f,%d,%s", 
                                       id, name, price, stock, author);
            
            System.out.println("Book added successfully!");
        } else if (type == 2) {
            System.out.print("Enter Brand: ");
            String brand = scanner.nextLine();
            
            newProduct = new Electronics(id, name, price, stock, brand);
            products.add(newProduct);
            
            // Format for storage.txt: ID,Name,Price,Stock,Brand
            storageEntry = String.format("%s,%s,%.2f,%d,%s", 
                                       id, name, price, stock, brand);
            
            System.out.println("Electronics added successfully!");
        } else {
            System.out.println("Invalid type!");
            return;
        }
        
        saveProductToStorage(storageEntry);
    }
    
    private static void saveProductToStorage(String productEntry) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("day7/storage.txt", true)); 
            writer.println(productEntry);
            writer.close();
            System.out.println("Product saved to storage.txt!");
        } catch (IOException e) {
            System.out.println("Error saving to storage.txt: " + e.getMessage());
        }
    }
    
    private static void saveCartToFile() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to save.");
            return;
        }
        
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("day7/cart.txt"));
            writer.println("SHOPPING CART RECEIPT");
            writer.println("====================");
            writer.println("Date: " + java.time.LocalDateTime.now());
            writer.println();
            
            double totalAmount = 0;
            for (int i = 0; i < cart.size(); i++) {
                Product item = cart.get(i);
                writer.println((i + 1) + ". " + item.getName() + " - $" + item.getPrice());
                totalAmount += item.getPrice();
            }
            
            writer.println("--------------------");
            writer.println("Total Amount: $" + String.format("%.2f", totalAmount));
            writer.println("====================");
            
            writer.close();
            System.out.println("Cart saved to cart.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }
    
    private static void updateStorageFile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("day7/storage.txt"));
            
            for (Product product : products) {
                String entry = "";
                if (product instanceof Book) {
                    Book book = (Book) product;
                    // Format: ID,Name,Price,Stock,Author
                    entry = String.format("%s,%s,%.2f,%d,%s", 
                                        product.getProductId(), product.getName(), 
                                        product.getPrice(), product.getStockQuantity(),
                                        book.getAuthor());
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    // Format: ID,Name,Price,Stock,Brand
                    entry = String.format("%s,%s,%.2f,%d,%s", 
                                        product.getProductId(), product.getName(), 
                                        product.getPrice(), product.getStockQuantity(),
                                        electronics.getBrand());
                }
                writer.println(entry);
            }
            
            writer.close();
            System.out.println("Storage updated successfully!");
        } catch (IOException e) {
            System.out.println("Error updating storage: " + e.getMessage());
        }
    }
}