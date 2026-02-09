package pinggu.task;

import pinggu.parser.Parser;

/**
 * Represents a Todo item.
 */
public class Todo extends Task {

    /**
     * Initializes a Todo object from input and save file.
     *
     * @param description Description of task to be done.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return Parser.Commands.TODO.name() + " | " + (getIsDone() ? "1" : "0")
                + " | " + getDescription();
    }
}
