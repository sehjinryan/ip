import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private static final String SEPARATOR = "____________________________________________________________";
    private final Parser parser;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
        this.parser = new Parser();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.parser = new Parser();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
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

    public void markTaskAsDone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.get(task_id);
        target.markAsDone();


        System.out.println(SEPARATOR);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    public void markTaskAsUndone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.get(task_id);

        target.markAsUndone();

        System.out.println(SEPARATOR);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    public void addTodo(String input) throws CbException {
        if (input.trim().equals("todo")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String description = input.substring(4).trim();

        Task todo = new Todo(description);
        tasks.add(todo);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void addDeadline(String input) throws CbException {
        if (input.trim().equals("deadline")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String[] components = input.substring(8).trim().split(" /by ");

        if (components.length < 2) {
            throw new CbException("Error: The deadline cannot be empty!");
        }

        String description = components[0];
        LocalDateTime d = parser.parseDateTime(components[1]);

        Task dl = new Deadline(description, d);
        tasks.add(dl);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(dl);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void addEvent(String input) throws CbException {
        if (input.trim().equals("event")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String[] components = input.substring(5).trim().split(" /from ");

        if (components.length < 2) {
            throw new CbException("Error: The start time cannot be empty!");
        }

        String[] timeComponents = components[1].split(" /to ");

        if (timeComponents.length < 2) {
            throw new CbException("Error: The end time cannot be empty!");
        }

        LocalDateTime start = parser.parseDateTime(timeComponents[0]);
        LocalDateTime end = parser.parseDateTime(timeComponents[1]);

        Task event = new Event(components[0], start, end);
        tasks.add(event);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void delete(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.remove(task_id);

        System.out.println(SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println(target);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }
}
