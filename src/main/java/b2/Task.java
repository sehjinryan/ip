package b2;

import java.time.format.DateTimeFormatter;

/**
 * Represents a general task with a description and completion status.\
 * This is the base class for specific task types like Todo, Deadline, and Event.
 * Extends to include task-specific information.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    /**
     * Constructs a Task with the specified description.
     * Task is initially marked as not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Returns a string representation of the task for saving to a file.
     *
     * @return a string in the format "isDone | description"
     */
    public String toSaveString() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}
