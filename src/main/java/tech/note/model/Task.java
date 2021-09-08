package tech.note.model;


public class Task {

    private int id;
    private String description;
    private boolean done;

    public Task() {
    }

    /**
     * @param id to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     */
    public Task(int id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }

}
