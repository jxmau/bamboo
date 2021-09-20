package tech.note.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Task extends BambooTask{

    private List<Subtask> subtasks;

    public Task() {
    }

    /**
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     */
    public Task(int order, String description, boolean done) {
        this.id = "N/A";
        this.order = order;
        this.description = description;
        this.done = done;
        this.subtasks = new ArrayList<>();
    }

    /**
     * Constructor Method used when parsing the JSON File.
     * @param id to identify the task or the subtask in the db.
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     * @param subtasks those are the subtask of the task.
     */
    public Task(String id, int order, String description, boolean done, List<Subtask> subtasks) {
        this.id = id;
        this.order = order;
        this.description = description;
        this.done = done;
        this.subtasks = subtasks;
    }


    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + id +
                ", order=" + order +
                ", description='" + description + '\'' +
                ", done=" + done +
                ", subtasks=" + subtasks +
                '}';
    }

    @Override
    public String toMapString() {
        return "{\"order\":\"" + order + "\"" +
                ",\"taskId\":\""+ id + "\"" +
                ",\"description\":\"" + description + "\"" +
                ",\"done\":\"" + done + "\"" +
                // As it an array, there's no need to put it in quote, but in squared brackets
                ",\"subtasks\":\"" +concatSubtasksMapString() + "\"}";
    }

    private String concatSubtasksMapString(){
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append("[");
        for (Subtask subtask : subtasks) {
            bobTheBuilder
                    .append(subtasks.indexOf(subtask) != 0 ? "," : "")
                    .append(subtask.toMapString());
        }
        bobTheBuilder.append("]");
        return bobTheBuilder.toString().replace("\"", "||");
    }
}
