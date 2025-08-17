import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public final List<Task> todoList = new ArrayList<>();

    /**
     * Creates a task based on the input string.
     * The input should start with "todo", "deadline", or "event".
     *
     * @param input The input string containing the task details.
     * @return A Task object of type ToDo, Deadline, or Event.
     */
    public Task createTask(String input){
        String[] words = input.trim().split("\\s+");
        if (words[0].equals("todo")) {
            String description = String.join(" ", java.util.Arrays.copyOfRange(words, 1, words.length));
            return new ToDo(description);
        } else if (words[0].equals("deadline")) {
            String[] parts = input.split(" /by ", 2);
            String description = parts[0].replaceFirst("deadline ", "").trim();
            String dueDate = parts.length > 1 ? parts[1].trim() : "";
            return new Deadline(description, dueDate);
        } else if (words[0].equals("event")) {
            String[] parts = input.split(" /from | /to ", 3);
            String description = parts[0].replaceFirst("event ", "").trim();
            String startDate = parts.length > 1 ? parts[1].trim() : "";
            String endDate = parts.length > 2 ? parts[2].trim() : "";
            return new Event(description, startDate, endDate);
        } else {
            throw new IllegalArgumentException("Cannot create task: Unknown command. Please use 'todo', 'deadline', or 'event'.");
        }
    }

    /**
     * Adds a new task to the todo list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        todoList.add(task); // Adds a new task to the list
        System.out.println("Got it! I've added this task: " + task);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
    }

    /**
     * Marks a task as not done based on the input command.
     * @param words The input command split into words.
     */
    public void unmarkTask(String[] words) {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todoList.size()) {
                Task task = todoList.get(index);
                task.setDone(false);
                System.out.println("Marked as not done: " + task);
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }

    /**
     * Marks a task as done based on the input command.
     * @param words The input command split into words.
     */
    public void markTask(String[] words) {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todoList.size()) {
                Task task = todoList.get(index);
                task.setDone(true);
                System.out.println("Marked as done: " + task);
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }

    /**
     * Prints the current todo list.
     */
    public void printList() {
        System.out.println("Here is your todo list:");
        if (todoList.isEmpty()) {
            System.out.println("Your todo list is empty.");
        } else {
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println((i + 1) + ". " + todoList.get(i));
            }
        }
    }

}
