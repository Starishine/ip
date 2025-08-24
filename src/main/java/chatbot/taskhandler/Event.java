package chatbot.taskhandler;

public class Event extends Task {
    private final String startDate;
    private final String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String formatData() {
        return "E | " + super.formatData() + " | " + startDate + " | " + endDate;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }
}
