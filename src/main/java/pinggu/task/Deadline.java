package pinggu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import pinggu.Constants;
import pinggu.exception.PingguException;
import pinggu.parser.Parser;

/**
 * Represents Deadline object with description and due date.
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Initializes a Deadline object by parsing from input.
     *
     * @param input String containing description and deadline date.
     * @throws PingguException If description or date is missing.
     * @throws DateTimeParseException If due date is not in yyyy-mm-dd format.
     */
    public Deadline(String input) throws PingguException, DateTimeParseException {
        super("");
        assert input != null : "Input to create Deadline should not be null";
        int byDate = input.indexOf(Constants.DEADLINE_DUE_PREFIX);
        if (byDate == -1) { //cannot find a due date
            throw new PingguException(" Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        String description = input.substring(0, byDate).trim();
        String by = input.substring(byDate + Constants.DEADLINE_DUE_PREFIX.length()).trim();
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (by.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        this.setDescription(description);
        this.by = LocalDate.parse(by);

    }

    /**
     * Initializes a Deadline object with description and a due date from save file.
     *
     * @param description The description of the task.
     * @param by  The due date of task.
     */
    public Deadline(String description, String by) {
        super(description);
        assert by != null : "Due date string cannot be null";
        //date are in yyyy-mm-dd format
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return Parser.Commands.DEADLINE.name() + " | " + (getIsDone() ? "1" : "0")
                + " | " + getDescription() + " | " + this.by;
    }
}
