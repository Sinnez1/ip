package pinggu.task;

/**
 * Task class to present the different Tasks.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initializes Task object with description.
     *
     * @param description The description of task.
     */
    public Task(String description) {
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
    public void markTask() {
        this.isDone = true;
    }

    /**
     * Sets a task to be not done.
     */
    public void unmarkTask() {
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
    public String toSaveFile() {
        return "pinggu.task.Task | " + (isDone ? "1" : "0") + " | " + this.description;
    }
}
