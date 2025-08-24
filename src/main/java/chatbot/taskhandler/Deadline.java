package chatbot.taskhandler;

public class Deadline extends Task {
    private final String dueDate;

    public Deadline(String name, String dueDate) {
        super(name);
        this.dueDate = dueDate;
    }

    public String formatData() {
        return "D | " + super.formatData() + " | " + dueDate;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + dueDate + ")";
    }
}
