import java.util.*;

public class Pinggu {
    private static List<Task> tasks = new ArrayList<>();
    public static final String DIVIDER = "____________________________________________________________";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = DIVIDER + "\n"
                + "Hello! I'm Pinggu \n"
                + "What can I do for you? \n"
                + DIVIDER;
        System.out.println(text);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                printExit();
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else if (input.startsWith("mark")) {
                String[] array = input.split(" ");
                int taskNo = Integer.parseInt(array[1]) - 1; //0-indexing for List<>
                Task task = tasks.get(taskNo);
                task.markTask();
            } else if (input.startsWith("unmark")) {
                String[] array = input.split(" ");
                int taskNo = Integer.parseInt(array[1]) - 1;
                Task task = tasks.get(taskNo);
                task.unmarkTask();
            } else {
                printText(input);
                addTask(input);
            }
        }
    }

    private static void printText(String text) {
        String output = DIVIDER + "\n"
                + "added: " + text + " \n"
                + DIVIDER;
        System.out.println(output);
    }

    private static void printExit() {
        String output = DIVIDER + "\n"
                + "Bye. Hope to see you again soon! \n"
                + DIVIDER;
        System.out.println(output);
    }

    private static void addTask(String input) {
        Task task = new Task(input);
        tasks.add(task);
    }

    private static void listTasks() {
        System.out.println(DIVIDER);
        int counter = 1;
        for (Task task : tasks) {
            System.out.println(counter + "." + "[" + task.getStatusIcon() + "] "
                    + task.description);
            counter++;
        }
        System.out.println(DIVIDER);
    }
}
