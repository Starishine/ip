package chatbot.inputreader;

import chatbot.exceptions.LeoException;
import chatbot.taskhandler.Task;
import chatbot.taskhandler.TaskManager;

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
        this.taskManager = taskManager;
    }

    /**
     * Processes the user input command and executes the corresponding action.
     *
     * @param input The user input command as a string.
     * @throws LeoException If there is an error processing the command.
     */
    public void handleCommand(String input) throws LeoException {
        String[] words = input.split(" ");
        CommandType command = CommandType.fromString(words[0]);
        // echoes user input until "bye" is entered
            try {
                switch (command) {
                case MARK:
                    taskManager.markTask(words);
                    break;
                case UNMARK:
                    taskManager.unmarkTask(words);
                    break;
                case LIST:
                    taskManager.printList();
                    break;
                case DELETE:
                    taskManager.deleteTask(words);
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    Task task = taskManager.createTask(input);
                    taskManager.addTask(task);
                    break;
                case FIND:
                    taskManager.findTasks(words);
                    break;
                default:
                    throw new LeoException("UH-OH!!! Cannot understand your command. "
                            + "Please use 'todo', 'deadline', 'event', 'mark', 'unmark', 'list', or 'delete'.");

            }
        } catch (LeoException e) {
            System.out.println(e.getMessage());
        }

    }

}
