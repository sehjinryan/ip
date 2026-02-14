package b2;

import b2.exception.CbException;
import b2.parser.DateTimeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DateTimeParserTest {

    @Test
    public void parseDateTime_validInput_success() throws CbException {
        DateTimeParser dateTimeParser = new DateTimeParser();

        String isoInput = "2026-01-30T14:30:00";
        assertEquals("2026-01-30T14:30", dateTimeParser.parseDateTime(isoInput).toString());

        String slashInput = "30/01/2026 1430";
        assertEquals("2026-01-30T14:30", dateTimeParser.parseDateTime(slashInput).toString());

        String dashInput = "30-01-2026 1430";
        assertEquals("2026-01-30T14:30", dateTimeParser.parseDateTime(dashInput).toString());
    }

    @Test
    public void parseDateTime_invalidInput_exceptionThrown() {
        DateTimeParser dateTimeParser = new DateTimeParser();

        String[] invalidInputs = {
            "30/01/26",
            "1430",
            "Random text",
            "30.01.2026 14:30",
            "01/30/2026 1430"
        };

        for (String invalidInput : invalidInputs) {
            try {
                dateTimeParser.parseDateTime(invalidInput);
                fail("Expected CbException to be thrown for input: " + invalidInput);
            } catch (CbException e) {
                assertEquals("Error: Invalid date/time format! Accepted formats are dd/MM/yyyy HHmm or dd-MM-yyyy HHmm", e.getMessage());
            }
        }
    }
}
