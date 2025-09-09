package chatbot.taskhandler;

import chatbot.exceptions.LeoException;

/**
 * Parses user input strings into specific {@link Task} objects.
 */
public class TaskParser {
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String EVENT = "event";

    private static final String DEADLINE_SPLITTER = " /by ";
    private static final String EVENT_FROM = " /from ";
    private static final String EVENT_TO = " /to ";

    /**
     * Parses an input string into a {@link Task}.
     * Determines which type of task (ToDo, Deadline, or Event) to create.
     *
     * @param input The raw user input string.
     * @return A {@link Task} object corresponding to the input.
     * @throws LeoException If the input format is invalid or command is unknown.
     */
    public static Task parseTask(String input) throws LeoException {
        String[] words = input.trim().split("\\s+");
        if (words.length == 0) {
            throw new LeoException("UH-OHH!!!! Input cannot be empty");
        }
        String command = words[0];

        switch (command) {
        case TODO:
            return parseTodo(words);
        case DEADLINE:
            return parseDeadline(input);
        case EVENT:
            return parseEvent(input);
        default:
            throw new LeoException("UH-OH!!! Cannot create task: Unknown command. " +
                    "Please use 'todo', 'deadline','event'.");
        }
    }

    /**
     * Parses a ToDo task from input words.
     * @param words The split input containing "todo" and the description.
     * @return A new {@link ToDo} task.
     * @throws LeoException If the description is missing.
     */
    public static Task parseTodo(String[] words) throws LeoException {
        String description = String.join(" ", java.util.Arrays.copyOfRange(words, 1, words.length));
        if (description.isEmpty()) {
            throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'todo'.");
        }
        return new ToDo(description);
    }

    /**
     * Parses a Deadline task from the input string.
     * @param input The raw user input.
     * @return A new {@link Deadline} task.
     * @throws LeoException If the description or due date is missing.
     */
    public static Task parseDeadline(String input) throws LeoException {
        String[] parts = input.split(DEADLINE_SPLITTER, 2);
        String description = parts[0].replaceFirst(DEADLINE, "").trim();
        if (description.isEmpty()) {
            throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'deadline'.");
        }
        String dueDate = parts.length > 1 ? parts[1].trim() : "";
        if (dueDate.isEmpty()) {
            throw new LeoException("UH-OH!!!! Cannot create task: Due date cannot be empty for 'deadline'.");
        }
        return new Deadline(description, dueDate);
    }

    /**
     * Parses an Event task from the input string.
     * @param input The raw user input.
     * @return A new {@link Event} task.
     * @throws LeoException If the description, start date, or end date is missing.
     */
    public static Task parseEvent(String input) throws LeoException {
        if (!input.contains(EVENT_FROM) || !input.contains(EVENT_TO)) {
            throw new LeoException("UH-OH!!!! Event must contain '/from' and '/to'.");
        }
        // split into 2 parts first - before and after /from
        String[] splitFrom = input.split(EVENT_FROM, 2);
        // before /from contains task description
        String description = splitFrom[0].replaceFirst(EVENT, "").trim();
        if (description.isEmpty()) {
            throw new LeoException("UH-OH!!!! Cannot create task: Description cannot be empty for 'event'.");
        }
        // after /from contains both startdate and endate so split again
        String[] splitTo = splitFrom[1].split(EVENT_TO, 2);
        String startDate = splitTo[0].trim();
        String endDate = (splitTo.length > 1) ? splitTo[1].trim() : "";

        if (startDate.isEmpty() || endDate.isEmpty()) {
            throw new LeoException("UH-OH!!!! Start date and end date cannot be empty for 'event'.");
        }
        return new Event(description, startDate, endDate);
    }
}
