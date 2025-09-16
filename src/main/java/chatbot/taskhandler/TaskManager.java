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

        int index = Integer.parseInt(words[1]) - 1;
        if (index >= 0 && index < todoList.size()) {
            Task task = todoList.get(index);
            task.setDone(false);
            saveTasksToFile(todoList);
            String confirm = "Marked as not done: " + task;
            System.out.println(confirm);
            return confirm;
        } else {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
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
        int index = Integer.parseInt(words[1]) - 1;
        if (index >= 0 && index < todoList.size()) {
            Task task = todoList.get(index);
            task.setDone(true);
            saveTasksToFile(todoList);
            String confirm = "Marked as done: " + task;
            System.out.println(confirm);
            return confirm;
        } else {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
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
        int index = Integer.parseInt(words[1]) - 1;
        if (index >= 0 && index < todoList.size()) {
            Task taskRemoved = todoList.remove(index);
            saveTasksToFile(todoList);
            String confirmMsg = "Removed Task: " + taskRemoved;
            String resultMsg = "Now you have " + todoList.size() + " tasks in the list.";

            System.out.println(confirmMsg);
            System.out.println(resultMsg);
            return confirmMsg + "\n" + resultMsg;
        } else {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
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
        int index = Integer.parseInt(words[1]) - 1;

        if (index < 0 || index > todoList.size()) {
            throw new LeoException("UH-OH!!! Invalid task number.");
        }
        Task task = todoList.get(index);
        // (\\w+) matches / followed by letters/numbers and captures flag name
        // \\s+ at least one space after flag
        // ([^/]+) -> everything after the space until the next / ,captures value of flag
        Pattern pattern = Pattern.compile("/(\\w+)\\s+([^/]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String field = matcher.group(1); // flag name
            String value = matcher.group(2).trim(); // value
            if (task instanceof ToDo && field.equals("name")) {
                task.setName(value);
            } else if (task instanceof Deadline) {
                Deadline deadlineTask = (Deadline) task;
                switch (field) {
                case "name":
                    deadlineTask.setName(value);
                    break;
                case "by":
                    deadlineTask.setBy(value);
                    break;
                default:
                    // no default needed
                }
            } else if (task instanceof Event) {
                Event eventTask = (Event) task;
                switch (field) {
                case "name":
                    eventTask.setName(value);
                    break;
                case "from":
                    eventTask.setStartDate(value);
                    break;
                case "to":
                    eventTask.setEndDate(value);
                    break;
                default:
                    // no default needed
                }
            }
        }
        saveTasksToFile(todoList);
        String updateMsg = "Task updated: " + task.toString();
        System.out.println(updateMsg);
        return updateMsg;
    }

    /**
     * Prints the current todo list.
     */
    public String printList() {
        String startLine = "Here is your todo list:";
        String result = startLine;
        System.out.println(result);
        assert todoList != null : "todoList must not be null";
        if (todoList.isEmpty()) {
            String emptyMsg = "Your todo list is empty.";
            System.out.println(emptyMsg);
            return emptyMsg;
        } else {
            for (int i = 0; i < todoList.size(); i++) {
                String todoTask = (i + 1) + ". " + todoList.get(i);
                System.out.println(todoTask);
                result += "\n" + todoTask;
            }
            return result;
        }
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
