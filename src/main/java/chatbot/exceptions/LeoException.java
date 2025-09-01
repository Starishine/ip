package chatbot.exceptions;

/**
 * The exception class for the Leo chatbot application.
 */
public class LeoException extends Exception {

    /**
     * Constructs an Exception with the specified message.
     *
     * @param message The error message.
     */
    public LeoException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}