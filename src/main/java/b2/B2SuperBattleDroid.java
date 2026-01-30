package b2;

import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String FILE_PATH = "data/tasks.txt";
    private static final String SEPARATOR = "____________________________________________________________";
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public B2SuperBattleDroid(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();

        try {
            taskList = new TaskList(storage.loadTasks());
        } catch (CbException e) {
            taskList = new TaskList();
        }
    }

    public void run() {
        ui.printIntro();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            isRunning = ui.handleUserInput(scanner, taskList, storage);
        }
    }

    public static void main(String[] args) {
        B2SuperBattleDroid chatbot = new B2SuperBattleDroid(FILE_PATH);
        chatbot.run();
    }
}
