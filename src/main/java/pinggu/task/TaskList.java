package pinggu.task;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskList that contains all the tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Initializes TaskList object as empty ArrayList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Sets TaskList with Tasks from existing save file.
     *
     * @param tasks The TaskList loaded from save file.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to TaskList.
     *
     * @param tasks The tasks to be added.
     */
    public void addTask(Task... tasks) {
        for (Task task : tasks) {
            this.tasks.add(task);
        }
    }

    /**
     * Deletes a task from TaskList given its index.
     *
     * @param index The index of the task to be deleted (0-indexing).
     */
    public void deleteTask(int index) {
        this.tasks.remove(index);
    }

    /**
     * Gets a task from TaskList given its index.
     *
     * @param index The index of task to retrieve (0-indexing).
     * @return The task in TaskList with the index.
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Returns size of TaskList.
     *
     * @return Size of TaskList.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns all tasks in TaskList.
     * @return Tasks in TaskList.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds tasks in TaskList containing the specific keyword in their description.
     *
     * @param keyword The String containing keyword to search for in description.
     * @return A new TaskList containing only tasks with the keyword.
     */
    public TaskList findTasks(String keyword) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(keyword)) {
                filteredTasks.add(task);
            }
        }
        return new TaskList(filteredTasks);
    }
}
