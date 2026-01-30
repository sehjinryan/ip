package b2;

import java.time.LocalDateTime;

/**
 * Represents a deadline task with a description and a deadline date and time.
 * Extends the Task class to include deadline-specific information.
 */
public class Deadline extends Task {
    protected LocalDateTime deadline;

    /**
     * Constructs a Deadline task with the specified description and deadline.
     *
     * @param description the task description
     * @param deadline the deadline consisting of date and time
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + deadline.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the deadline task for saving to a file.
     *
     * @return a string in the format "D | isDone | description | deadline"
     */
    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + deadline;
    }
}

