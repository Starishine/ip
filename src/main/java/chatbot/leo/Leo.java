package chatbot.leo;

import chatbot.exceptions.LeoException;
import chatbot.inputreader.CommandHandler;
import chatbot.taskhandler.TaskManager;
import chatbot.ui.Ui;


/**
 * The main class for the Leo chatbot application.
 * It initializes the necessary components and starts the interaction loop with the user.
 */
public class Leo {
    private final CommandHandler commandHandler;

    /**
     * Constructs a Leo chatbot instance with the specified file path for task storage.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Leo(String filePath) {
        TaskManager taskManager = new TaskManager(filePath);
        this.commandHandler = new CommandHandler(taskManager);
    }

    /**
     * Starts the Leo chatbot interaction loop.
     * It greets the user, processes commands, and says goodbye when the user exits.
     */
    public String start(String input) {
        Ui ui = new Ui();
        System.out.println(ui.showWelcome());
        if (!input.equals("bye")) {
            try {
                String output = commandHandler.handleCommand(input);
                return output;
            } catch (LeoException e) {
                String error = e.getMessage();
                return error;
            }
        }
        String goodbyeMsg = ui.showGoodbye();
        System.out.println(goodbyeMsg);
        return "null";
    }

    public static void main(String[] args) {
        Leo leo = new Leo("data/leo.txt");
    }

}
