package day3;

import java.util.Scanner;

public class array {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SISTEM RECEIPT ARRAY DINAMIS ===");
        
        // ARRAY POOL
        String[] masterMenu = {
            "Ayam Goreng:20000",
            "Nasi Padang:25000", 
            "Es Teh Manis:8000",
            "Bakso Special:20000",
            "Kopi Hitam:12000",
            "Sate Ayam:30000",
            "Jus Jeruk:15000",
            "Mie Ayam:18000"
        };
        
        System.out.println("MENU LENGKAP YANG TERSEDIA:");
        System.out.println("===========================");
        for (int i = 0; i < masterMenu.length; i++) {
            String[] parts = masterMenu[i].split(":");
            System.out.println((i + 1) + ". " + parts[0] + " - Rp " + parts[1]);
        }
        
        // ARRAY UNTUK PESANAN (yang dipilih dari pool)
        String[] selectedItems = new String[10];
        boolean[] isSelected = new boolean[masterMenu.length]; // Track item yang dipilih
        int selectedCount = 0;
        
        // 1. AMBIL/PILIH 2 ITEM DARI POOL ARRAY
        selectedItems[0] = masterMenu[0]; // Ayam Goreng
        selectedItems[1] = masterMenu[1]; // Nasi Padang
        isSelected[0] = true;
        isSelected[1] = true;
        selectedCount = 2;
        
        System.out.println("\n1. AMBIL 2 ITEM DARI MENU:");
        System.out.println("==========================");
        printSelectedArray(selectedItems, selectedCount);
        int total1 = calculateTotal(selectedItems, selectedCount);
        System.out.println(" Total: Rp " + total1);
        
        // 2. TAMBAH 2 ITEM LAGI DARI POOL ARRAY
        selectedItems[2] = masterMenu[2]; // Es Teh Manis
        selectedItems[3] = masterMenu[3]; // Bakso Special
        isSelected[2] = true;
        isSelected[3] = true;
        selectedCount = 4;
        
        System.out.println("\n2. TAMBAH 2 ITEM LAGI - TOTAL 4 ITEM:");
        System.out.println("=====================================");
        printSelectedArray(selectedItems, selectedCount);
        int total2 = calculateTotal(selectedItems, selectedCount);
        System.out.println(" Total: Rp " + total2);
        
        // 3. HAPUS 1 ITEM (Bakso Special - index 3)
        System.out.println("\n3. HAPUS 1 ITEM (Bakso Special) - TOTAL 3 ITEM:");
        System.out.println("===============================================");
        // Hapus item dengan shift
        for (int i = 3; i < selectedCount - 1; i++) {
            selectedItems[i] = selectedItems[i + 1];
        }
        selectedItems[selectedCount - 1] = null; // Clear last item
        isSelected[3] = false; // Update master tracking
        selectedCount = 3; // Update count
        
        printSelectedArray(selectedItems, selectedCount);
        int total3 = calculateTotal(selectedItems, selectedCount);
        System.out.println("Total: Rp " + total3);
        
        // 4. OPERASI ARITMATIKA - Diskon 10% untuk makanan
        System.out.println("\n4. OPERASI ARITMATIKA - DISKON 10% MAKANAN:");
        System.out.println("==========================================");
        for (int i = 0; i < selectedCount; i++) {
            if (selectedItems[i] != null) {
                String[] parts = selectedItems[i].split(":");
                String itemName = parts[0];
                int price = Integer.parseInt(parts[1]);
                
                // Kasih diskon untuk makanan (Ayam Goreng, Nasi Padang)
                if (itemName.contains("Ayam") || itemName.contains("Nasi")) {
                    price = price - (price * 10 / 100);
                    System.out.println(" " + itemName + " dapat diskon 10%!");
                    selectedItems[i] = itemName + ":" + price;
                }
            }
        }
        
        System.out.println("\nArray Setelah Diskon:");
        printSelectedArray(selectedItems, selectedCount);
        int totalFinal = calculateTotal(selectedItems, selectedCount);
        System.out.println(" Total Setelah Diskon: Rp " + totalFinal);
        
        // 5. RINGKASAN
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                  RINGKASAN");
        System.out.println("=".repeat(50));
        System.out.println("Total Awal (2 item)       : Rp " + total1);
        System.out.println("Total Tambah (4 item)     : Rp " + total2);
        System.out.println("Total Hapus (3 item)      : Rp " + total3);
        System.out.println("Total Final (dengan diskon): Rp " + totalFinal);
        System.out.println("Selisih Awal vs Final     : Rp " + (totalFinal - total1));
        System.out.println("=".repeat(50));
        
        // TAMPILKAN STATUS ITEM DI MASTER ARRAY
        System.out.println("\nSTATUS ITEM DI MASTER MENU:");
        System.out.println("===========================");
        for (int i = 0; i < masterMenu.length; i++) {
            String[] parts = masterMenu[i].split(":");
            String status = isSelected[i] ? " DIPILIH" : " TIDAK DIPILIH";
            System.out.println((i + 1) + ". " + parts[0] + " - " + status);
        }
        
        scanner.close();
    }
    
    // Method helper untuk print selected array
    public static void printSelectedArray(String[] array, int count) {
        for (int i = 0; i < count; i++) {
            if (array[i] != null) {
                String[] parts = array[i].split(":");
                System.out.println((i + 1) + ". " + parts[0] + " - Rp " + parts[1]);
            }
        }
    }
    
    // Method helper untuk hitung total
    public static int calculateTotal(String[] array, int count) {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (array[i] != null) {
                String[] parts = array[i].split(":");
                total += Integer.parseInt(parts[1]);
            }
        }
        return total;
    }
}

// / buat kayak array tagihan gitu

// bikin bebas receipt order apapun (pulsa, makanan, dll) pake array berupa string terus di manipulasi di lakukan operasi aritmatika ke dalem array itu. nah tunjukkan sebelum array list dibuat dan setalh penjumlahan dan atau pengurangan. tunjukkin gimana total pembeliannya