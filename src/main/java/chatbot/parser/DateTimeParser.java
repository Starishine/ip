package chatbot.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chatbot.exceptions.LeoException;

/**
 * Utility class for parsing date and time strings into LocalDateTime objects.
 * Supports formats "yyyy-MM-dd HHmm" and "yyyy-MM-dd".
 */
public class DateTimeParser {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Parses a date string into a LocalDateTime object.
     * Accepts formats "yyyy-MM-dd HHmm" or "yyyy-MM-dd".
     * If only date is provided, time defaults to 00:00.
     *
     * @param dateString The input date string.
     * @param fieldName  The name of the field for error messages.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws LeoException If the input format is invalid or empty.
     */
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
