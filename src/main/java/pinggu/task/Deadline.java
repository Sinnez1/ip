package pinggu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents Deadline object with description and due date.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Initializes a Deadline object with description and a due date.
     *
     * @param description The description of the task.
     * @param by  The due date of task.
     */
    public Deadline(String description, String by) {
        super(description);
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
        return "D | " + (getIsDone() ? "1" : "0") + " | " + getDescription() + " | " + this.by;
    }
}
