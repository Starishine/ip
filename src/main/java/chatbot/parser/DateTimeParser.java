package chatbot.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import chatbot.exceptions.LeoException;

public class DateTimeParser {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDateTime parseDateTime (String dateString, String fieldName) throws LeoException {
        try {
            return LocalDateTime.parse(dateString, java.time.format.DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (DateTimeParseException e) {
            try {
                // Try parsing as date only and set time to 00:00
                return LocalDateTime.of(java.time.LocalDate.parse(dateString,
                        java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT)),
                        java.time.LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ex) {
                throw new LeoException("UH-OH!!! The " + fieldName + " format is invalid. "
                        + "Please use YYYY-MM-DD or YYYY-MM-DD HHMM format.");
            }
        }
    }
}
