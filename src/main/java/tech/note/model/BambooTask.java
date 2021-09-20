package tech.note.model;

/**
 * This is the superclass of both the Task Object and the Subtask object.
 */
public abstract class BambooTask {
    protected String id;
    protected int order;
    protected String description;
    protected boolean done;


    // Boiler plates


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    // Abstract Methods

    abstract  String toMapString();
}
