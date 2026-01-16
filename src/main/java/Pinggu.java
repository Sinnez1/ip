import java.util.*;

public class Pinggu {
    public static void main(String[] args) {

        String text = "____________________________________________________________ \n"
                + "Hello! I'm Pinggu \n"
                + "What can I do for you? \n"
                + "____________________________________________________________";
        System.out.println(text);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                printExit();
                break;
            }
            printText(input);
        }
    }

    private static void printText(String text) {
        String output = "____________________________________________________________ \n"
                + text
                + " \n"
                + "____________________________________________________________";
        System.out.println(output);
    }

    private static void printExit() {
        String output = "____________________________________________________________ \n"
                + "Bye. Hope to see you again soon! \n"
                + "____________________________________________________________";
        System.out.println(output);
    }
}
