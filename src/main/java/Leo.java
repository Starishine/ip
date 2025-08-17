import java.util.Scanner;

public class Leo {
    public static void main(String[] args) {
        // greets the user
        printLogo();
        printLine();
        TaskManager taskManager = new TaskManager();

        System.out.println("Hello, \uD83C\uDF1F I'm Leo, your favorite chatbot!");
        System.out.println("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // echoes user input until "bye" is entered
        while (!input.equals("bye")){
            String[] words = input.split(" ");
            if (words[0].equals("mark")){
                taskManager.markTask(words);
            } else if (words[0].equals("unmark")){
                taskManager.unmarkTask(words);
            } else if (input.equals("list")){
                taskManager.printList();
            } else {
                try {
                    Task task = taskManager.createTask(input);
                    taskManager.addTask(task);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye \uD83D\uDC4B ! Hope to see you soon!");
        scanner.close();
    }


    public static void printLine() {
        System.out.println("___________________________________________");
    }

    public static void printLogo(){
        System.out.println("██╗     ███████╗ ██████╗ ");
        System.out.println("██║     ██╔════╝██╔═══██╗");
        System.out.println("██║     █████╗  ██║   ██║");
        System.out.println("██║     ██╔══╝  ██║   ██║");
        System.out.println("███████╗███████╗╚██████╔╝");
        System.out.println("╚══════╝╚══════╝ ╚═════╝ ");
        System.out.println();
    }
}
