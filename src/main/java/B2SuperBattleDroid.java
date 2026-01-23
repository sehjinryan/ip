import java.util.ArrayList;
import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<Task>();

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
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < tasks.size(); i++) {
                    Task curr = tasks.get(i);
                    System.out.println((i + 1) + ". " + curr.getStatusIcon() + " " + curr.description);
                }
                System.out.println(SEPARATOR);
                continue;
            }

            if (input.startsWith("mark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                Task target = tasks.get(task_id);
                target.markAsDone();

                System.out.println(SEPARATOR);
                System.out.println("Nice! I've marked this task as done: ");
                System.out.println("  " + target.getStatusIcon() + " " + target.description);
                System.out.println(SEPARATOR);

                continue;
            }

            if (input.startsWith("unmark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                Task target = tasks.get(task_id);
                target.markAsUndone();

                System.out.println(SEPARATOR);
                System.out.println("OK, I've marked this task as not done yet: ");
                System.out.println("  " + target.getStatusIcon() + " " + target.description);
                System.out.println(SEPARATOR);

                continue;
            }

            if (input.equals("bye")) {
                System.out.println(SEPARATOR);
                System.out.println("Bye. hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            else {
                Task t = new Task(input);
                tasks.add(t);

                System.out.println(SEPARATOR);
                System.out.println("added: " + input);
                System.out.println(SEPARATOR);
            }
        }
    }
}
