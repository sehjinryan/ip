package b2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void parseDateTime_validInput_success() throws CbException {
        Parser parser = new Parser();

        String isoInput = "2026-01-30T14:30:00";
        assertEquals("2026-01-30T14:30", parser.parseDateTime(isoInput).toString());

        String slashInput = "30/01/2026 1430";
        assertEquals("2026-01-30T14:30", parser.parseDateTime(slashInput).toString());

        String dashInput = "30-01-2026 1430";
        assertEquals("2026-01-30T14:30", parser.parseDateTime(dashInput).toString());
    }

    @Test
    public void parseDateTime_invalidInput_exceptionThrown() {
        Parser parser = new Parser();

        String[] invalidInputs = {
            "30/01/26",
            "1430",
            "Random text",
            "30.01.2026 14:30",
            "01/30/2026 1430"
        };

        for (String invalidInput : invalidInputs) {
            try {
                parser.parseDateTime(invalidInput);
                fail("Expected CbException to be thrown for input: " + invalidInput);
            } catch (CbException e) {
                assertEquals("Error: Invalid date/time format! Accepted formats are dd/MM/yyyy HHmm or dd-MM-yyyy HHmm", e.getMessage());
            }
        }
    }
}
