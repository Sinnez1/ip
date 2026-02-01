package pinggu.ui;

import pinggu.task.Task;
import pinggu.task.TaskList;

/**
 * Handles user interactions.
 * Reads input and prints messages to console.
 */
public class Ui {

    private String format(String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Displays welcome message when program is run.
     */
    public String showWelcomeMessage() {
        return "Hello! I'm Pinggu.\nWhat can I do for you?";

    }

    /**
     * Displays exit message when user quits the program.
     */
    public String showExitMessage() {
        return format("Bye. Pinggu hopes to see you again soon!", "Exiting in 3 seconds.");
    }

    /**
     * Displays list of all tasks currently in TaskList with their indexing and description.
     *
     * @param tasks The TaskList object holding tasks to display.
     * @return Message showing all tasks in task list.
     */
    public String printTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        int counter = 1;
        for (Task task : tasks.getTasks()) {
            sb.append(counter).append(".").append(task.toString()).append("\n");
            counter++;
        }
        return sb.toString();
    }

    /**
     * Displays message to show Task is deleted and remaining tasks in TaskList.
     *
     * @param task The task object to be deleted.
     * @param size The number of remaining Tasks in TaskList.
     * @return Message showing that task is deleted.
     */
    public String showDeleteMessage(Task task, int size) {
        return format("Noted. Pinggu has removed this task:",
                " " + task.toString(),
                "Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays message to show Task is added and new number of tasks in TaskList.
     *
     * @param task The Task object to be added.
     * @param size The new number of tasks in TaskList.
     * @return Message showing that task is added and size of task list.
     */
    public String showAddMessage(Task task, int size) {
        return format("Got it. Pinggu has added this task:",
                " " + task.toString(),
                "Now you have " + size + " tasks in the list."
        );
    }

    /**
     * Displays message to show that Task is marked as done.
     *
     * @param task The task to be marked as done.
     * @return Message showing that task is marked.
     */
    public String showMarkTaskMessage(Task task) {
        return format("Nice! I've marked this task as done:", task.toString());
    }

    /**
     * Displays message to show that Task is not done.
     *
     * @param task The task to be marked as not done.
     * @return Message that task is unmarked.
     */
    public String showUnmarkTaskMessage(Task task) {
        return format("OK, I've marked this task as not done yet:", task.toString());
    }

    /**
     * Displays list of tasks with the keyword.
     *
     * @param tasks TaskList containing our filtered tasks with the keyword.
     * @return Message showing tasks with the keyword.
     */
    public String showFindMessage(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        int counter = 1;
        for (Task task : tasks.getTasks()) {
            sb.append(counter).append(".").append(task.toString()).append("\n");
            counter++;
        }
        return sb.toString();
    }

    /**
     * Shows the error message.
     *
     * @param message
     * @return Error message.
     */
    public String showErrorMessage(String message) {
        return "Noot noot!" + message;
    }
}
