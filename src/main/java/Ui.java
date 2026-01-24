import java.util.Scanner;

public class Ui {
    private final Scanner scanner;
    public static final String DIVIDER = "____________________________________________________________";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String text = DIVIDER + "\n"
                + "Hello! I'm Pinggu\n"
                + "What can I do for you?\n"
                + DIVIDER;
        System.out.println(text);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void showDivider() {
        System.out.println(DIVIDER);
    }

    public void showExit() {
        String output = DIVIDER + "\n"
                + "Bye. Pinggu hopes to see you again soon!\n"
                + DIVIDER;
        System.out.println(output);
    }

    public void printMessage(String msg) { //used to print normal errors and common messages
        showDivider();
        System.out.println(msg);
        showDivider();
    }

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

    public void showDelete(Task task, int size) {
        String msg = "Noted. Pinggu has removed this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + size + " tasks in the list.";
        printMessage(msg);
    }

    public void showAdd(Task task, int size) {
        String msg = "Got it. Pinggu has added this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + size + " tasks in the list.";
        printMessage(msg);
    }

    public void markTask(Task task) {
        showDivider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        showDivider();
    }

    public void unmarkTask(Task task) {
        showDivider();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.toString());
        showDivider();
    }
}
