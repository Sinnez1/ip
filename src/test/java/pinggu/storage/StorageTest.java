package pinggu.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import pinggu.exception.PingguException;
import pinggu.task.Deadline;
import pinggu.task.Event;
import pinggu.task.TaskList;
import pinggu.task.Todo;

public class StorageTest {

    @TempDir
    Path tempDirectory;

    @Test
    public void saveAndLoad_validTasks_success() {
        String fileName = "test_save.txt";
        Storage storage = new Storage(tempDirectory.toString(), fileName);

        TaskList testTaskList = new TaskList();
        Todo todoTest = new Todo("read book");
        Deadline deadlineTest = new Deadline("read book2", "2026-02-09");
        Event eventTest = new Event("read book3", "2026-02-09", "2026-02-20");

        testTaskList.addTask(todoTest, deadlineTest, eventTest);

        assertEquals(3, testTaskList.getSize());
        storage.save(testTaskList);
        try {
            TaskList loadedTaskList = storage.load();
            assertEquals(3, loadedTaskList.getSize());
            assertEquals("[T][ ] read book", loadedTaskList.getTask(0).toString());
            assertEquals("[D][ ] read book2 (by: Feb 09 2026)", loadedTaskList.getTask(1).toString());
            assertEquals("[E][ ] read book3 (from: Feb 09 2026 to: Feb 20 2026)",
                    loadedTaskList.getTask(2).toString());

        } catch (PingguException e) {
            fail("Exception should not be thrown for a valid file:" + e.getMessage());
        }
    }

    @Test
    public void loadFile_wrongDate_skipsLineAndDoesNotCrash() {
        File corruptedFile = tempDirectory.resolve("corrupted.txt").toFile();

        try (FileWriter fw = new FileWriter(corruptedFile)) {
            fw.write("TODO | 0 | valid todo\n");
            fw.write("DEADLINE | 0 | valid description | wrong date\n");
            fw.write("TODO | 0 | another valid todo\n");
        } catch (IOException e) {
            fail("File should be able to be written to in test:" + e.getMessage());
        }

        Storage storage = new Storage(tempDirectory.toString(), "corrupted.txt");

        try {
            TaskList loadedTasks = storage.load();

            assertEquals(2, loadedTasks.getSize(),
                    "Should parse 2 valid tasks and skip the invalid one");

            assertEquals("[T][ ] valid todo", loadedTasks.getTask(0).toString());
            assertEquals("[T][ ] another valid todo", loadedTasks.getTask(1).toString());

        } catch (PingguException e) {
            fail("Storage crashed on corrupt file instead of handling valid ones only: " + e.getMessage());
        }
    }
}
