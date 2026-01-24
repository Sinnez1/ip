import java.util.List;
import java.util.Scanner;

import java.time.format.DateTimeParseException;

public class Pinggu {
    private static TaskList tasks;
    private static Storage storage;
    private static Ui ui;
    public static final String DIVIDER = "____________________________________________________________";
    public static final String filePath = "./data/pinggu.txt";

    public enum Commands {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    public static void main(String[] args) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        ui.showWelcome();

        while (true) {
            String input = ui.readLine();
            String[] split = input.split(" ");
            String command = split[0];
            boolean isModified = false; //check if we changed our tasks

            try {
                Commands cmd = Commands.valueOf(command.toUpperCase()); //returns enum, will throw IllegalArgumentException
                switch (cmd) {
                case BYE:
                    ui.showExit();
                    return;
                case LIST:
                    listTasks();
                    break;
                case MARK:
                    createMarkTask(input);
                    isModified = true;
                    break;
                case UNMARK:
                    createUnmarkTask(input);
                    isModified = true;
                    break;
                case TODO:
                    createTodo(input);
                    isModified = true;
                    break;
                case DEADLINE:
                    createDeadLine(input);
                    isModified = true;
                    break;
                case EVENT:
                    createEvent(input);
                    isModified = true;
                    break;
                case DELETE:
                    deleteTask(input);
                    isModified = true;
                    break;
                }
                if (isModified) {
                    storage.save(tasks.getTasks());
                }
            } catch (NumberFormatException e) { //has to come before IllegalArgumentException as it extends that
                ui.printMessage("Pinggu needs a valid number!");
            } catch (IllegalArgumentException e) {
                ui.printMessage("Noot Noot! Pinggu does not recognize this command!");
            } catch (PingguException e) {
                ui.printMessage(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                ui.printMessage("Pinggu does not have this task number! "
                        + "The max is " + tasks.getSize());
            }
        }
    }

    private static void addTask(Task task) {
        tasks.addTask(task);
        ui.showAdd(task, tasks.getSize());
    }

    private static void listTasks() {
        ui.printTaskList(tasks);
    }

    private static void createMarkTask(String input) {
        String[] array = input.split(" ");
        int taskNo = Integer.parseInt(array[1]) - 1; //0-indexing for List<>
        Task task = tasks.getTask(taskNo); //throws error if out of bounds
        task.markTask();
        ui.markTask(task);
    }

    private static void createUnmarkTask(String input) {
        String[] array = input.split(" ");
        int taskNo = Integer.parseInt(array[1]) - 1;
        Task task = tasks.getTask(taskNo); //throws error if out of bounds
        task.unmarkTask();
        ui.unmarkTask(task);
    }

    private static void createTodo(String input) throws PingguException {
        if (input.trim().equals("todo")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a task to remind you of!");
        }
        String description = input.substring(5).trim();
        addTask(new Todo(description));
    }

    private static void createDeadLine(String input) throws PingguException {
        if (input.trim().equals("deadline")) { //remove white space and check string equality
            throw new PingguException("Pinggu needs a deadline task description!");
        }
        int byDate = input.indexOf("/by");
        if (byDate == -1) { //cannot find a due date
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        String description = input.substring(9, byDate).trim();
        String by = input.substring(byDate + 4).trim();
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (by.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /by <yyyy-mm-dd> into your description!");
        }
        try {
            addTask(new Deadline(description, by));
        } catch (DateTimeParseException e) {
            ui.printMessage("Pinggu needs a due date of <yyyy-mm-dd> format!");
        }

    }

    private static void createEvent(String input) throws PingguException {
        if (input.trim().equals("event")) {
            throw new PingguException("Pinggu needs an event description!");
        }
        int fromDate = input.indexOf("/from");
        int toDate = input.indexOf("/to");
        if (fromDate == -1 || toDate == -1) {
            throw new PingguException("Pinggu needs a start and end date! "
                    + "Your description should have /from <yyyy-mm-dd> and /to <yyyy-mm-dd>!");

        }
        String description = input.substring(6, fromDate).trim();
        String from = input.substring(fromDate + 6, toDate).trim();
        String to = input.substring(toDate + 4).trim();
        if (description.isEmpty()) {
            throw new PingguException("Pinggu needs a description!");
        }
        if (from.isEmpty()) {
            throw new PingguException("Pinggu needs a start date! "
                    + "Add /from <yyyy-mm-dd> into your description!");
        }
        if (to.isEmpty()) {
            throw new PingguException("Pinggu needs a due date! "
                    + "Add /to <yyyy-mm-dd> into your description!");
        }
        try {
            addTask(new Event(description, from, to));
        } catch (DateTimeParseException e) {
            ui.printMessage("Pinggu needs a start and due dates of <yyyy-mm-dd> format!");
        }
    }

    private static void deleteTask(String input) throws PingguException {
        if (input.trim().equals("delete")) {
            throw new PingguException("Pinggu needs a task number to delete!");
        }
        String[] array = input.split(" ");
        int taskToDelete = Integer.parseInt(array[1]) - 1;
        Task task = tasks.getTask(taskToDelete); //throws error if out of bounds
        tasks.deleteTask(taskToDelete);
        ui.showDelete(task, tasks.getSize());
    }
}
