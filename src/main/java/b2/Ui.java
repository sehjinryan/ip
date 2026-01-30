package b2;

import java.util.Scanner;

/**
 * Ui class to handle user interactions with the chatbot.
 * Provides methods for displaying intro and exit messages,
 * validating user input, and processing commands.
 */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    /**
     * Validates the user input to ensure it is not empty.
     *
     * @param input the user input string
     * @throws CbException if the input is empty
     */
    public void validate(String input) throws CbException {
        if (input == null || input.trim().isEmpty()) {
            throw new CbException("Error: Input cannot be empty!");
        }
    }

    /**
     * Displays the introduction message to the user.
     */
    public void intro() {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    /**
     * Displays the exit message to the user.
     */
    public void exit() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    /**
     * Handles user input and processes commands accordingly.
     * Supports commands list, bye, mark, unmark, todo, deadline, event, and delete.
     * Displays appropriate messages for each command and updates the task list and storage if needed.
     *
     * @param scanner  the Scanner object to read user input
     * @param taskList the TaskList object to manage tasks
     * @param storage  the Storage object to update tasks
     * @return true if the chatbot should continue running, false to exit
     */
    public boolean handleUserInput(Scanner scanner, TaskList taskList, Storage storage) {
        String input = scanner.nextLine().trim();

        try {
            validate(input);

            if (input.equals("list")) {
                taskList.listTasks();
                return true;
            }

            if (input.equals("bye")) {
                exit();
                return false;
            }

            if (input.startsWith("mark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                taskList.markTaskAsDone(task_id);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            if (input.startsWith("unmark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                taskList.markTaskAsUndone(task_id);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            if (input.startsWith("todo")) {
                taskList.addTodo(input);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            if (input.startsWith("deadline")) {
                taskList.addDeadline(input);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            if (input.startsWith("event")) {
                taskList.addEvent(input);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            if (input.startsWith("delete")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;
                taskList.delete(task_id);
                storage.saveTasks(taskList.getTasks());
                return true;
            }

            System.out.println(SEPARATOR);
            System.out.println("Error: invalid command!");
            System.out.println(SEPARATOR);
            return true;

        } catch (CbException e) {
            System.out.println(SEPARATOR);
            System.out.println(e.getMessage());
            System.out.println(SEPARATOR);
            return true;
        }
    }
}
