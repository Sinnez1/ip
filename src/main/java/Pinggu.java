
import java.time.format.DateTimeParseException;

public class Pinggu {
    private static TaskList tasks;
    private static Storage storage;
    private static Ui ui;
    public static final String filePath = "./data/pinggu.txt";


    public static void main(String[] args) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
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
                    listTasks();
                    break;
                case MARK:
                    int markIndex = Parser.parseIndex(input);
                    createMarkTask(markIndex);
                    isModified = true;
                    break;
                case UNMARK:
                    int unmarkIndex = Parser.parseIndex(input);
                    createUnmarkTask(unmarkIndex);
                    isModified = true;
                    break;
                case TODO:
                    Task todo = Parser.createTodo(input);
                    addTask(todo);
                    isModified = true;
                    break;
                case DEADLINE:
                    Task deadLine = Parser.createDeadline(input);
                    addTask(deadLine);
                    isModified = true;
                    break;
                case EVENT:
                    Task event = Parser.createEvent(input);
                    addTask(event);
                    isModified = true;
                    break;
                case DELETE:
                    int deleteIndex = Parser.parseIndex(input);
                    deleteTask(deleteIndex);
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
            } catch (DateTimeParseException e) {
                ui.printMessage("Pinggu needs a valid date of <yyyy-mm-dd>!");
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

    private static void createMarkTask(int index) {
        Task task = tasks.getTask(index); //throws error if out of bounds
        task.markTask();
        ui.markTask(task);
    }

    private static void createUnmarkTask(int index) {
        Task task = tasks.getTask(index); //throws error if out of bounds
        task.unmarkTask();
        ui.unmarkTask(task);
    }


    private static void deleteTask(int input) throws PingguException {
        Task task = tasks.getTask(input); //throws error if out of bounds
        tasks.deleteTask(input);
        ui.showDelete(task, tasks.getSize());
    }
}
