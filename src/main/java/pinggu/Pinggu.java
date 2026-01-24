package pinggu;

import pinggu.exception.PingguException;
import pinggu.parser.Parser;
import pinggu.storage.Storage;
import pinggu.task.Task;
import pinggu.task.TaskList;
import pinggu.ui.Ui;

import java.time.format.DateTimeParseException;

public class Pinggu {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    public static final String filePath = "./data/pinggu.txt";

    public Pinggu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (PingguException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readLine();
            boolean isModified = false; //check if we changed our tasks

            try {
                Parser.Commands cmd = Parser.parseCommand(input); //returns enum, will throw IllegalArgumentException
                switch (cmd) {
                case BYE:
                    ui.showExit();
                    return;
                case LIST:
                    ui.printTaskList(tasks);
                    break;
                case MARK:
                    int markIndex = Parser.parseIndex(input);
                    Task taskToMark = tasks.getTask(markIndex); //throws error if out of bounds
                    taskToMark.markTask();
                    ui.markTask(taskToMark);
                    isModified = true;
                    break;
                case UNMARK:
                    int unmarkIndex = Parser.parseIndex(input);
                    Task taskToUnmark = tasks.getTask(unmarkIndex); //throws error if out of bounds
                    taskToUnmark.unmarkTask();
                    ui.unmarkTask(taskToUnmark);
                    isModified = true;
                    break;
                case TODO:
                    Task todo = Parser.createTodo(input);
                    tasks.addTask(todo);
                    ui.showAdd(todo, tasks.getSize());
                    isModified = true;
                    break;
                case DEADLINE:
                    Task deadLine = Parser.createDeadline(input);
                    tasks.addTask(deadLine);
                    ui.showAdd(deadLine, tasks.getSize());
                    isModified = true;
                    break;
                case EVENT:
                    Task event = Parser.createEvent(input);
                    tasks.addTask(event);
                    ui.showAdd(event, tasks.getSize());
                    isModified = true;
                    break;
                case DELETE:
                    int deleteIndex = Parser.parseIndex(input);
                    Task taskToDelete = tasks.getTask(deleteIndex); //throws error if out of bounds
                    tasks.deleteTask(deleteIndex);
                    ui.showDelete(taskToDelete, tasks.getSize());
                    isModified = true;
                    break;
                }
                if (isModified) {
                    storage.save(tasks.getTasks());
                }
            } catch (NumberFormatException e) { //has to come before IllegalArgumentException as it extends that
                ui.printMessage("pinggu.Pinggu needs a valid number!");
            } catch (IllegalArgumentException e) {
                ui.printMessage("Noot Noot! pinggu.Pinggu does not recognize this command!");
            } catch (PingguException e) {
                ui.printMessage(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                ui.printMessage("pinggu.Pinggu does not have this task number! "
                        + "The max is " + tasks.getSize());
            } catch (DateTimeParseException e) {
                ui.printMessage("pinggu.Pinggu needs a valid date of <yyyy-mm-dd>!");
            }
        }
    }

    public static void main(String[] args) {
        new Pinggu(filePath).run();
    }
}
