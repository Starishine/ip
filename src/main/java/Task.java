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

    @Override
    public String toString() {
        return (isDone ? "[✗] " : "[ ] ") + name; // Returns the task status and name
    }
}
