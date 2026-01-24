import java.time.format.DateTimeParseException;

public class Parser {

    public enum Commands {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    public static Commands parseCommand(String input) throws IllegalArgumentException {
        String[] split = input.split(" ");
        String command = split[0];
        return Commands.valueOf(command.toUpperCase());
    }

    public static int parseIndex(String input) {
        String[] split = input.split(" ");
        return Integer.parseInt(split[1]) - 1;
    }

    public static Todo createTodo(String input) throws PingguException, DateTimeParseException {
        if (input.trim().equals("todo")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a task to remind you of!");
        }
        String description = input.substring(5).trim();
        return new Todo(description);
    }

    public static Deadline createDeadline(String input) throws PingguException, DateTimeParseException {
        if (input.trim().equals("deadline")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a deadline task description!");
        }
        int byDate = input.indexOf("/by");
        if (byDate == -1) { //cannot find a due date
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        String description = input.substring(9, byDate).trim();
        String by = input.substring(byDate + 4).trim();
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (by.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        return new Deadline(description, by);
    }

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

}
