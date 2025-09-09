package chatbot.taskhandler;

/**
 * Represents a general task with a name and completion status.
 */
public class Task {
    private final String name;
    private boolean isDone;

    /**
     * Constructs a Task with the given name. The task is initially not done.
     *
     * @param name The name of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false; // Task is initially not done
    }

    public String getName() {
        return name;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean getDone() {
        return this.isDone;
    }

    public String formatData() {
        return (isDone ? "1" : "0") + " | " + name; // Formats the task data for file writing
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name; // Returns the task status and name
    }
}
