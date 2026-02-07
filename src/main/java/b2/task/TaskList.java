package b2.task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import b2.exception.CbException;
import b2.parser.Parser;

/**
 * TaskList class to manage a list of task operations.
 * Operations include adding, deleting, marking tasks as done/undone, and listing tasks.
 * Utilizes the Parser class for dateTime parsing when adding deadlines and events.
 */
public class TaskList {
    private ArrayList<Task> tasks;
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

    public int getSize() {
        return tasks.size();
    }

    /**
     * Lists all tasks in the task list.
     * Displays each task's string representation with its index.
     */
    public String listTasks() {
        String response = "Here are the tasks in your list:\n";

        for (int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            response += (i + 1) + "." + curr + "\n";
        }

        return response;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param taskId the index of the task to mark as done
     * @throws CbException if the taskId is invalid
     */
    public Task markTaskAsDone(int taskId) throws CbException {
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.get(taskId);
        target.markAsDone();

        return target;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param taskId the index of the task to mark as not done
     * @throws CbException if the taskId is invalid
     */
    public Task markTaskAsUndone(int taskId) throws CbException {
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.get(taskId);
        target.markAsUndone();

        return target;
    }

    /**
     * Adds a Todo task to the task list.
     *
     * @param input the input string containing the todo command and task description
     * @throws CbException if the description is empty
     */
    public Task addTodo(String input) throws CbException {
        if (input.trim().equals("todo")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String description = input.substring(4).trim();

        Task todo = new Todo(description);
        tasks.add(todo);

        return todo;
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input the input string containing the dueDateTime command, task description, and due dateTime
     * @throws CbException if the description or due dateTime is empty
     */
    public Task addDeadline(String input) throws CbException {
        if (input.trim().equals("dueDateTime")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String[] components = input.substring(8).trim().split(" /by ");

        if (components.length < 2) {
            throw new CbException("Error: The dueDateTime cannot be empty!");
        }

        String description = components[0];
        LocalDateTime d = parser.parseDateTime(components[1]);

        Task dl = new Deadline(description, d);
        tasks.add(dl);

        return dl;
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param input the input string containing the event command, task description, start dateTime, and end dateTime
     * @throws CbException if the description, start dateTime, or end dateTime is empty
     */
    public Task addEvent(String input) throws CbException {
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

        return event;
    }

    /**
     * Deletes the task at the given index from the task list.
     *
     * @param taskId the index of the task to delete
     * @throws CbException if the taskId is invalid
     */
    public Task deleteTask(int taskId) throws CbException {
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new CbException("Error: Invalid task id!");
        }

        Task target = tasks.remove(taskId);

        return target;
    }

    public String findTask(String keyword) {
        String response = "Here are the matching tasks in your list:\n";

        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            if (curr.getDescription().contains(keyword)) {
                response += (count + 1) + "." + curr + "\n";
                count++;
            }
        }

        if (count == 0) {
            response += "No matching tasks found.\n";
        }

        return response;
    }
}
