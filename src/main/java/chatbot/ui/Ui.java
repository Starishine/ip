package chatbot.ui;

/**
 * Ui class handles basic interactions with the user, including displaying messages.
 */
public class Ui {
    public void showLine() {
        System.out.println("___________________________________________");
    }

    public void showWelcome() {
        System.out.println("Hello, 🌟 I'm Leo, your favorite chatbot!"+ "\n"
                + "What can I do for you today?");
    }

    public void showGoodbye() {
       System.out.println("Bye 👋 ! Hope to see you soon!");
    }
}
