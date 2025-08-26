package chatbot.taskhandler;

import chatbot.exceptions.LeoException;
import chatbot.inputreader.FileWriting;

import java.io.IOException;
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
     * @throws LeoException If the input format is incorrect or if required details are missing.
     */
    public Task createTask(String input) throws LeoException {
        String[] words = input.trim().split("\\s+");
        if (words[0].equals("todo")) {
            String description = String.join(" ", java.util.Arrays.copyOfRange(words, 1, words.length));
            if (description.isEmpty()) {
                throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'todo'.");
            }
            return new ToDo(description);
        } else if (words[0].equals("deadline")) {
            String[] parts = input.split(" /by ", 2);
            String description = parts[0].replaceFirst("deadline", "").trim();
            if (description.isEmpty()) {
                throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'deadline'.");
            }
            String dueDate = parts.length > 1 ? parts[1].trim() : "";
            if (dueDate.isEmpty()) {
                throw new LeoException("UH-OH!!!! Cannot create task: Due date cannot be empty for 'deadline'.");
            }
            return new Deadline(description, dueDate);
        } else if (words[0].equals("event")) {
            String[] parts = input.split(" /from | /to ", 3);
            String description = parts[0].replaceFirst("event", "").trim();
            if (description.isEmpty()) {
                throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'event'.");
            }
            String startDate = parts.length > 1 ? parts[1].trim() : "";
            String endDate = parts.length > 2 ? parts[2].trim() : "";
            if (startDate.isEmpty() || endDate.isEmpty()) {
                throw new LeoException("UH-OH!!!! Cannot create task: Start date and end date cannot be empty " +
                        "for 'event'.");
            }
            return new Event(description, startDate, endDate);
        } else {
            throw new LeoException("UH-OH!!! Cannot create task: Unknown command. " +
                    "Please use 'todo', 'deadline','event'.");
        }
    }

    /**
     * Loads tasks from a file and populates the todoList.
     *
     * @param filePath The path to the file containing the tasks.
     */
    public void loadDataFromFile(String filePath) throws LeoException {
        try {
            List<String> lines = FileWriting.readFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                String taskType = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task;
                switch (taskType) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    String dueDate = parts[3];
                    task = new Deadline(description, dueDate);
                    break;
                case "E":
                    String startDate = parts[3];
                    String endDate = parts[4];
                    task = new Event(description, startDate, endDate);
                    break;
                default:
                    System.out.println("Unknown task type in file: " + taskType);
                    continue; // Skip unknown task types
                }
                task.setDone(isDone);
                todoList.add(task);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while loading data: " + e.getMessage());
        } catch (LeoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the current list of tasks to a file.
     *
     * @param tasks    The list of tasks to be saved.
     * @param filePath The path to the file where tasks will be saved.
     */
    public void saveTasksToFile(List<Task> tasks, String filePath) {
        try {
            FileWriting.writeToFile(filePath, tasks);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Adds a new task to the todo list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        todoList.add(task); // Adds a new task to the list
        saveTasksToFile(todoList, "data/leo.txt");
        System.out.println("Got it! I've added this task: " + task);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
    }

    /**
     * Marks a task as not done based on the input command.
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public void unmarkTask(String[] words) throws LeoException {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todoList.size()) {
                Task task = todoList.get(index);
                task.setDone(false);
                saveTasksToFile(todoList, "data/leo.txt");
                System.out.println("Marked as not done: " + task);
            } else {
                throw new LeoException("UH-OH!!! Invalid task number.");
            }
        }
    }

    /**
     * Marks a task as done based on the input command.
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public void markTask(String[] words) throws LeoException {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todoList.size()) {
                Task task = todoList.get(index);
                task.setDone(true);
                saveTasksToFile(todoList, "data/leo.txt");
                System.out.println("Marked as done: " + task);
            } else {
                throw new LeoException("UH-OH!!! Invalid task number.");
            }
        }
    }

    /**
     * Deletes a task from the todo list based on the input command.
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public void deleteTask(String[] words) throws LeoException {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todoList.size()) {
                Task taskRemoved = todoList.remove(index);
                saveTasksToFile(todoList, "data/leo.txt");
                System.out.println("Removed Task: " + taskRemoved);
                System.out.println("Now you have " + todoList.size() + " tasks in the list.");
            } else {
                throw new LeoException("UH-OH!!! Invalid task number.");
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
