package b2.task;

import java.time.LocalDateTime;

/**
 * Represents a dueDateTime task with a description and a dueDateTime date and time.
 * Extends the Task class to include dueDateTime-specific information.
 */
public class Deadline extends Task {
    protected LocalDateTime dueDateTime;

    /**
     * Constructs a Deadline task with the specified description and dueDateTime.
     *
     * @param description the task description
     * @param deadline the dueDateTime consisting of date and time
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.dueDateTime = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + dueDateTime.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the dueDateTime task for saving to a file.
     *
     * @return a string in the format "D | isDone | description | dueDateTime"
     */
    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + dueDateTime;
    }
}

