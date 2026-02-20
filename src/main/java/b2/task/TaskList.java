package b2.task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import b2.exception.CbException;
import b2.parser.DateTimeParser;

/**
 * TaskList class to manage a list of task operations.
 * Operations include adding, deleting, marking tasks as done/undone, and listing tasks.
 * Utilizes the Parser class for dateTime parsing when adding deadlines and events.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private final DateTimeParser dateTimeParser;

    /**
     * Constructs an empty TaskList.
     * Initializes the tasks ArrayList and Parser.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
        this.dateTimeParser = new DateTimeParser();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     * Initializes the Parser.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list should not be null";
        this.tasks = tasks;
        this.dateTimeParser = new DateTimeParser();
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
     * Parses the taskId from the input string and validates it.
     * Ensures that the taskId is a valid integer and within the bounds of the task list.
     *
     * @param taskIdStr the string representation of the taskId
     * @return the parsed taskId as an integer
     * @throws CbException if the taskId is not a valid number or is out of bounds
     */
    private int parseTaskId(String taskIdStr) throws CbException {
        try {
            int taskId = Integer.parseInt(taskIdStr) - 1;

            if (taskId < 0 || taskId >= tasks.size()) {
                throw new CbException("Error: Invalid taskId!");
            }

            return taskId;
        } catch (NumberFormatException e) {
            throw new CbException("Error: TaskId must be a number! Usage: mark <taskId>");
        }
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
     * @param input the input string containing the mark command and task index
     * @throws CbException if the taskId is invalid
     */
    public Task markTaskAsDone(String input) throws CbException {
        String[] components = input.split(" ");

        if (components.length != 2) {
            throw new CbException("Error: Invalid command format! Usage: mark <taskId>");
        }

        int taskId = parseTaskId(components[1]);

        Task target = tasks.get(taskId);
        target.markAsDone();

        return target;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param input the input string containing the unmark command and task index
     * @throws CbException if the taskId is invalid
     */
    public Task markTaskAsUndone(String input) throws CbException {
        String[] components = input.split(" ");

        if (components.length != 2) {
            throw new CbException("Error: Invalid command format! Usage: unmark <taskId>");
        }

        int taskId = parseTaskId(components[1]);

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
            throw new CbException("Error: The description cannot be empty! Usage: todo <description>");
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

        if (input.trim().equals("deadline")) {
            throw new CbException("Error: The description cannot be empty! Usage: deadline <description> /by <dateTime>");
        }

        String[] components = input.substring(8).trim().split(" /by ");

        if (components.length < 2) {
            throw new CbException("Error: The due dateTime cannot be empty! Usage: deadline <description> /by <dateTime>");
        }

        String description = components[0];
        LocalDateTime d = dateTimeParser.parseDateTime(components[1]);

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
            throw new CbException("Error: The description cannot be empty! Usage: event <description> /from <dateTime> /to <dateTime>");
        }

        String[] components = input.substring(5).trim().split(" /from ");

        if (components.length < 2) {
            throw new CbException("Error: The start time cannot be empty! Usage: event <description> /from <dateTime> /to <dateTime>");
        }

        String[] timeComponents = components[1].split(" /to ");

        if (timeComponents.length < 2) {
            throw new CbException("Error: The end time cannot be empty! Usage: event <description> /from <dateTime> /to <dateTime>");
        }

        LocalDateTime start = dateTimeParser.parseDateTime(timeComponents[0]);
        LocalDateTime end = dateTimeParser.parseDateTime(timeComponents[1]);

        Task event = new Event(components[0], start, end);
        tasks.add(event);

        return event;
    }

    /**
     * Deletes the task at the given index from the task list.
     *
     * @param input the input string containing the delete command and task index
     * @throws CbException if the taskId is invalid
     */
    public Task deleteTask(String input) throws CbException {
        String[] components = input.split(" ");

        if (components.length != 2) {
            throw new CbException("Error: Invalid command format! Usage: delete <taskId>");
        }

        int taskId = parseTaskId(components[1]);

        Task target = tasks.remove(taskId);

        return target;
    }

    /**
     * Finds tasks in the task list that contain the given keyword in their description.
     * Returns a string listing the matching tasks, or a message if no matches are found.
     *
     * @param input the input string containing the find command and keyword
     * @throws CbException if the command format is invalid
     */
    public String findTask(String input) throws CbException {
        String[] components = input.split(" ");

        if (components.length != 2) {
            throw new CbException("Error: Invalid command format! Usage: find <keyword>");
        }

        String keyword = components[1];
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

    /**
     * Edits the specified component of a task at the given index.
     * Component can be the description, due dateTime (for Deadline), or start/end dateTime (for Event).
     *
     * @param input the input string containing the edit command, task index, component to edit, and new value
     * @throws CbException if the command format is invalid, taskId is invalid, or component is invalid
     */
    public Task editTask(String input) throws CbException {
        String components[] = input.split(" ", 4);

        if (components.length != 4) {
            throw new CbException("Error: Invalid command format! Usage: edit <taskId> <field: description/by/from/to> <newValue>");
        }

        int taskId = parseTaskId(components[1]);
        String component = components[2];
        String updatedInfo = components[3];

        if (taskId < 0 || taskId >= tasks.size()) {
            throw new CbException("Error: Invalid taskId!");
        }

        if (component == null || component.trim().equals("")) {
            throw new CbException("Error: Component to edit cannot be empty! Usage: edit <taskId> <field: description/by/from/to> <newValue>");
        }

        if (updatedInfo == null || updatedInfo.trim().equals("")) {
            throw new CbException("Error: Updated information cannot be empty! Usage: edit <taskId> <field: description/by/from/to> <newValue>");
        }

        Task target = tasks.get(taskId);

        switch (component) {
        case "description":
            target.editDescription(updatedInfo);
            break;
        case "by":
            if (target instanceof Deadline) {
                Deadline dl = (Deadline) target;
                dl.editDueDateTime(dateTimeParser.parseDateTime(updatedInfo));
            } else {
                throw new CbException("Error: Only Deadline tasks have a due dateTime!");
            }
            break;
        case "from":
            if (target instanceof Event) {
                Event event = (Event) target;
                event.editStart(dateTimeParser.parseDateTime(updatedInfo));
            } else {
                throw new CbException("Error: Only Event tasks have a start dateTime!");
            }
            break;
        case "to":
            if (target instanceof Event) {
                Event event = (Event) target;
                event.editEnd(dateTimeParser.parseDateTime(updatedInfo));
            } else {
                throw new CbException("Error: Only Event tasks have an end dateTime!");
            }
            break;
        default:
            throw new CbException("Error: Invalid component to edit! Valid components are: description, by, from, to.");
        }

        return target;
    }
}
