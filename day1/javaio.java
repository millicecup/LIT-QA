package day1;

import java.util.Scanner;

public class javaio {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Kontainer nama dan tinggi 
        String[] names = new String[10];
        double[] heights = new double[10];
        
        System.out.println("=== Program Input Tinggi Badan 10 Orang ===");
        System.out.println();
        
        // Input data 10 orang
        for (int i = 0; i < 10; i++) {
            System.out.println("Data orang ke-" + (i + 1) + ":");
            System.out.print("Nama: ");
            names[i] = scanner.nextLine();
            
            System.out.print("Tinggi badan (cm): ");
            heights[i] = scanner.nextDouble();
            scanner.nextLine(); 
            System.out.println();
        }
        
        // Equation
        double totalHeight = 0;
        for (int i = 0; i < 10; i++) {
            totalHeight += heights[i];
        }
        double averageHeight = totalHeight / 10;
        
        // Output
        System.out.println("=== HASIL ===");
        System.out.println("\nData lengkap:");
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + ". " + names[i] + " - " + heights[i] + " cm");
        }
        
        System.out.println("\n--- Statistik ---");
        System.out.println("Total tinggi badan: " + totalHeight + " cm");
        System.out.printf("Rata-rata tinggi badan: %.2f cm%n", averageHeight);
        
        // Max min
        double maxHeight = heights[0];
        double minHeight = heights[0];
        String tallestPerson = names[0];
        String shortestPerson = names[0];
        
        for (int i = 1; i < 10; i++) {
            if (heights[i] > maxHeight) {
                maxHeight = heights[i];
                tallestPerson = names[i];
            }
            if (heights[i] < minHeight) {
                minHeight = heights[i];
                shortestPerson = names[i];
            }
        }
        
        System.out.println("Tertinggi: " + tallestPerson + " (" + maxHeight + " cm)");
        System.out.println("Terendah: " + shortestPerson + " (" + minHeight + " cm)");
        
        scanner.close();
    }
}