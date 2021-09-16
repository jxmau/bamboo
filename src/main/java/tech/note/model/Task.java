package tech.note.model;


import java.util.ArrayList;
import java.util.List;

public class Task {

    private String taskId;
    private int order;
    private String description;
    private boolean done;
    private List<Task> subtasks;

    public Task() {
    }

    /**
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     */
    public Task(int order, String description, boolean done) {
        this.taskId = "N/A";
        this.order = order;
        this.description = description;
        this.done = done;
        this.subtasks = new ArrayList<>();
    }

    /**
     * Constructor Method used when parsing the JSON File.
     * @param taskId to identify the task or the subtask in the db.
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     * @param subtasks those are the subtask of the task.
     */
    public Task(String taskId, int order, String description, boolean done, List<Task> subtasks) {
        this.taskId = taskId;
        this.order = order;
        this.description = description;
        this.done = done;
        this.subtasks = subtasks;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", order=" + order +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", subtasks=" + subtasks +
                '}';
    }
}
