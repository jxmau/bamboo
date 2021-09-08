package tech.note.file;

import java.io.File;

public enum Path {
    /**
     * Main Directory where the files regarding bamboo will be saved
     */
    MAIN_DIRECTORY("Main directory",System.getProperty("user.home") + File.separator + "Bamboo"),
    /**
     * Directory where the lists for the lists of tasks will be saved.
     */
    LISTS_DIRECTORY("Lists directory", MAIN_DIRECTORY.getPath() + File.separator + "lists"),
    /**
     * Path to get a list. To assemble with the name of the list and the extension
     */
    LIST_PATH("List path", LISTS_DIRECTORY.getPath() + File.separator);
    private final String name;
    private final String path;

    Path(String name,String path){
        this.name = name;
        this.path = path;
    }

    /**
     *
     * @return the path of the enum
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return the name of the Path enum.
     */
    public String getName() {
        return name;
    }
}
