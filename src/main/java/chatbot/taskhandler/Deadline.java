package chatbot.taskhandler;

import chatbot.exceptions.LeoException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * A Deadline object contains the task name and the due date.
 */
public class Deadline extends Task {
    private final String stringDueDate;
    private final LocalDateTime dueDate;

    /**
     * Constructs a Deadline object with the specified name and due date.
     *
     * @param name    The name of the task.
     * @param dueDate The due date of the task in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @throws LeoException If the due date format is invalid.
     */
    public Deadline(String name, String dueDate) throws LeoException {
        super(name);
        this.stringDueDate = dueDate;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime parsedDueDate;

        try {
            parsedDueDate = LocalDateTime.parse(dueDate, dateTimeFormatter);
        } catch (DateTimeException e) {
            // if fails, try to parse as LocalDate only and set time to 00:00
            try {
                parsedDueDate = LocalDateTime.of(LocalDate.parse(dueDate, dateFormatter),
                        java.time.LocalTime.MIDNIGHT);
            } catch (DateTimeParseException ex) {
                throw new LeoException("UH-OH!!! The dueDate format is invalid. "
                        + "Please use YYYY-MM-DD or YYYY-MM-DD HHMM format.");
            }

        }
        this.dueDate = parsedDueDate;
    }

    public String formatData() {
        return "D | " + super.formatData() + " | " + stringDueDate;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: "
                + dueDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm")) + ")";
    }
}
