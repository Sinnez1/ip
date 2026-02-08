package pinggu.task;

/**
 * Represents Task in TaskList.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Initializes Task object with description.
     *
     * @param description The description of task.
     */
    public Task(String description) {
        assert description != null : "Description should not be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns completion status of task.
     *
     * @return Completion status of task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Sets a task to be done.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets a task to be not done.
     */
    public void setNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns task description in specified format.
     *
     * @return String description of Task to be saved in save file.
     */
    public String toFileString() {
        return "Task | " + (isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Returns task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether a task is done.
     *
     * @return True if task is completed, false otherwise.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Sets the description of a task.
     *
     * @param description Description of task.
     */
    public void setDescription(String description) {
        assert description != null : "Description to set to should not be null";
        this.description = description;
    }
}
