package day2;

public class WhileLoopExample {
	public static void main(String[] args) {
		int count = 1;
		System.out.println("While loop:");
		while (count <= 5) {
			System.out.println("Count is: " + count);
			count++;
		}

		int doWhileCount = 1;
		System.out.println("\nDo-while loop:");
		do {
			System.out.println("Do-while count is: " + doWhileCount);
			doWhileCount++;
		} while (doWhileCount <= 6); // Condition is false, but loop runs once
	}
}