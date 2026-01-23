import java.util.ArrayList;
import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<Task>();

    public void intro() {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void listTasks() {
        System.out.println(SEPARATOR);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            System.out.println((i + 1) + "." + curr);
        }
        System.out.println(SEPARATOR);
    }

    public void markTaskAsDone(int task_id) {
        Task target = tasks.get(task_id);
        target.markAsDone();

        System.out.println(SEPARATOR);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    public void markTaskAsUndone(int task_id) {
        Task target = tasks.get(task_id);
        target.markAsUndone();

        System.out.println(SEPARATOR);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    public void addTodo(String description) {
        Task todo = new Todo(description);
        tasks.add(todo);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void addDeadline(String description, String deadline) {
        Task dl = new Deadline(description, deadline);
        tasks.add(dl);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(dl);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void addEvent(String description, String start, String end) {
        Task event = new Event(description, start, end);
        tasks.add(event);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void exit() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    public static void main(String[] args) {
        B2SuperBattleDroid chatbot = new B2SuperBattleDroid();

        chatbot.intro();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("list")) {
                chatbot.listTasks();
                continue;
            }

            if (input.startsWith("mark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                chatbot.markTaskAsDone(task_id);
                continue;
            }

            if (input.startsWith("unmark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                chatbot.markTaskAsUndone(task_id);
                continue;
            }

            if (input.startsWith("todo")) {
                String description = input.substring(5).trim();
                chatbot.addTodo(description);
                continue;
            }

            if (input.startsWith("deadline")) {
                String[] components = input.substring(9).trim().split(" /by ");
                chatbot.addDeadline(components[0], components[1]);
                continue;
            }

            if (input.startsWith("event")) {
                String[] components = input.substring(6).trim().split(" /from | /to ");
                chatbot.addEvent(components[0], components[1], components[2]);
                continue;
            }

            if (input.equals("bye")) {
                chatbot.exit();
                break;
            }
        }
    }
}
