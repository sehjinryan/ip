package b2;

import b2.exception.CbException;
import b2.storage.Storage;
import b2.task.TaskList;
import b2.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class UiTest {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        File f = new File("data/test_tasks.txt");

        if (f.exists()) {
            f.delete();
        }

        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage("data/test_tasks.txt");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void validate_emptyInput_exceptionThrown() {
        Ui ui = new Ui();

        String[] invalidInputs = {
            "",
            "   ",
            null
        };

        for (String invalidInput : invalidInputs) {
            try {
                ui.validate(invalidInput);
                fail("Expected CbException to be thrown for input: " + invalidInput);
            } catch (CbException e) {
                assertEquals("Error: Input cannot be empty!", e.getMessage());
            }
        }
    }

    @Test
    public void handleUserInput_addTodoCommand_success() throws CbException {
        Scanner scanner = new Scanner("todo Read book\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Got it. I've added this task:\n" +
                "[T][ ] Read book\n" +
                "Now you have 1 tasks in the list.\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void handleUserInput_addTaskNoDescription_exceptionThrown() {
        Scanner scanner = new Scanner("todo \n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Error: The description cannot be empty!\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }


    @Test
    public void handleUserInput_addDeadlineCommand_success() throws CbException {
        Scanner scanner = new Scanner("dueDateTime Submit assignment /by 30/01/2026 2359\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Got it. I've added this task:\n" +
                "[D][ ] Submit assignment (by: Jan 30 2026 2359)\n" +
                "Now you have 1 tasks in the list.\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void handleUserInput_addEventCommand_success() throws CbException {
        Scanner scanner = new Scanner("event Team meeting /from 31/01/2026 1400 /to 31/01/2026 1600\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Got it. I've added this task:\n" +
                "[E][ ] Team meeting (from: Jan 31 2026 1400 to: Jan 31 2026 1600)\n" +
                "Now you have 1 tasks in the list.\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void handleUserInput_addTaskNoDateTime_exceptionThrown() {
        Scanner scanner = new Scanner("dueDateTime Submit assignment\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Error: The dueDateTime cannot be empty!\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void handleUserInput_listCommand_success() throws CbException {
        taskList.addTodo("todo Read book");
        taskList.addDeadline("dueDateTime Submit assignment /by 30/01/2026 2359");
        taskList.addEvent("event Team meeting /from 31/01/2026 1400 /to 31/01/2026 1600");
        outContent.reset();

        Scanner scanner = new Scanner("list\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Here are the tasks in your list:\n" +
                "1.[T][ ] Read book\n" +
                "2.[D][ ] Submit assignment (by: Jan 30 2026 2359)\n" +
                "3.[E][ ] Team meeting (from: Jan 31 2026 1400 to: Jan 31 2026 1600)\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void handleUserInput_invalidCommand_printErrorMessage() {
        Scanner scanner = new Scanner("invalid command\n");
        ui.handleUserInput(scanner, taskList, storage);

        String expectedOutput = "____________________________________________________________\n" +
                "Error: invalid command!\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
