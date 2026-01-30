import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class B2SuperBattleDroid {
    private static final String FILE_PATH = "data/tasks.txt";
    private static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<Task>();

    private void loadTasks() {
        File f = new File(FILE_PATH);
        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] components = line.split(" \\| ");
                String taskType = components[0];
                boolean isDone = components[1].equals("1");
                String description = components[2];

                Task t = null;

                switch (taskType) {
                    case "T":
                        t = new Todo(description);
                        break;
                    case "D":
                        t = new Deadline(description, components[3]);
                        break;
                    case "E":
                        String start = components[3].split(" to ")[0];
                        String end = components[3].split(" to ")[1];
                        t = new Event(description, start, end);
                        break;
                }

                if (isDone) {
                    t.markAsDone();
                }

                tasks.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: tasks file not found!");
        }
    }

    private void saveTasks() {
        try {
            File f = new File(FILE_PATH);

            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }

            FileWriter fw = new FileWriter(f, false);

            for (Task curr : tasks) {
                fw.write(curr.toSaveString() + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks!");

        }
    }

    public void intro() {
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm B2 Super Battle Droid");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public void listTasks() {
        System.out.println(SEPARATOR);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task curr = tasks.get(i);
            System.out.println((i + 1) + "." + curr);
        }
        System.out.println(SEPARATOR);
    }

    public void markTaskAsDone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.get(task_id);
        target.markAsDone();

        System.out.println(SEPARATOR);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

    public void markTaskAsUndone(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.get(task_id);

        target.markAsUndone();

        System.out.println(SEPARATOR);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(target);
        System.out.println(SEPARATOR);
    }

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

    public void addDeadline(String input) throws CbException {
        if (input.trim().equals("deadline")) {
            throw new CbException("Error: The description cannot be empty!");
        }

        String[] components = input.substring(8).trim().split(" /by ");

        if (components.length < 2) {
            throw new CbException("Error: The deadline cannot be empty!");
        }

        Task dl = new Deadline(components[0], components[1]);
        tasks.add(dl);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(dl);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

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

        Task event = new Event(components[0], timeComponents[0], timeComponents[1]);
        tasks.add(event);

        System.out.println(SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void exit() {
        System.out.println(SEPARATOR);
        System.out.println("Bye. hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    public void validate(String input) throws CbException {
        if (input == null || input.trim().isEmpty()) {
            throw new CbException("Error: Input cannot be empty!");
        }
    }

    public void delete(int task_id) throws CbException {
        if (task_id < 0 || task_id >= tasks.size()) {
            throw new CbException("Error: Invalid task!");
        }

        Task target = tasks.remove(task_id);

        System.out.println(SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println(target);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public static void main(String[] args) {
        B2SuperBattleDroid chatbot = new B2SuperBattleDroid();

        chatbot.loadTasks();
        chatbot.intro();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                chatbot.validate(input);
            } catch (CbException e) {
                System.out.println(SEPARATOR);
                System.out.println(e.getMessage());
                System.out.println(SEPARATOR);
                continue;
            }

            if (input.equals("list")) {
                chatbot.listTasks();
                continue;
            }

            if (input.startsWith("mark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;

                try {
                    chatbot.markTaskAsDone(task_id);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.startsWith("unmark")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;

                try {
                    chatbot.markTaskAsUndone(task_id);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.startsWith("todo")) {
                try {
                    chatbot.addTodo(input);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.startsWith("deadline")) {
                try {
                    chatbot.addDeadline(input);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.startsWith("event")) {
                try {
                    chatbot.addEvent(input);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.startsWith("delete")) {
                String[] components = input.split(" ");
                int task_id = Integer.parseInt(components[1]) - 1;

                try {
                    chatbot.delete(task_id);
                    chatbot.saveTasks();
                } catch (CbException e) {
                    System.out.println(SEPARATOR);
                    System.out.println(e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }

                continue;
            }

            if (input.equals("bye")) {
                chatbot.exit();
                break;
            }

            try {
                throw new CbException("Error: invalid command!");
            } catch (CbException e) {
                System.out.println(SEPARATOR);
                System.out.println(e.getMessage());
                System.out.println(SEPARATOR);
            }
        }
    }
}
