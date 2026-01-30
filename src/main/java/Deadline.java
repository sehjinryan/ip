public class Deadline extends Task {
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + description + " (by: " + deadline + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + deadline;
    }
}

