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
    private final Storage storage;
    private final Ui ui;
    private boolean hasLoadError = false;
    private String commandType;

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
            this.hasLoadError = true;
            tasks = new TaskList();
        }
    }

    /**
     * Displays welcome message to user.
     *
     * @return Welcome message.
     */
    public String getWelcomeMessage() {
        return ui.showWelcomeMessage();
    }

    /**
     * Returns a Boolean to indicate if save file was loaded successfully.
     *
     * @return Boolean value indicating loading status.
     */
    public boolean getHasLoadError() {
        return hasLoadError;
    }

    /**
     * Runs the chat app.
     */
    public String run(String input) {
        assert input != null : "Input must not be null";
        assert ui != null : "Ui component must exist";
        assert storage != null : "Storage component must exist";
        assert tasks != null : "TaskList component must exist";
        try {
            return executeCommand(input);
        } catch (NumberFormatException e) { //has to come before IllegalArgumentException as it extends that
            return ui.showErrorMessage("Pinggu needs a valid number!");
        } catch (IllegalArgumentException e) {
            return ui.showErrorMessage("Pinggu does not recognize this command!");
        } catch (PingguException e) {
            return ui.showErrorMessage(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            return ui.showErrorMessage("Pinggu does not have this task number! "
                    + "The max is " + tasks.getSize() + ".");
        } catch (DateTimeParseException e) {
            return ui.showErrorMessage("Pinggu needs a valid date of <yyyy-mm-dd>!");
        }
    }

    /**
     * Gets the command type.
     *
     * @return Command type.
     */
    public String getCommandType() {
        return this.commandType;
    }

    private String executeCommand(String input) throws PingguException, IllegalArgumentException,
            IndexOutOfBoundsException {

        // needs to come before parseCommand else it will be skipped on illegal commands
        this.commandType = Constants.COMMAND_TYPE_DEFAULT;
        Parser.Commands cmd = Parser.parseCommand(input); //returns enum, will throw IllegalArgumentException

        switch (cmd) {
        case BYE:
            return ui.showExitMessage();
        case LIST:
            return ui.printTaskList(tasks);
        case MARK:
            return executeMarkCommand(input);
        case UNMARK:
            return executeUnmarkCommand(input);
        case TODO:
        case DEADLINE:
        case EVENT:
            return executeAddCommand(cmd, input);
        case DELETE:
            return executeDeleteCommand(input);
        case FIND:
            return executeFindCommand(input);
        default: //catch new commands in enum that has not been implemented
            throw new PingguException("This command is valid, but Pinggu has not learned it yet!");
        }

    }

    private String executeMarkCommand(String input) throws IndexOutOfBoundsException {
        int markIndex = Parser.parseInputIndex(input);
        Task taskToMark = tasks.getTask(markIndex); //throws error if out of bounds
        assert taskToMark != null : "Task to be marked must exist";
        taskToMark.setDone();
        storage.save(tasks.getTasks());
        this.commandType = Parser.Commands.MARK.name();
        return ui.showMarkTaskMessage(taskToMark);
    }

    private String executeUnmarkCommand(String input) throws IndexOutOfBoundsException {
        int unmarkIndex = Parser.parseInputIndex(input);
        Task taskToUnmark = tasks.getTask(unmarkIndex); //throws error if out of bounds
        assert taskToUnmark != null : "Task to be unmarked must exist";
        taskToUnmark.setNotDone();
        storage.save(tasks.getTasks());
        return ui.showUnmarkTaskMessage(taskToUnmark);
    }

    private String executeDeleteCommand(String input) throws IndexOutOfBoundsException {
        int deleteIndex = Parser.parseInputIndex(input);
        Task taskToDelete = tasks.getTask(deleteIndex); //throws error if out of bounds
        assert taskToDelete != null : "Task to be deleted must exist";
        tasks.deleteTask(deleteIndex);
        storage.save(tasks.getTasks());
        this.commandType = Parser.Commands.DELETE.name();
        return ui.showDeleteMessage(taskToDelete, tasks.getSize());
    }

    private String executeAddCommand(Parser.Commands cmd, String input) throws PingguException {
        Task newTask;
        switch (cmd) {
        case TODO:
            newTask = Parser.createTodo(input);
            break;
        case DEADLINE:
            newTask = Parser.createDeadline(input);
            break;
        case EVENT:
            newTask = Parser.createEvent(input);
            break;
        default:
            throw new PingguException("Unexpected add value: " + cmd); //this path should never be reached.
        }
        tasks.addTask(newTask);
        storage.save(tasks.getTasks());
        this.commandType = Constants.COMMAND_TYPE_ADD;
        return ui.showAddMessage(newTask, tasks.getSize());
    }

    private String executeFindCommand(String input) throws PingguException {
        String keyword = Parser.parseFindKeyword(input);
        TaskList matchingTasks = tasks.findTasks(keyword);
        assert matchingTasks != null : "Task list must exist after finding tasks";
        return ui.showFindMessage(matchingTasks);
    }
}

