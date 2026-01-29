package pinggu.ui;

import java.util.Scanner;

import pinggu.task.Task;
import pinggu.task.TaskList;

/**
 * Handles user interactions.
 * Reads input and prints messages to console.
 */
public class Ui {
    public static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Initializes new Ui instance with Scanner for input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays welcome message when program is run.
     */
    public void showWelcome() {
        String text = DIVIDER + "\n"
                + "Hello! I'm Pinggu.\n"
                + "What can I do for you?\n"
                + DIVIDER;
        System.out.println(text);
    }

    /**
     * Reads single line of command input from user.
     *
     * @return The String command input from user.
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Prints horizontal divider to separate sections of output message.
     */
    public void showDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays exit message when user quits the program.
     */
    public void showExit() {
        String output = DIVIDER + "\n"
                + "Bye. Pinggu hopes to see you again soon!\n"
                + DIVIDER;
        System.out.println(output);
    }

    /**
     * Prints error and common messages to console.
     *
     * @param msg The message to display.
     */
    public void printMessage(String msg) { //used to print normal errors and common messages
        showDivider();
        System.out.println(msg);
        showDivider();
    }

    /**
     * Prints an error message when save file cannot be loaded.
     */
    public void showLoadingError() {
        printMessage("Error loading file. Starting with empty task list.");
    }

    /**
     * Displays list of all tasks currently in TaskList with their indexing and description.
     *
     * @param tasks The TaskList object holding tasks to display.
     */
    public void printTaskList(TaskList tasks) {
        showDivider();
        System.out.println("Here are the tasks in your list:");
        int counter = 1;
        for (Task task : tasks.getTasks()) {
            System.out.println(counter + "." + task.toString());
            counter++;
        }
        showDivider();
    }

    /**
     * Displays message to show Task is deleted and remaining tasks in TaskList.
     *
     * @param task The task object to be deleted.
     * @param size The number of remaining Tasks in TaskList.
     */
    public void showDelete(Task task, int size) {
        String msg = "Noted. Pinggu has removed this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + size + " tasks in the list.";
        printMessage(msg);
    }

    /**
     * Displays message to show Task is added and new number of tasks in TaskList.
     *
     * @param task The Task object to be added.
     * @param size The new number of tasks in TaskList.
     */
    public void showAdd(Task task, int size) {
        String msg = "Got it. Pinggu has added this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + size + " tasks in the list.";
        printMessage(msg);
    }

    /**
     * Displays message to show that Task is marked as done.
     *
     * @param task The task to be marked as done.
     */
    public void markTask(Task task) {
        showDivider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        showDivider();
    }

    /**
     * Displays message to show that Task is not done.
     *
     * @param task The task to be marked as not done.
     */
    public void unmarkTask(Task task) {
        showDivider();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.toString());
        showDivider();
    }

    /**
     * Displays list of tasks with the keyword.
     *
     * @param tasks TaskList containing our filtered tasks with the keyword.
     */
    public void showFind(TaskList tasks) {
        showDivider();
        System.out.println("Here are the matching tasks in your list:");
        int counter = 1;
        for (Task task : tasks.getTasks()) {
            System.out.println(counter + "." + task.toString());
            counter++;
        }
        showDivider();
    }
}
