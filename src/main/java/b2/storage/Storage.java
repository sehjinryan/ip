package b2.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import b2.exception.CbException;
import b2.parser.DateTimeParser;
import b2.task.Deadline;
import b2.task.Event;
import b2.task.Task;
import b2.task.Todo;

/**
 * Handles loading and saving tasks to and from a file.
 * Provides methods to read tasks from a specified file path and write tasks back to the file.
 * Utilizes the Parser class to parse dateTime strings.
 */
public class Storage {
    private final String filePath;
    private final DateTimeParser dateTimeParser;

    /**
     * Constructs a Storage object with the specified file path.
     * Initializes the Parser for dateTime parsing.
     *
     * @param filePath the path to the file where tasks are stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.dateTimeParser = new DateTimeParser();
    }

    /**
     * Loads tasks from the specified file.
     * Reads each line, parses the task information and constructs Task objects accordingly.
     * Stores the loaded tasks in an ArrayList and returns it.
     *
     * @return an ArrayList of loaded Task objects
     * @throws CbException if there is an error during loading
     */
    public ArrayList<Task> loadTasks() throws CbException {
        ArrayList<Task> loadedTasks = new ArrayList<Task>();
        File f = new File(filePath);

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
                    LocalDateTime due = dateTimeParser.parseDateTime(components[3]);
                    t = new Deadline(description, due);
                    break;
                case "E":
                    LocalDateTime start = dateTimeParser.parseDateTime(components[3].split(" to ")[0]);
                    LocalDateTime end = dateTimeParser.parseDateTime(components[3].split(" to ")[1]);
                    t = new Event(description, start, end);
                    break;
                }

                if (isDone) {
                    t.markAsDone();
                }

                loadedTasks.add(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: tasks file not found! Creating one for you...");
        } catch (CbException e) {
            System.out.println("Error: Unable to load tasks! " + e.getMessage());
        }

        return loadedTasks;
    }

    /**
     * Saves the given list of tasks to the specified file.
     * Appends each task's string representation to the file, one per line.
     *
     * @param tasks the ArrayList of Task objects to be saved
     * @throws CbException if there is an error during saving
     */
    public void saveTasks(ArrayList<Task> tasks) throws CbException {
        try {
            File f = new File(filePath);

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
}
