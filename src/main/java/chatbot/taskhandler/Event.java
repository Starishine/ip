package chatbot.taskhandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import chatbot.exceptions.LeoException;
import chatbot.parser.DateTimeParser;

/**
 * Represents an event task with a start date and an end date.
 * An Event object contains the task name, start date, and end date.
 */
public class Event extends Task {
    private String stringStartDate;
    private String stringEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs an Event object with the specified name, start date, and end date.
     *
     * @param name      The name of the task.
     * @param startDate The start date of the event in "yyyy-MM-dd" format.
     * @param endDate   The end date of the event in "yyyy-MM-dd" format.
     * @throws LeoException If the start date or end date format is invalid.
     */
    public Event(String name, String startDate, String endDate) throws LeoException {
        super(name);
        setStartDate(startDate);
        setEndDate(endDate);

    }


    public void setStartDate(String startDate) throws LeoException {
        this.startDate = DateTimeParser.parseDateTime(startDate, "startDate");
        this.stringStartDate = startDate;
    }

    public void setEndDate(String endDate) throws LeoException {
        this.endDate = DateTimeParser.parseDateTime(endDate, "endDate");
        this.stringEndDate = endDate;
    }

    public String formatData() {
        return "E | " + super.formatData() + " | " + stringStartDate + " | " + stringEndDate;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm")) + " to: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm")) + ")";
    }
}
