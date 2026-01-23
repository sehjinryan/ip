import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("User: ");
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                System.out.println(SEPARATOR);
                System.out.println("Bye. hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            System.out.println(SEPARATOR);
            System.out.println(input);
            System.out.println(SEPARATOR);
        }
    }
}
