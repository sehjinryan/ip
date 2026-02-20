package b2.ui;

import b2.exception.CbException;
import b2.task.Task;

/**
 * Responsible for handling user interactions and generating messages to be displayed to the user.
 * Provides methods to validate user input and to create messages for various actions such as listing commands,
 * marking tasks as done or undone, adding tasks, and deleting tasks.
 */
public class Ui {

    /**
     * Validates the user input to ensure it is not empty or just whitespace.
     *
     * @param input the user input to validate
     * @throws CbException if the input is empty or only whitespace
     */
    public void validate(String input) throws CbException {
        if (input == null || input.trim().isEmpty()) {
            throw new CbException("Error: Input cannot be empty!");
        }
    }

    public String printCommands() {
        return "Here are the available commands:\n"
                + "1. list: List all tasks\n"
                + "2. mark <taskId>: Mark a task as done\n"
                + "3. unmark <taskId>: Mark a task as not done\n"
                + "4. delete <taskId>: Delete a task\n"
                + "5. todo <description>: Add a Todo task\n"
                + "6. deadline <description> /by <dateTime>: Add a Deadline task\n"
                + "7. event <description> /from <dateTime> /to <dateTime>: Add an Event task\n"
                + "8. find <keyword>: Find tasks containing the keyword\n"
                + "9. command: Show this list of commands\n"
                + "10. bye: Exit the application\n"
                + "11. edit <taskId> <field: description/by/from/to> <newValue>: Edit a task's description or dateTime.";
    }

    public String printExit() {
        return "Bye. hope to see you again soon!";
    }

    public String printMarkAsDoneMessage(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    public String printMarkAsUndoneMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    public String printAddTodoMessage(Task todo, int size) {
        return "Got it. I've added this task:\n" + todo + "\nNow you have " + size + " tasks in the list.";
    }

    public String printAddDeadlineMessage(Task deadline, int size) {
        return "Got it. I've added this task:\n" + deadline + "\nNow you have " + size + " tasks in the list.";
    }

    public String printAddEventMessage(Task event, int size) {
        return "Got it. I've added this task:\n" + event + "\nNow you have " + size + " tasks in the list.";
    }

    public String printDeleteMessage(Task task, int size) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }
}
