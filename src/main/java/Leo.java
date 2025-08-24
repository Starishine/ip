import java.util.Scanner;

public class Leo {
    public static void main(String[] args) throws LeoException {
        // greets the user
        printLine();
        TaskManager taskManager = new TaskManager();

        System.out.println("Hello, 🌟 I'm Leo, your favorite chatbot!");
        System.out.println("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // echoes user input until "bye" is entered
        while (!input.equals("bye")){
            String[] words = input.split(" ");
            CommandType command = CommandType.fromString(words[0]);
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
                default:
                    throw new LeoException("UH-OH!!! Cannot understand your command. " +
                            "Please use 'todo', 'deadline', 'event', 'mark', 'unmark', 'list', or 'delete'.");

                }
            } catch (LeoException e) {
                System.out.println(e.getMessage());
            }

            input = scanner.nextLine();
        }
        System.out.println("Bye 👋 ! Hope to see you soon!");
        scanner.close();
    }


    public static void printLine() {
        System.out.println("___________________________________________");
    }

}
