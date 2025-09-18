package chatbot.taskhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import chatbot.exceptions.LeoException;
import chatbot.inputreader.CommandHandler;
import chatbot.inputreader.FileWriting;
import chatbot.parser.TaskParser;

/**
 * Manages a list of tasks, including loading from and saving to a file.
 * Provides methods to create, add, mark, unmark, delete, and print tasks.
 */
public class TaskManager {
    private String filePath;
    private List<Task> todoList = new ArrayList<>();

    /**
     * Constructs a TaskManager with the specified file path for task storage.
     * Loads existing tasks from the file into the todoList.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public TaskManager(String filePath) {
        assert filePath != null && !filePath.isBlank() : "File path must not be null or blank";
        this.filePath = filePath;
        this.loadDataFromFile(filePath);
    }

    public List<Task> getTodoList() {
        return todoList;
    }

    /**
     * Delegates the creation of a Task to the {@link TaskParser}.
     * The input string should start with "todo", "deadline", or "event".
     *
     * @param input The user input containing task details.
     * @return A {@link Task} object (ToDo, Deadline, or Event) parsed from the input.
     * @throws LeoException If the input format is invalid or required details are missing.
     */
    public Task createTask(String input) throws LeoException {
        return TaskParser.parseTask(input);
    }

    /**
     * Loads tasks from a file and populates the todoList.
     *
     * @param filePath The path to the file containing the tasks.
     */
    public void loadDataFromFile(String filePath) {
        try {
            List<String> lines = FileWriting.readFromFile(filePath);
            CommandHandler commandHandler = new CommandHandler(this);
            commandHandler.handleCommandFromFile(lines);
        } catch (IOException e) {
            System.out.println("Something went wrong while loading data: " + e.getMessage());
        } catch (LeoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the current list of tasks to a file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void saveTasksToFile(List<Task> tasks) {
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
    public String addTask(Task task) {
        assert task != null : "Task should never be null here";
        todoList.add(task); // Adds a new task to the list
        saveTasksToFile(todoList);
        assert todoList.contains(task) : "Task should have been added to todoList";
        String confirm = "Got it! I've added this task: " + task;
        String display = "Now you have " + todoList.size() + " tasks in the list.";

        System.out.println(confirm);
        System.out.println(display);

        return confirm + "\n" + display;
    }

    /**
     * Marks a task as not done based on the input command.
     *
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public String unmarkTask(String... words) throws LeoException {
        if (words.length <= 1) {
            throw new LeoException("UH-OH!!! Invalid format. Use unmark <task number>");
        }
        int index;
        try {
            index = Integer.parseInt(words[1]) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new LeoException("UH-OH!!! Task number must be an integer. Use unmark <integer>");
        }
        if (index < 0 || index >= todoList.size()) {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }

        Task task = todoList.get(index);
        task.setDone(false);
        saveTasksToFile(todoList);
        String confirm = "Marked as not done: " + task;
        System.out.println(confirm);
        return confirm;

    }

    /**
     * Sorts deadline tasks due dates in chronological order.
     */
    public String sortDeadlineTask() {
        List<Deadline> deadlineTasks = this.todoList
                .stream()
                .filter(task -> task instanceof Deadline) // filter all deadline tasks
                .map(task -> (Deadline) task)
                .filter(task -> !task.getDone()) // filter tasks not done yet
                .sorted(Comparator.comparing(Deadline::getDueDate))
                .toList();

        if (deadlineTasks.isEmpty()) {
            String emptyMsg = "YAY no dues yet!";
            System.out.println(emptyMsg);
            return emptyMsg;
        }

        List<String> taskToString = deadlineTasks
                .stream()
                .map(task -> (todoList.indexOf(task) + 1) + ". " + task)
                .toList();

        String resultMsg = "Here are the deadline tasks due soon:\n" + String.join("\n", taskToString);
        taskToString.forEach(System.out::println);
        return resultMsg;
    }

    /**
     * Marks a task as done based on the input command.
     *
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public String markTask(String... words) throws LeoException {
        if (words.length <= 1) {
            throw new LeoException("UH-OH!!! Invalid format. Use mark <task number>");
        }
        int index;
        try {
            index = Integer.parseInt(words[1]) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new LeoException("UH-OH!!! Task number must be an integer. Use mark <integer>");
        }
        if (index < 0 || index >= todoList.size()) {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }

        Task task = todoList.get(index);
        task.setDone(true);
        saveTasksToFile(todoList);
        String confirm = "Marked as done: " + task;
        System.out.println(confirm);
        return confirm;
    }

    /**
     * Deletes a task from the todo list based on the input command.
     *
     * @param words The input command split into words.
     * @throws LeoException If the task number is invalid.
     */
    public String deleteTask(String... words) throws LeoException {
        if (words.length <= 1) {
            throw new LeoException("UH-OH!!! Invalid format. Use delete <task number>");
        }
        int index;
        try {
            index = Integer.parseInt(words[1]) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new LeoException("UH-OH!!! Task number must be an integer. Use delete <integer>");
        }
        if (index < 0 || index >= todoList.size()) {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
        Task taskRemoved = todoList.remove(index);
        saveTasksToFile(todoList);
        String confirmMsg = "Removed Task: " + taskRemoved;
        String resultMsg = "Now you have " + todoList.size() + " tasks in the list.";

        System.out.println(confirmMsg);
        System.out.println(resultMsg);
        return confirmMsg + "\n" + resultMsg;
    }

    /**
     * Updates a task from the todo list based on the input command.
     *
     * @param input The input command.
     * @throws LeoException If the task number is invalid.
     */
    public String updateTask(String input) throws LeoException {
        String[] words = input.split(" ");
        if (words.length <= 1) {
            throw new LeoException("UH-OH!!! Invalid format. Use edit <task number>");
        }
        int index;
        try {
            index = Integer.parseInt(words[1]) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new LeoException("UH-OH!!! Task number must be an integer. Use edit <integer>");
        }

        if (index < 0 || index >= todoList.size()) {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
        Task task = todoList.get(index);
        // (\\w+) matches / followed by letters/numbers and captures flag name
        // \\s+ at least one space after flag
        // ([^/]+) -> everything after the space until the next / ,captures value of flag
        Pattern pattern = Pattern.compile("/(\\w+)\\s+([^/]+)");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.find()) {
            throw new LeoException("UH-OH!!! No fields to update. Use /name, /by, /from, or /to to specify fields.");
        }

        while (matcher.find()) {
            String field = matcher.group(1); // flag name
            String value = matcher.group(2).trim(); // value
            updateTaskField(task, field, value);
        }

        saveTasksToFile(todoList);
        String updateMsg = "Task updated: " + task.toString();
        System.out.println(updateMsg);
        return updateMsg;
    }

    /**
     * Updates the specified field of a task with the given value.
     *
     * @param task  The task to be updated.
     * @param field The field to be updated (e.g., "name", "by", "from", "to").
     * @param value The new value for the specified field.
     * @throws LeoException If the field is invalid or the value format is incorrect.
     */
    public void updateTaskField(Task task, String field, String value) throws LeoException {
        if (task instanceof ToDo) {
            updateToDoField((ToDo) task, field, value);
        } else if (task instanceof Deadline) {
            updateDeadlineField((Deadline) task, field, value);
        } else if (task instanceof Event) {
            updateEventField((Event) task, field, value);
        }
    }

    /**
     * Updates the specified field of a ToDo task with the given value.
     *
     * @param task  The ToDo task to be updated.
     * @param field The field to be updated.
     * @param value The new value for the specified field.
     * @throws LeoException If the field is invalid.
     */
    public void updateToDoField(ToDo task, String field, String value) throws LeoException {
        if (field.equals("name")) {
            task.setName(value);
        } else {
            throw new LeoException("UH-OH!!! Invalid field for ToDo. Only '/name' can be updated.");
        }
    }

    /**
     * Updates the specified field of a Deadline task with the given value.
     *
     * @param task  The Deadline task to be updated.
     * @param field The field to be updated.
     * @param value The new value for the specified field.
     * @throws LeoException If the field is invalid or the value format is incorrect.
     */
    public void updateDeadlineField(Deadline task, String field, String value) throws LeoException {
        switch (field) {
        case "name":
            task.setName(value);
            break;
        case "by":
            task.setBy(value);
            break;
        default:
            throw new LeoException("UH-OH!!! Invalid field for Deadline. Use '/name' or '/by' to edit.");
        }
    }

    /**
     * Updates the specified field of an Event task with the given value.
     *
     * @param task  The Event task to be updated.
     * @param field The field to be updated.
     * @param value The new value for the specified field.
     * @throws LeoException If the field is invalid or the value format is incorrect.
     */
    public void updateEventField(Event task, String field, String value) throws LeoException {
        switch (field) {
        case "name":
            task.setName(value);
            break;
        case "from":
            task.setStartDate(value);
            break;
        case "to":
            task.setEndDate(value);
            break;
        default:
            throw new LeoException("UH-OH!!! Invalid field for Event. Use '/name', '/from', or '/to' to edit.");
        }
    }


    /**
     * Prints the current todo list.
     */
    public String printList() {
        String startLine = "Here are the tasks in your list:";
        String result = startLine;
        System.out.println(result);
        assert todoList != null : "taskList must not be null";
        if (todoList.isEmpty()) {
            String emptyMsg = "Your task list is empty.";
            System.out.println(emptyMsg);
            return emptyMsg;
        }
        for (int i = 0; i < todoList.size(); i++) {
            String todoTask = (i + 1) + ". " + todoList.get(i);
            System.out.println(todoTask);
            result += "\n" + todoTask;
        }
        return result;
    }

    /**
     * Finds and prints tasks that contain the given keyword.
     * @param words The input command split into words, where the second word is the keyword.
     * @throws LeoException If no keyword is provided.
     */
    public String findTasks(String... words) throws LeoException {
        if (words.length < 2) {
            throw new LeoException("UH-OH!!! Please provide a keyword to search for.");
        }
        String keyword = String.join(" ", java.util.Arrays.copyOfRange(words, 1, words.length));
        // using streams
        List<String> foundTasks = Stream.iterate(0, x -> x + 1)
                .limit(todoList.size())
                .map(i -> todoList.get(i))
                .filter(task -> task.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(task -> (todoList.indexOf(task) + 1) + ". " + task)
                .toList();

        if (foundTasks.isEmpty()) {
            String emptyMsg = "No matching tasks found.";
            System.out.println(emptyMsg);
            return emptyMsg;
        }
        String resultMsg = "Here are the matching tasks in your list:\n" + String.join("\n", foundTasks);
        foundTasks.forEach(System.out::println);
        return resultMsg;
    }

}
