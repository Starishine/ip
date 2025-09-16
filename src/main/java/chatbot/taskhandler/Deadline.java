package chatbot.taskhandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import chatbot.exceptions.LeoException;
import chatbot.parser.DateTimeParser;

/**
 * Represents a task with a deadline.
 * A Deadline object contains the task name and the due date.
 */
public class Deadline extends Task {
    private String stringDueDate;
    private LocalDateTime dueDate;

    /**
     * Constructs a Deadline object with the specified name and due date.
     *
     * @param name    The name of the task.
     * @param dueDate The due date of the task in "yyyy-MM-dd" or "yyyy-MM-dd HHmm" format.
     * @throws LeoException If the due date format is invalid.
     */
    public Deadline(String name, String dueDate) throws LeoException {
        super(name);
        setBy(dueDate);

    }

    public void setBy(String dueDate) throws LeoException {
        this.dueDate = DateTimeParser.parseDateTime(dueDate, "dueDate");
        this.stringDueDate = dueDate;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
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
