package chatbot.leo;

import java.util.Scanner;

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
    private final Scanner scanner;


    public Leo (String filePath) {
        TaskManager taskManager = new TaskManager(filePath);
        this.commandHandler = new CommandHandler(taskManager);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the Leo chatbot interaction loop.
     * It greets the user, processes commands, and says goodbye when the user exits.
     */
    public void start() {
        Ui ui = new Ui();
        ui.showLine();
        ui.showWelcome();
        String input = scanner.nextLine();
        while (!input.equals("bye")){
            try {
                commandHandler.handleCommand(input);
            } catch (LeoException e) {
                System.out.println(e.getMessage());
            }
            input = scanner.nextLine();
        }
        ui.showGoodbye();
        scanner.close();
    }


    public static void main(String[] args) {
        Leo leo = new Leo("data/leo.txt");
        leo.start();
    }

}
