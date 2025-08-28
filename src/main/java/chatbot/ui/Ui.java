package chatbot.ui;

/**
 * Ui class handles basic interactions with the user, including displaying messages.
 */
public class Ui {
    /**
     * Displays a line separator for better readability.
     */
    public void showLine() {
        System.out.println("___________________________________________");
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Hello, 🌟 I'm Leo, your favorite chatbot!" + "\n"
                + "What can I do for you today?");
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println("Bye 👋 ! Hope to see you soon!");
    }
}
