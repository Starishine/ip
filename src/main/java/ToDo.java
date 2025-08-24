public class ToDo extends Task {

    public ToDo(String name) {
        super(name);
    }

    public String formatData() {
        return "T | " + super.formatData(); // Formats the ToDo data for file writing
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
