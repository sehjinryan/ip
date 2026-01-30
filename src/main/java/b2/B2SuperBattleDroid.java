package b2;

import java.util.Scanner;

/**
 * The main class for the B2 Super Battle Droid chatbot application.
 * It initializes the necessary components and runs the chatbot loop.
 * Allows users to manage tasks through a command-line interface.
 */
public class B2SuperBattleDroid {
    private static final String FILE_PATH = "data/tasks.txt";
    private static final String SEPARATOR = "____________________________________________________________";
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
     * Runs the main chatbot loop, handling user input until the user decides to exit.
     * Displays an introduction message and processes commands through the UI.
     */
    public void run() {
        ui.printIntro();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            isRunning = ui.handleUserInput(scanner, taskList, storage);
        }
    }

    /**
     * The main method to start the B2 Super Battle Droid chatbot application.
     * Creates an instance of the chatbot and runs it.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        B2SuperBattleDroid chatbot = new B2SuperBattleDroid(FILE_PATH);
        chatbot.run();
    }
}
