import java.util.ArrayList;
import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("User: ");
            String input = scanner.nextLine().trim();

            if (input.equals("list")) {
                System.out.println(SEPARATOR);
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.println(SEPARATOR);
                continue;
            }

            if (input.equals("bye")) {
                System.out.println(SEPARATOR);
                System.out.println("Bye. hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            System.out.println(SEPARATOR);
            System.out.println("added: " + input);
            System.out.println(SEPARATOR);
            tasks.add(input);
        }
    }
}
