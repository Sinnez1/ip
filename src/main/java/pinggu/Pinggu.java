package pinggu;

import java.time.format.DateTimeParseException;

import pinggu.exception.PingguException;
import pinggu.parser.Parser;
import pinggu.storage.Storage;
import pinggu.task.Task;
import pinggu.task.TaskList;
import pinggu.ui.Ui;

/**
 * Runs Pinggu chat app.
 */
public class Pinggu {
    public static final String FILEPATH = "./data/pinggu.txt";
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Initializes chat app with the given file path.
     *
     * @param filePath The file path to store and load save file from.
     */
    public Pinggu(String filePath) {
        ui = new Ui();
        storage = new Storage(FILEPATH);
        try {
            tasks = new TaskList(storage.load());
        } catch (PingguException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chat app.
     */
    public void run() {
        ui.showWelcomeMessage();

        while (true) {
            String input = ui.readNextLine();
            boolean isModified = false; //check if we changed our task;

            try {
                Parser.Commands cmd = Parser.parseCommand(input); //returns enum, will throw IllegalArgumentException
                switch (cmd) {
                case BYE:
                    ui.showExitMessage();
                    return;
                case LIST:
                    ui.printTaskList(tasks);
                    break;
                case MARK:
                    int markIndex = Parser.parseInputIndex(input);
                    Task taskToMark = tasks.getTask(markIndex); //throws error if out of bounds
                    taskToMark.setDone();
                    ui.showMarkTaskMessage(taskToMark);
                    isModified = true;
                    break;
                case UNMARK:
                    int unmarkIndex = Parser.parseInputIndex(input);
                    Task taskToUnmark = tasks.getTask(unmarkIndex); //throws error if out of bounds
                    taskToUnmark.setNotDone();
                    ui.showUnmarkTaskMessage(taskToUnmark);
                    isModified = true;
                    break;
                case TODO:
                    Task todo = Parser.createTodo(input);
                    tasks.addTask(todo);
                    ui.showAddMessage(todo, tasks.getSize());
                    isModified = true;
                    break;
                case DEADLINE:
                    Task deadLine = Parser.createDeadline(input);
                    tasks.addTask(deadLine);
                    ui.showAddMessage(deadLine, tasks.getSize());
                    isModified = true;
                    break;
                case EVENT:
                    Task event = Parser.createEvent(input);
                    tasks.addTask(event);
                    ui.showAddMessage(event, tasks.getSize());
                    isModified = true;
                    break;
                case DELETE:
                    int deleteIndex = Parser.parseInputIndex(input);
                    Task taskToDelete = tasks.getTask(deleteIndex); //throws error if out of bounds
                    tasks.deleteTask(deleteIndex);
                    ui.showDeleteMessage(taskToDelete, tasks.getSize());
                    isModified = true;
                    break;
                case FIND:
                    String keyword = Parser.parseFindKeyword(input);
                    TaskList matchingTasks = tasks.findTasks(keyword);
                    ui.showFindMessage(matchingTasks);
                    break;
                default: //catch new commands in enum that has not been implemented
                    throw new PingguException("This command is valid, but Pinggu has not learned it yet!");
                    //default ends here
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

    /**
     * Starts the chat app.
     *
     * @param args None.
     */
    public static void main(String[] args) {
        new Pinggu(FILEPATH).run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Pinggu heard: " + input;
    }
}
