package b2.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import b2.exception.CbException;

/**
 * DateTimeParser class to handle parsing of date and time strings into LocalDateTime objects.
 * Supports multiple date/time formats.
 */
public class DateTimeParser {
    private static final DateTimeFormatter[] FORMATS = {
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
    };

    /**
     * Parses the input string into a LocalDateTime object if it matches any of the predefined formats.
     *
     * @param input the dateTime string to parse
     * @return the parsed LocalDateTime object
     * @throws CbException if the input string does not match any supported format
     */
    public LocalDateTime parseDateTime(String input) throws CbException {
        String dateTime = input.trim();

        for (DateTimeFormatter format : FORMATS) {
            try {
                return LocalDateTime.parse(dateTime, format);
            } catch (DateTimeParseException ignored) {}
        }

        throw new CbException("Error: Invalid date/time format! Accepted formats are dd/MM/yyyy HHmm or dd-MM-yyyy HHmm");
    }

}
