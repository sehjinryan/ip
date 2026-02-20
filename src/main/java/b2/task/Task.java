package b2.task;

import java.time.format.DateTimeFormatter;

/** Represents a general task with a description and completion status.
 * Base class for specific types of tasks such as Todo, Deadline, and Event.
 * Provides common functionality for managing the task's description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    /** Constructs a Task with the specified description and sets it as not done by default.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Edits the description of the task.
     *
     * @param newDescription The new description to replace the old one.
     */
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Returns a string representation of the task for display.
     *
     * @return a string in the format "[status] description"
     */
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
