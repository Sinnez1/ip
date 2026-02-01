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
    private boolean isLoadError = false;

    /**
     * Initializes chat app with the given file path.
     *
     * @param filePath The file path to store and load save file from.
     */
    public Pinggu(String filePath) {
        ui = new Ui();
        storage = new Storage(".", "data", "pinggu.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (PingguException e) {
            this.isLoadError = true;
            tasks = new TaskList();
        }
    }

    public String getWelcomeMessage() {
        return ui.showWelcomeMessage();
    }

    public boolean getIsLoadError() {
        return isLoadError;
    }

    /**
     * Runs the chat app.
     */
    public String run(String input) {
        try {
            Parser.Commands cmd = Parser.parseCommand(input); //returns enum, will throw IllegalArgumentException
            String response = "";
            boolean isModified = false; //check if we changed our task;

            switch (cmd) {
            case BYE:
                return ui.showExitMessage();
            case LIST:
                return ui.printTaskList(tasks);
            case MARK:
                int markIndex = Parser.parseInputIndex(input);
                Task taskToMark = tasks.getTask(markIndex); //throws error if out of bounds
                taskToMark.setDone();
                response = ui.showMarkTaskMessage(taskToMark);
                isModified = true;
                break;
            case UNMARK:
                int unmarkIndex = Parser.parseInputIndex(input);
                Task taskToUnmark = tasks.getTask(unmarkIndex); //throws error if out of bounds
                taskToUnmark.setNotDone();
                response = ui.showUnmarkTaskMessage(taskToUnmark);
                isModified = true;
                break;
            case TODO:
                Task todo = Parser.createTodo(input);
                tasks.addTask(todo);
                response = ui.showAddMessage(todo, tasks.getSize());
                isModified = true;
                break;
            case DEADLINE:
                Task deadLine = Parser.createDeadline(input);
                tasks.addTask(deadLine);
                response = ui.showAddMessage(deadLine, tasks.getSize());
                isModified = true;
                break;
            case EVENT:
                Task event = Parser.createEvent(input);
                tasks.addTask(event);
                response = ui.showAddMessage(event, tasks.getSize());
                isModified = true;
                break;
            case DELETE:
                int deleteIndex = Parser.parseInputIndex(input);
                Task taskToDelete = tasks.getTask(deleteIndex); //throws error if out of bounds
                tasks.deleteTask(deleteIndex);
                response = ui.showDeleteMessage(taskToDelete, tasks.getSize());
                isModified = true;
                break;
            case FIND:
                String keyword = Parser.parseFindKeyword(input);
                TaskList matchingTasks = tasks.findTasks(keyword);
                return ui.showFindMessage(matchingTasks);
            default: //catch new commands in enum that has not been implemented
                throw new PingguException("This command is valid, but Pinggu has not learned it yet!");
                //default ends here
            }
            if (isModified) {
                storage.save(tasks.getTasks());
            }
            return response;
        } catch (NumberFormatException e) { //has to come before IllegalArgumentException as it extends that
            return ui.showErrorMessage(" Pinggu needs a valid number!");
        } catch (IllegalArgumentException e) {
            return ui.showErrorMessage(" Pinggu does not recognize this command!");
        } catch (PingguException e) {
            return ui.showErrorMessage(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return ui.showErrorMessage(" Pinggu does not have this task number! "
                    + "The max is " + tasks.getSize() + ".");
        } catch (DateTimeParseException e) {
            return ui.showErrorMessage(" Pinggu needs a valid date of <yyyy-mm-dd>!");
        }
    }
}

