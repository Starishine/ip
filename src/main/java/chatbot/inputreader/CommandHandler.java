package chatbot.inputreader;

import java.util.List;

import chatbot.exceptions.LeoException;
import chatbot.taskhandler.Deadline;
import chatbot.taskhandler.Event;
import chatbot.taskhandler.Task;
import chatbot.taskhandler.TaskManager;
import chatbot.taskhandler.ToDo;

/**
 * Handles user commands and interacts with the TaskManager.
 */
public class CommandHandler {
    private final TaskManager taskManager;

    /**
     * Constructs a CommandHandler with the specified TaskManager.
     *
     * @param taskManager The TaskManager to manage tasks.
     */
    public CommandHandler(TaskManager taskManager) {
        assert taskManager != null : "Taskmanager should not be null";
        this.taskManager = taskManager;
    }

    /**
     * Processes the user input command and executes the corresponding action.
     *
     * @param input The user input command as a string.
     * @throws LeoException If there is an error processing the command.
     */
    public String handleCommand(String input) throws LeoException {
        String[] words = input.split(" ");
        CommandType command = CommandType.fromString(words[0]);

        try {
            switch (command) {
            case MARK:
                return taskManager.markTask(words);
            case UNMARK:
                return taskManager.unmarkTask(words);
            case LIST:
                return taskManager.printList();
            case DELETE:
                return taskManager.deleteTask(words);
            case TODO:
            case DEADLINE:
            case EVENT:
                Task task = taskManager.createTask(input);
                return taskManager.addTask(task);
            case FIND:
                return taskManager.findTasks(words);
            default:
                throw new LeoException("UH-OH!!! Cannot understand your command. "
                        + "Please use 'todo', 'deadline', 'event', 'mark', 'unmark', 'list', or 'delete'.");
            }
        } catch (LeoException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            return errorMessage;
        }
    }

    /**
     * Handles commands read from a file and adds them to the TaskManager.
     *
     * @param lines The list of command lines read from the file.
     * @throws LeoException If there is an error processing the commands.
     */
    public void handleCommandFromFile(List<String> lines) throws LeoException {
        assert lines != null : "Lines from file must not be null";

        for (String line : lines) {
            assert line != null && !line.isBlank() : "Each line should not be null or blank";
            String[] parts = line.split(" \\| ");
            assert parts.length >= 3 : "Line must have at least 3 parts - taskType, isDone, description";
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
            this.taskManager.addTask(task);
        }
    }

}
