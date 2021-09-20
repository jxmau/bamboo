package tech.note.model;

public class Subtask extends BambooTask{


    /**
     * Method used when creating a new Subtask.
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     */
    public Subtask(int order, String description, boolean done) {
        this.id = "N/A";
        this.order = order;
        this.description = description;
        this.done = done;
    }

    /**
     * Method used when getting Subtask's data through Sync or JSON parsing.
     * @param order to differentiate tasks in list and in file.
     * @param description Used as the description of the task.
     * @param done Used to know if task is complete or not.
     */
    public Subtask(String subtaskId, int order, String description, boolean done) {
        this.id = subtaskId;
        this.order = order;
        this.description = description;
        this.done = done;
    }


    @Override
    String toMapString() {
        return "{\"order\":\"" + order + "\"" +
                ",\"subtasksId\":\"" + id + "\"" +
                ",\"description\":\"" + description + "\"" +
                ",\"done\":\"" + done + "\"}";
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id='" + id + '\'' +
                ", order=" + order +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
