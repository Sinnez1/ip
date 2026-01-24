package pinggu.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, String from, String to) {
        super(description);
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
    public String toSaveFile() {
        return "E | " + (isDone ? "1" : "0") + " | " + this.description + " | " + this.from + " | " + this.to;
    }
}
