package b2;

import java.util.Scanner;

import b2.exception.CbException;
import b2.parser.Parser;
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
    private Parser parser;

    /**
     * Constructs a B2SuperBattleDroid instance with the specified file path for storage.
     * Initializes the storage, UI, and task list components.
     *
     * @param filePath The file path where tasks are stored.
     */
    public B2SuperBattleDroid(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.trim().isEmpty() : "File path should not be empty";

        this.storage = new Storage(filePath);
        this.ui = new Ui();

        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (CbException e) {
            taskList = new TaskList();
        }

        this.parser = new Parser(taskList, storage, ui);
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

            String commandWord = input.split(" ")[0];

            return switch (commandWord) {
                case "command" -> ui.printCommands();
                case "bye" -> ui.printExit();
                case "list" -> taskList.listTasks();
                case "mark" -> parser.parseMarkCommand(input);
                case "unmark" -> parser.parseUnmarkCommand(input);
                case "todo" -> parser.parseTodoCommand(input);
                case "deadline" -> parser.parseDeadlineCommand(input);
                case "event" -> parser.parseEventCommand(input);
                case "delete" -> parser.parseDeleteCommand(input);
                case "find" -> parser.parseFindCommand(input);
                case "edit" -> parser.parseEditCommand(input);
                default -> "Error: invalid command!";
            };
        } catch (CbException e) {
            return e.getMessage();
        }
    }
}
