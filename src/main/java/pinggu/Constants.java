package pinggu;

/**
 * Stores constant values used across application.
 */
public class Constants {

    //delimiter for save file
    public static final String SAVEFILE_DELIMITER = "\\|";

    //strings used for start and end dates
    public static final String DEADLINE_DUE_PREFIX = "/by";
    public static final String EVENT_START_PREFIX = "/from";
    public static final String EVENT_END_PREFIX = "/to";

    public static final int DEADLINE_LENGTH = 9; //length of deadline and a whitespace
    public static final int TODO_LENGTH = 5; ///length of todo and a white space
    public static final int EVENT_LENGTH = 6; //length of event and a whitespace

    //command types for GUI
    public static final String COMMAND_TYPE_DEFAULT = "default";
    public static final String COMMAND_TYPE_ADD = "add";
}
