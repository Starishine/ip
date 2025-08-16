import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Leo {
    public static void main(String[] args) {
        // greets the user
        printLogo();
        printLine();
        List<Task> todolist = new ArrayList<>();

        System.out.println("Hello, \uD83C\uDF1F I'm Leo, your favorite chatbot!");
        System.out.println("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // echoes user input until "bye" is entered
        while (!input.equals("bye")){
            System.out.println("You said: " + input);

            String[] words = input.split(" ");
            if (words[0].equals("mark")){
                markTask(todolist, words);
            } else if (words[0].equals("unmark")){
                unmarkTask(todolist, words);
            } else if (input.equals("list")){
                printList(todolist);
            } else {
                Task task = new Task(input);
                todolist.add(task);
            }

            input = scanner.nextLine();
        }
        System.out.println("Bye \uD83D\uDC4B ! Hope to see you soon!");
        scanner.close();
    }

    /**
     * Marks a task as done in the todo list.
     * @param todolist The list of todo items.
     * @param words The command words, where the second word is the task number to mark.
     */
    public static void markTask(List<Task> todolist, String[] words) {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todolist.size()) {
                Task task = todolist.get(index);
                task.setDone(true);
                System.out.println("Marked as done: " + task);
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }

    /**
     * Unmarks a task as not done in the todo list.
     * @param todolist The list of todo items.
     * @param words The command words, where the second word is the task number to unmark.
     */
    public static void unmarkTask(List<Task> todolist, String[] words) {
        if (words.length > 1) {
            int index = Integer.parseInt(words[1]) - 1;
            if (index >= 0 && index < todolist.size()) {
                Task task = todolist.get(index);
                task.setDone(false);
                System.out.println("Marked as not done: " + task);
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }

    /**
     * Prints the todo list to the console.
     * @param todolist The list of todo items.
     */
    public static void printList(List<Task> todolist) {
        System.out.println("Here is your todo list:");
        if (todolist.isEmpty()) {
            System.out.println("Your todo list is empty.");
        } else {
            for (int i = 0; i < todolist.size(); i++) {
                System.out.println((i + 1) + ". " + todolist.get(i));
            }
        }
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
