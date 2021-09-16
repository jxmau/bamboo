package tech.note.model;

public enum MessageBuilderMessage {
    NO_INDEX_FOUND("No index found."),
    INCORRECT_INDEX("The index was not valid."),
    NO_DESCRIPTION_FOUND("No description was found."),
    NO_SUBTASKS_SAVED("No subtasks were found for this task.");
    private final String message;

    MessageBuilderMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
