package chatbot.taskhandler;

import chatbot.exceptions.LeoException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final String stringStartDate;
    private final String stringEndDate;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Event(String name, String startDate, String endDate) throws LeoException {
        super(name);
        this.stringStartDate = startDate;
        this.stringEndDate = endDate;


        try {
           this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException e) {
            throw new LeoException("UH-OH!!! The startDate/endDate format is invalid. "
                    + "Please use YYYY-MM-DD format.");
        }
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
