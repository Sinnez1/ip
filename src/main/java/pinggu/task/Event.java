package pinggu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import pinggu.Constants;
import pinggu.exception.PingguException;
import pinggu.parser.Parser;

/**
 * Represents Event object with description, start and end date.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Initializes Event object by parsing input.
     *
     * @param input Input containing task description, start date and end date.
     * @throws PingguException If description or dates are missing.
     * @throws DateTimeParseException If date is not in yyyy-mm-dd format.
     */
    public Event(String input) throws PingguException, DateTimeParseException {
        super("");
        assert input != null : "Input to create Event should not be null";
        int fromDateIndex = input.indexOf(Constants.EVENT_START_PREFIX);
        int toDateIndex = input.indexOf(Constants.EVENT_END_PREFIX);
        if (fromDateIndex == -1 || toDateIndex == -1) {
            throw new PingguException("Pinggu needs a start and end date! "
                    + "Your description should have /from <yyyy-mm-dd> and /to <yyyy-mm-dd>!");

        }
        String description = input.substring(0, fromDateIndex).trim();
        String from = input.substring(fromDateIndex + Constants.EVENT_START_PREFIX.length(), toDateIndex).trim();
        String to = input.substring(toDateIndex + Constants.EVENT_END_PREFIX.length()).trim();
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
        this.setDescription(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Initializes Event object with a description, start and end date.
     *
     * @param description The description of the event.
     * @param from The start date of the event.
     * @param to The end date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null : "From date string should not be null";
        assert to != null : "To date string should not be null";
        //expects yyyy-mm-dd format
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + ")";
    }

    @Override
    public String toFileString() {
        return Parser.Commands.EVENT.name() + " | " + (getIsDone() ? "1" : "0")
                + " | " + getDescription() + " | " + this.from + " | " + this.to;
    }
}
