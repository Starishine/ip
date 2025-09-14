package chatbot.taskhandler;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import chatbot.exceptions.LeoException;

/**
 * Represents an event task with a start date and an end date.
 * An Event object contains the task name, start date, and end date.
 */
public class Event extends Task {
    private String stringStartDate;
    private String stringEndDate;
    private LocalDate startDate;
    private LocalDate endDate;

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
        try {
            this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException e) {
            throw new LeoException("UH-OH!!! The startDate format is invalid. "
                    + "Please use YYYY-MM-DD format.");
        }
        this.stringStartDate = startDate;
    }

    public void setEndDate(String endDate) throws LeoException {
        try {
            this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException e) {
            throw new LeoException("UH-OH!!! The endDate format is invalid. "
                    + "Please use YYYY-MM-DD format.");
        }
        this.stringEndDate = endDate;
    }

    public String formatData() {
        return "E | " + super.formatData() + " | " + stringStartDate + " | " + stringEndDate;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
