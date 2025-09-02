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
    public String showWelcome() {
        return "Hello, I'm Leo, your favorite chatbot!" + "\n"
                + "What can I do for you today?";
    }

    /**
     * Displays a goodbye message to the user.
     */
    public String showGoodbye() {
        return "Bye ! Hope to see you soon!";
    }
}
