package chatbot.inputreader;

/**
 * Enum representing the different types of commands that can be processed.
 */
public enum CommandType {
    BYE,
    MARK,
    UNMARK,
    LIST,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    /**
     * Converts a string command to its corresponding CommandType.
     *
     * @param command the command string to convert
     * @return the corresponding CommandType
     */
    public static CommandType fromString(String command) {
        return switch (command.toLowerCase()) {
            case "bye" -> BYE;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "list" -> LIST;
            case "delete" -> DELETE;
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            default -> UNKNOWN;
        };
    }

}
