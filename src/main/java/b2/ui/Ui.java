package b2.ui;

import b2.exception.CbException;
import b2.task.Task;

public class Ui {

    public void validate(String input) throws CbException {
        if (input == null || input.trim().isEmpty()) {
            throw new CbException("Error: Input cannot be empty!");
        }
    }

    public String printExit() {
        return "Bye. hope to see you again soon!";
    }

    public String printMarkAsDoneMessage(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    public String printMarkAsUndoneMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    public String printAddTodoMessage(Task todo, int size) {
        return "Got it. I've added this task:\n" + todo + "\nNow you have " + size + " tasks in the list.";
    }

    public String printAddDeadlineMessage(Task deadline, int size) {
        return "Got it. I've added this task:\n" + deadline + "\nNow you have " + size + " tasks in the list.";
    }

    public String printAddEventMessage(Task event, int size) {
        return "Got it. I've added this task:\n" + event + "\nNow you have " + size + " tasks in the list.";
    }

    public String printDeleteMessage(Task task, int size) {
        return "Noted. I've removed this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }
}
