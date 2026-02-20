package b2.parser;

import b2.exception.CbException;
import b2.storage.Storage;
import b2.task.Task;
import b2.task.TaskList;
import b2.ui.Ui;

/**
 * Responsible for interpreting user commands and executing the corresponding actions on the task list.
 * Interacts with TaskList to perform operations such as adding, marking, unmarking, deleting, finding, and editing tasks.
 * Communicates with Storage to save changes to the file and with the Ui class to generate messages.
 */
public class Parser {

    private final TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    public Parser(TaskList taskList, Storage storage, Ui ui) {
        this.taskList = taskList;
        this.storage = storage;
        this.ui = ui;
    }

    public String parseMarkCommand(String command) throws CbException {
        Task t = taskList.markTaskAsDone(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printMarkAsDoneMessage(t);
    }

    public String parseUnmarkCommand(String command) throws CbException {
        Task t = taskList.markTaskAsUndone(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printMarkAsUndoneMessage(t);
    }

    public String parseDeleteCommand(String command) throws CbException {
        Task t = taskList.deleteTask(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printDeleteMessage(t, taskList.getSize());
    }

    public String parseTodoCommand(String command) throws CbException {
        Task t = taskList.addTodo(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printAddTodoMessage(t, taskList.getSize());
    }

    public String parseDeadlineCommand(String command) throws CbException {
        Task t = taskList.addDeadline(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printAddDeadlineMessage(t, taskList.getSize());
    }

    public String parseEventCommand(String command) throws CbException {
        Task t = taskList.addEvent(command);
        storage.saveTasks(taskList.getTasks());
        return ui.printAddEventMessage(t, taskList.getSize());
    }

    public String parseEditCommand(String command) throws CbException {
        Task t = taskList.editTask(command);
        storage.saveTasks(taskList.getTasks());
        return "Noted. I've edited this task:\n" + t;
    }
}
