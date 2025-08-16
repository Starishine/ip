import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Leo {
    public static void main(String[] args) {
        // greets the user
        printLogo();
        printLine();
        List<String> todolist = new ArrayList<>();

        System.out.println("Hello, \uD83C\uDF1F I'm Leo, your favorite chatbot!");
        System.out.println("What can I do for you today?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // echoes user input until "bye" is entered
        while (!input.equals("bye")){
            System.out.println("You said: " + input);
            if (input.equals("list")){
                printList(todolist);
            } else {
                todolist.add(input);
            }
            input = scanner.nextLine();
        }
        System.out.println("Bye \uD83D\uDC4B ! Hope to see you soon!");
        scanner.close();
    }

    /**
     * Prints the todo list to the console.
     * @param todolist The list of todo items.
     */
    public static void printList(List<String> todolist) {
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
