package b2;

import java.util.Scanner;

import b2.exception.CbException;
import b2.storage.Storage;
import b2.task.Task;
import b2.task.TaskList;
import b2.ui.Ui;

/**
 * The main class for the B2 Super Battle Droid chatbot application.
 * It initializes the necessary components and runs the chatbot loop.
 * Allows users to manage tasks through a command-line interface.
 */
public class B2SuperBattleDroid {
    private static final String FILE_PATH = "data/tasks.txt";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * Constructs a B2SuperBattleDroid instance with the specified file path for storage.
     * Initializes the storage, UI, and task list components.
     *
     * @param filePath The file path where tasks are stored.
     */
    public B2SuperBattleDroid(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();

        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (CbException e) {
            taskList = new TaskList();
        }
    }

    /**
     * Gets the response from B2 Super Battle Droid for a given user input.
     * Handles various commands such as listing tasks, adding tasks, marking tasks as done/undone, deleting tasks, and finding tasks.
     *
     * @param input The user input command.
     * @return The response string from B2 Super Battle Droid.
     */
    public String getResponse(String input) {
        try {
            ui.validate(input);

            if (input.equals("list")) {
                return taskList.listTasks();
            }

            if (input.equals("bye")) {
                return ui.printExit();
            }

            if (input.startsWith("mark")) {
                String[] components = input.split(" ");
                int taskId = Integer.parseInt(components[1]) - 1;
                Task t = taskList.markTaskAsDone(taskId);
                storage.saveTasks(taskList.getTasks());
                return ui.printMarkAsDoneMessage(t);
            }

            if (input.startsWith("unmark")) {
                String[] components = input.split(" ");
                int taskId = Integer.parseInt(components[1]) - 1;
                Task t = taskList.markTaskAsUndone(taskId);
                storage.saveTasks(taskList.getTasks());
                return ui.printMarkAsUndoneMessage(t);
            }

            if (input.startsWith("todo")) {
                Task t = taskList.addTodo(input);
                storage.saveTasks(taskList.getTasks());
                return ui.printAddTodoMessage(t, taskList.getSize());
            }

            if (input.startsWith("dueDateTime")) {
                Task t = taskList.addDeadline(input);
                storage.saveTasks(taskList.getTasks());
                return ui.printAddDeadlineMessage(t, taskList.getSize());
            }

            if (input.startsWith("event")) {
                Task t = taskList.addEvent(input);
                storage.saveTasks(taskList.getTasks());
                return ui.printAddEventMessage(t, taskList.getSize());
            }

            if (input.startsWith("delete")) {
                String[] components = input.split(" ");
                int taskId = Integer.parseInt(components[1]) - 1;
                Task t = taskList.deleteTask(taskId);
                storage.saveTasks(taskList.getTasks());
                return ui.printDeleteMessage(t, taskList.getSize());
            }

            if (input.startsWith("find")) {
                String[] components = input.split(" ");
                String keyword = components[1];
                return taskList.findTask(keyword);
            }

            return "Error: invalid command!";

        } catch (CbException e) {
            return e.getMessage();
        }
    }
}
