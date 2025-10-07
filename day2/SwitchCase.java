package day2;

import java.util.Scanner;

public class SwitchCase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a color number (1-6): ");
        int color = scanner.nextInt();
        
        String colorName;
        switch (color) {
            case 1:
                colorName = "Red";
                break;
            case 2:
                colorName = "Green";
                break;
            case 3:
                colorName = "Blue";
                break;
            case 4:
                colorName = "Yellow";
                break;
            case 5:
                colorName = "Purple";
                break;
            case 6:
                colorName = "Orange";
                break;
            default:
                colorName = "Unknown";
                break;
        }
        
        System.out.println("You selected: " + colorName);
        scanner.close();
    }
}
