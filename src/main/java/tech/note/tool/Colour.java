package tech.note.tool;

public enum Colour {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    RESET("\u001B[0m");
    private final String code;

    Colour(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
