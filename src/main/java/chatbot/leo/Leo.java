package chatbot.leo;

import java.util.Scanner;

import chatbot.exceptions.LeoException;
import chatbot.taskhandler.CommandHandler;
import chatbot.taskhandler.TaskManager;
import chatbot.taskhandler.Ui;


public class Leo {
    private final CommandHandler commandHandler;
    private final Scanner scanner;


    public Leo (String filePath) {
        TaskManager taskManager = new TaskManager(filePath);
        this.commandHandler = new CommandHandler(taskManager);
        this.scanner = new Scanner(System.in);
    }

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
