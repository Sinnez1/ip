package pinggu.parser;

import java.time.format.DateTimeParseException;

import pinggu.exception.PingguException;
import pinggu.task.Deadline;
import pinggu.task.Event;
import pinggu.task.Todo;

/**
 * Handles user commands.
 */
public class Parser {

    /**
     * List of valid commands.
     */
    public enum Commands {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        FIND
    }

    /**
     * Parses the user String input into a Command.
     *
     * @param input The user input into Ui.
     * @return Commands from enum.
     * @throws IllegalArgumentException If input does not match commands in enum Commands.
     */
    public static Commands parseCommand(String input) throws IllegalArgumentException {
        String[] split = input.split(" ");
        String command = split[0];
        return Commands.valueOf(command.toUpperCase());
    }

    /**
     * Parses user input to get an integer.
     *
     * @param input The user input into Ui.
     * @return An integer in 0-indexing format.
     */
    public static int parseInputIndex(String input) {
        String[] split = input.split(" ");
        return Integer.parseInt(split[1]) - 1;
    }

    /**
     * Creates a Todo object with task description.
     *
     * @param input The user input into Ui with todo and task description.
     * @return Todo item.
     * @throws PingguException If there is no task description.
     */
    public static Todo createTodo(String input) throws PingguException {
        if (input.trim().equals("todo")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a task to remind you of!");
        }
        String description = input.substring(5).trim();
        return new Todo(description);
    }

    /**
     * Creates a Deadline object with task description and due date.
     *
     * @param input The user input into Ui with deadline, task description and due date.
     * @return Deadline object with task description and due date.
     * @throws PingguException If task description is empty or if there is no due date.
     * @throws DateTimeParseException If due date is not written in yyyy-mm-dd format.
     */
    public static Deadline createDeadline(String input) throws PingguException, DateTimeParseException {
        if (input.trim().equals("deadline")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a deadline task description!");
        }
        String arguments = input.substring(9).trim();
        return new Deadline(arguments);
    }

    /**
     * Creates an Event object with task description, start date and end date.
     *
     * @param input The user input into Ui with event description, start date and end date.
     * @return An Event object with task description, start date and end date.
     * @throws PingguException If task description is empty, or if there is no start and end date.
     * @throws DateTimeParseException If start and end dates are not in yyyy-mm-dd format.
     */
    public static Event createEvent(String input) throws PingguException, DateTimeParseException {
        if (input.trim().equals("event")) {
            throw new PingguException("Pinggu needs an event description!");
        }
        int fromDate = input.indexOf("/from");
        int toDate = input.indexOf("/to");
        if (fromDate == -1 || toDate == -1) {
            throw new PingguException("Pinggu needs a start and end date! "
                    + "Your description should have /from <yyyy-mm-dd> and /to <yyyy-mm-dd>!");

        }
        String description = input.substring(6, fromDate).trim();
        String from = input.substring(fromDate + 6, toDate).trim();
        String to = input.substring(toDate + 4).trim();
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (from.isEmpty()) {
            throw new PingguException("Pinggu needs a start date! "
                    + "Add /from <yyyy-mm-dd> into your description!");
        }
        if (to.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /to <yyyy-mm-dd> into your description!");
        }
        return new Event(description, from, to);
    }

    /**
     * Parses the find command to extract item to search for.
     *
     * @param input The user input into Ui with "find" and item to search for.
     * @return The keyword to search for.
     * @throws PingguException If keyword is empty.
     */
    public static String parseFindKeyword(String input) throws PingguException {
        String[] inputs = input.split(" ", 2);
        if (inputs.length < 2 || inputs[1].trim().isEmpty()) {
            throw new PingguException("Pinggu needs something to search for!");
        }
        return inputs[1].trim();
    }

}
