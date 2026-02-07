package b2.task;

import java.time.LocalDateTime;

/**
 * Represents an event task with a description, start dateTime and end dateTime.
 * Extends the Task class to include event-specific information.
 */
public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructs an Event task with the specified description, start time, and end time.
     *
     * @param description the task description
     * @param start the start date and time of the event
     * @param end the end date and time of the event
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + getStatusIcon() + " " + description + " (from: " + start.format(DISPLAY_FORMAT) + " to: " + end.format(DISPLAY_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the event task for saving to a file.
     *
     * @return a string in the format "E | isDone | description | start to end"
     */
    @Override
    public String toSaveString() {
        return "E | " + super.toSaveString() + " | " + start + " to " + end;
    }
}
