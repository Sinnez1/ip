package pinggu.parser;

import org.junit.jupiter.api.Test;
import pinggu.exception.PingguException;
import pinggu.task.Deadline;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

    @Test
    public void createDeadline_validInput_success() {
        try {
            Deadline deadline = Parser.createDeadline("deadline return book /by 2026-01-25");
            assertEquals("[D][ ] return book (by: Jan 25 2026)", deadline.toString());
        } catch (PingguException e) {
            fail("No exception should be thrown" + e.getMessage());
        }
    }

    @Test
    public void createDeadline_wrongDateFormat_throwsDateTimeParseException() {
        String input = "deadline return book /by 25-01-2026";
        assertThrows(DateTimeParseException.class, () -> Parser.createDeadline(input));
    }

    @Test
    public void parseCommand_invalidCommand_IllegalArgumentException() {
        String invalidCommand = "help do homework";
        assertThrows(IllegalArgumentException.class, () -> Parser.parseCommand(invalidCommand));
    }

    @Test
    public void parseCommand_validCommand_returnCommandInUpperCase() {
        String validCommand = "todo homework";
        assertEquals(Parser.Commands.TODO, Parser.parseCommand(validCommand));
    }
}
