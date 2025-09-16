package chatbot.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatbot.exceptions.LeoException;

public class DateTimeParser {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDateTime parseDateTime(String dateString, String fieldName) throws LeoException {
        // Guard: empty/null input
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new LeoException("UH-OH!!! The " + fieldName + " cannot be empty.");
        }

        // cut spaces : "2025-09-16   1230" -> "2025-09-16 1230")
        String s = dateString.trim().replaceAll("\\s+", " ");

        // Try full datetime first
        try {
            return LocalDateTime.parse(s, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ignored) {
            // fall through to date-only
        }

        // Try date-only next (set time to 00:00)
        try {
            LocalDate d = LocalDate.parse(s, DATE_FORMATTER);
            return LocalDateTime.of(d, LocalTime.MIDNIGHT);
        } catch (DateTimeParseException ex) {
            throw new LeoException("UH-OH!!! The " + fieldName + " format is invalid. "
                    + "Please use YYYY-MM-DD or YYYY-MM-DD HHMM format. Invalid input: '" + s + "'");
        }
    }
}