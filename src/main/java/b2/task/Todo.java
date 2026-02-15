package b2.task;

/**
 * Represents a todo task with a description.
 * Extends the Task class to include todo-specific information.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description the task description
     */
    public Todo(String description) {
        super(description);
    }

    /** Returns a string representation of the todo task for display.
     *
     * @return a string in the format "[T][status] description"
     */
    @Override
    public String toString() {
        return "[T]" + getStatusIcon() + " " + description;
    }

    /**
     * Returns a string representation of the todo task for saving to a file.
     *
     * @return a string in the format "T | isDone | description"
     */
    @Override
    public String toSaveString() {
        return "T | " + super.toSaveString();
    }
}
