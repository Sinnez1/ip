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
            } else if (input.startsWith("todo")) {
                String description = input.substring(5);
                addTask(new Todo(description));
            } else if (input.startsWith("deadline")) {
                int byDate = input.indexOf("/by");
                String description = input.substring(9, byDate);
                String by = input.substring(byDate + 4);
                addTask(new Deadline(description, by));
            } else if (input.startsWith("event")) {
                int fromDate = input.indexOf("/from");
                int toDate = input.indexOf("/to");
                String description = input.substring(6, fromDate);
                String from = input.substring(fromDate + 6, toDate);
                String to = input.substring(toDate + 4);
                addTask(new Event(description, from, to));
            }
            else {
                printText(input);
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

    private static void addTask(Task task) {
        tasks.add(task);
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    private static void listTasks() {
        System.out.println(DIVIDER);
        int counter = 1;
        for (Task task : tasks) {
            System.out.println(counter + "." + task.toString());
            counter++;
        }
        System.out.println(DIVIDER);
    }
}
