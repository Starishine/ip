package chatbot.taskhandler;

/**
 * Represents an todo task with a name.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the given name.
     *
     * @param name The name of the todo task.
     */
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
