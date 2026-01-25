package pinggu.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    class TaskStub extends Task {

        public TaskStub() {
            super("stub test");
        }

        public TaskStub(String input) {
            super(input);
        }

        @Override
        public String toString() {
            return "This is " + this.description;
        }
    }

    @Test
    public void addTask_stubTask_listSizeIncreaseAndTaskStored() {
        TaskList taskList = new TaskList();
        taskList.addTask(new TaskStub());

        assertEquals(1, taskList.getSize());

        assertEquals("This is stub test", taskList.getTask(0).toString());
    }

    @Test
    public void deleteTask_stubTask_removeTaskAndDecreaseList() {
        TaskList taskList = new TaskList();

        TaskStub stub1 = new TaskStub("stub 1");
        TaskStub stub2 = new TaskStub("stub 2");
        TaskStub stub3 = new TaskStub("stub 3");

        taskList.addTask(stub1);
        taskList.addTask(stub2);
        taskList.addTask(stub3);

        assertEquals(3, taskList.getSize()); //check size is 3

        Task toDelete = taskList.getTask(1); //get second task
        taskList.deleteTask(1); //delete second task

        //check that we actually deleted correct item
        assertEquals("This is stub 2", toDelete.toString());

        assertEquals(2, taskList.getSize()); //check decrease in size

        assertEquals("This is stub 1", taskList.getTask(0).toString()); //task 1 remains

        assertEquals("This is stub 3", taskList.getTask(1).toString()); //check that task 3 moved up

    }
}
