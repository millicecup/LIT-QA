import java.util.Scanner;
import java.io.File;

public class ReadingCLI {
    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner scanner = new Scanner(new File("output.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
        scanner.close();
       
    }
}
