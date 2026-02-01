package pinggu.task;

/**
 * Represents a Todo item.
 */
public class Todo extends Task {

    /**
     * Initializes a Todo object.
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
    public String toStringInSaveFile() {
        return "T | " + (isDone ? "1" : "0") + " | " + this.description;
    }
}
