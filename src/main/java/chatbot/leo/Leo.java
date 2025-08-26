package chatbot.leo;

import java.util.Scanner;

import chatbot.exceptions.LeoException;
import chatbot.inputreader.CommandType;
import chatbot.taskhandler.CommandHandler;
import chatbot.taskhandler.Task;
import chatbot.taskhandler.TaskManager;


public class Leo {
    private final CommandHandler commandHandler;
    private final Scanner scanner;


    public Leo () {
        TaskManager taskManager = new TaskManager();
        this.commandHandler = new CommandHandler(taskManager);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printLine();
        System.out.println("Hello, 🌟 I'm Leo, your favorite chatbot!");
        System.out.println("What can I do for you today?");
        String input = scanner.nextLine();
        while (!input.equals("bye")){
            try {
                commandHandler.handleCommand(input);
            } catch (LeoException e) {
                System.out.println(e.getMessage());
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye 👋 ! Hope to see you soon!");
        scanner.close();
    }


    public static void main(String[] args) throws LeoException {
        Leo leo = new Leo();
        leo.start();
    }


    public static void printLine() {
        System.out.println("___________________________________________");
    }

}
