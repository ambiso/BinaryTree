import java.util.Scanner;

public class Main {
	public static void main(String... args) {
		Tree x = new Tree();
		x.insert(30);
		x.insert(20);
		x.insert(45);
		x.insert(10);
		x.insert(25);
		x.insert(38);
		x.insert(50);
		x.insert(5);
		x.insert(13);
		x.insert(22);
		x.insert(29);
		x.insert(12);
		x.insert(14);
		x.inOrder();
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextInt()) {
			System.out.println(x.valueAtPosition(sc.nextInt()));
		}
	}
}
