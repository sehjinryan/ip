package b2;

import java.util.Scanner;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    public void validate(String input) throws CbException {
        if (input == null || input.trim().isEmpty()) {
            throw new CbException("Error: Input cannot be empty!");
        }
    }
    public void printIntro() {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void printExit() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    public boolean handleUserInput(Scanner scanner, TaskList taskList, Storage storage) {
        String input = scanner.nextLine().trim();

        try {
            validate(input);

            if (input.equals("list")) {
                taskList.listTasks();
                return true;
            }

            if (input.equals("bye")) {
                printExit();
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
