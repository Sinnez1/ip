package pinggu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

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
    public String toSaveFile() {
        return "D | " + (isDone ? "1" : "0") + " | " + this.description + " | " + this.by;
    }
}
