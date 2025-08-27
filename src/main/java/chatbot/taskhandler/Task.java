package chatbot.taskhandler;

/**
 * Represents a general task with a name and completion status.
 */
public class Task {
    private final String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false; // Task is initially not done
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String formatData() {
        return (isDone ? "1" : "0") + " | " + name; // Formats the task data for file writing
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name; // Returns the task status and name
    }
}
