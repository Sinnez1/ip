import java.util.*;

public class Pinggu {
    private static List<Task> tasks = new ArrayList<>();
    public static final String DIVIDER = "____________________________________________________________";

    public enum Commands {
        bye,
        list,
        mark,
        unmark,
        todo,
        deadline,
        event,
        delete
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = DIVIDER + "\n"
                + "Hello! I'm Pinggu\n"
                + "What can I do for you?\n"
                + DIVIDER;
        System.out.println(text);

        while (true) {
            String input = scanner.nextLine();
            String[] split = input.split(" ");
            String command = split[0];

            try {
                Commands cmd = Commands.valueOf(command); //returns enum, will throw IllegalArgumentException
                switch (cmd) {
                    case bye:
                        printExit();
                        return;
                    case list:
                        listTasks();
                        break;
                    case mark:
                        createMarkTask(input);
                        break;
                    case unmark:
                        createUnmarkTask(input);
                        break;
                    case todo:
                        createTodo(input);
                        break;
                    case deadline:
                        createDeadLine(input);
                        break;
                    case event:
                        createEvent(input);
                        break;
                    case delete:
                        deleteTask(input);
                        break;
                }
            } catch (NumberFormatException e) { //has to come before IllegalArgumentException as it extends that
                    printMessage("Pinggu needs a valid number!");
            } catch (IllegalArgumentException e) {
                printMessage("Noot Noot! Pinggu does not recognize this command!");
            } catch (PingguException e) {
                printMessage(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                printMessage("Pinggu does not have this task number! " +
                        "The max is " + tasks.size());
            }
        }
    }

    private static void printMessage(String msg) { //used to print normal errors and common messages
        System.out.println(DIVIDER);
        System.out.println(msg);
        System.out.println(DIVIDER);
    }

    private static void printExit() {
        String output = DIVIDER + "\n"
                + "Bye. Pinggu hopes to see you again soon!\n"
                + DIVIDER;
        System.out.println(output);
    }

    private static void addTask(Task task) {
        tasks.add(task);
        String msg = "Got it. Pinggu has added this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        printMessage(msg);
    }

    private static void listTasks() {
        System.out.println(DIVIDER);
        System.out.println("Here are the tasks in your list:");
        int counter = 1;
        for (Task task : tasks) {
            System.out.println(counter + "." + task.toString());
            counter++;
        }
        System.out.println(DIVIDER);
    }

    private static void createMarkTask(String input) {
        String[] array = input.split(" ");
        int taskNo = Integer.parseInt(array[1]) - 1; //0-indexing for List<>
        Task task = tasks.get(taskNo); //throws error if out of bounds
        task.markTask();
    }

    private static void createUnmarkTask(String input) {
        String[] array = input.split(" ");
        int taskNo = Integer.parseInt(array[1]) - 1;
        Task task = tasks.get(taskNo); //throws error if out of bounds
        task.unmarkTask();
    }

    private static void createTodo(String input) throws PingguException {
        if (input.trim().equals("todo")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a task to remind you of!");
        }
        String description = input.substring(5);
        addTask(new Todo(description));
    }

    private static void createDeadLine(String input) throws PingguException {
        if (input.trim().equals("deadline")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a deadline task description!");
        }
        int byDate = input.indexOf("/by");
        if (byDate == -1) { //cannot find a due date
            throw new PingguException("Pinggu needs a due date! " +
                    "Add /by <date> into your description!");
        }
        String description = input.substring(9, byDate);
        String by = input.substring(byDate + 4);
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (by.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! " +
                    "Add /by <date> into your description!");
        }
        addTask(new Deadline(description, by));
    }

    private static void createEvent(String input) throws PingguException {
        if (input.trim().equals("event")) {
            throw new PingguException("Pinggu needs an event description!");
        }
        int fromDate = input.indexOf("/from");
        int toDate = input.indexOf("/to");
        if (fromDate == -1 || toDate == -1) {
            throw new PingguException("Pinggu needs a start and end date! " +
                    "Your description should have /from <date> and /to <date>!");

        }
        String description = input.substring(6, fromDate);
        String from = input.substring(fromDate + 6, toDate);
        String to = input.substring(toDate + 4);
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (from.isEmpty()) {
            throw new PingguException("Pinggu needs a start date! " +
                    "Add /from <date> into your description!");
        }
        if (to.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! " +
                    "Add /to <date> into your description!");
        }

        addTask(new Event(description, from, to));
    }

    private static void deleteTask(String input) throws PingguException {
        if (input.trim().equals("delete")) {
            throw new PingguException("Pinggu needs a task number to delete!");
        }
        String[] array = input.split(" ");
        int taskToDelete = Integer.parseInt(array[1]) - 1;
        Task task = tasks.get(taskToDelete); //throws error if out of bounds
        tasks.remove(task);
        String msg = "Noted. Pinggu has removed this task:\n"
                + " " + task.toString() + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
        printMessage(msg);
    }
}
