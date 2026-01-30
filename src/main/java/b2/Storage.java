package b2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private final Parser parser;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.parser = new Parser();
    }

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
                    LocalDateTime due = parser.parseDateTime(components[3]);
                    t = new Deadline(description, due);
                    break;
                case "E":
                    LocalDateTime start = parser.parseDateTime(components[3].split(" to ")[0]);
                    LocalDateTime end = parser.parseDateTime(components[3].split(" to ")[1]);
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
