import java.util.*;

public class Pinggu {
    private static List<String> tasks = new ArrayList<>();
    private static final String DIVIDER = "____________________________________________________________";
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
            } else {
                printText(input);
                addText(input);
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

    private static void addText(String text) {
        tasks.add(text);
    }

    private static void listTasks() {
        System.out.println(DIVIDER);
        int counter = 1;
        for (String task : tasks) {
            System.out.println(counter + " ." + task);
            counter++;
        }
        System.out.println(DIVIDER);
    }
}
