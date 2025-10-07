import java.io.PrintWriter;
import java.util.Scanner;

// public class WriterCLI {
//     public static void main(String[] args) throws java.io.FileNotFoundException {
//         PrintWriter writer = new PrintWriter("output.txt");
//         Scanner scanner = new Scanner(System.in);
//         System.out.println("How many?");
//         String inputCount = scanner.nextLine();
//         int count = Integer.parseInt(inputCount);
//         for (int i = 0; i < count; i++) {
//             System.out.print("Write name, height: ");
//             String input = scanner.nextLine();
//             writer.println(input);
//         }
        
//         writer.close();
//     }
// }


public class WriterCLI {
    public static void main(String[] args) throws java.io.FileNotFoundException {
        PrintWriter writer = new PrintWriter("output.txt");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Write name, height: ");
            String input = scanner.nextLine();
            writer.println(input);
            if (input.equals("selesai") ) {
                break;
            }
        }
        
        writer.close();
    }
}

