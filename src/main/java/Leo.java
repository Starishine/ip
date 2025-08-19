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
            if (words[0].equals("mark")){
                try {
                    taskManager.markTask(words);
                } catch (LeoException e) {
                    System.out.println(e.getMessage());
                }
            } else if (words[0].equals("unmark")){
                try {
                    taskManager.unmarkTask(words);
                } catch (LeoException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.equals("list")){
                taskManager.printList();
            } else if (words[0].equals("delete")){
                try {
                    taskManager.deleteTask(words);
                } catch (LeoException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    Task task = taskManager.createTask(input);
                    taskManager.addTask(task);
                } catch (LeoException e) {
                    System.out.println(e.getMessage());
                }
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
