package b2;

import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + deadline.format(DISPLAY_FORMAT) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + deadline;
    }
}

