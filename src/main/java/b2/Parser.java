package b2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter[] FORMATS = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
    };

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
