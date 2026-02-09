package pinggu.task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskList that contains all the tasks.
 */
public class TaskList {
    private final List<Task> tasks;

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
        assert tasks != null : "Loaded task list cannot be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to TaskList.
     *
     * @param tasks The tasks to be added.
     */
    public void addTask(Task... tasks) {
        assert tasks != null : "TaskList to add to cannot be null";
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
        assert keyword != null : "Keyword for finding tasks cannot be null";
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toList());
        return new TaskList(filteredTasks);
    }

    /**
     * Checks if TaskList contains identical task.
     *
     * @param task The task to check for.
     * @return True if TaskList contains a duplicate task, false otherwise.
     */
    public boolean hasDuplicate(Task task) {
        return tasks.stream().anyMatch(t -> t.equals(task));
    }
}
