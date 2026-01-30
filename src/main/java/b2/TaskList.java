package b2;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * TaskList class to manage a list of task operations.
 * Operations include adding, deleting, marking tasks as done/undone, and listing tasks.
 * Utilizes the Parser class for dateTime parsing when adding deadlines and events.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private static final String SEPARATOR = "____________________________________________________________";
    private final Parser parser;

    /**
     * Constructs an empty TaskList.
     * Initializes the tasks ArrayList and Parser.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
        this.parser = new Parser();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     * Initializes the Parser.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.parser = new Parser();
    }

    /**
     * Returns the list of tasks.
     *
     * @return the ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Lists all tasks in the task list.
     * Displays each task's string representation with its index.
     */
    public void listTasks() {
        System.out.println(SEPARATOR);
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            System.out.println((i + 1) + "." + curr);
        }

        System.out.println(SEPARATOR);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param task_id the index of the task to mark as done
     * @throws CbException if the task_id is invalid
     */
    public void markTaskAsDone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.get(task_id);
        target.markAsDone();

        System.out.println(SEPARATOR);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param task_id the index of the task to mark as not done
     * @throws CbException if the task_id is invalid
     */
    public void markTaskAsUndone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.get(task_id);

        target.markAsUndone();

        System.out.println(SEPARATOR);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    /**
     * Adds a Todo task to the task list.
     *
     * @param input the input string containing the todo command and task description
     * @throws CbException if the description is empty
     */
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

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input the input string containing the deadline command, task description, and due dateTime
     * @throws CbException if the description or due dateTime is empty
     */
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

    /**
     * Adds an Event task to the task list.
     *
     * @param input the input string containing the event command, task description, start dateTime, and end dateTime
     * @throws CbException if the description, start dateTime, or end dateTime is empty
     */
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

    /**
     * Deletes the task at the given index from the task list.
     *
     * @param task_id the index of the task to delete
     * @throws CbException if the task_id is invalid
     */
    public void delete(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.remove(task_id);

        System.out.println(SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println(target);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }
}
