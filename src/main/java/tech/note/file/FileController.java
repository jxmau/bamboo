package tech.note.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileController {

    /**
     *
     * @param listName name of list to build its path.
     */
    public static void createListFile(String listName){
        try {
            FileWriter file = new FileWriter(Path.LIST_PATH.getPath() + listName + ".json");
            file.write("[]");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param listName name of list to build its path.
     * @return true if file's been deleted.
     */
    public static void deleteListFile(String listName){
        new File(Path.LIST_PATH.getPath() + listName + ".json").delete();
    }

    /**
     *
     * @param listName name of list to build its path.
     * @return true if already exists.
     */
    public static boolean doesListAlreadyExists(String listName){
        return new File(Path.LIST_PATH.getPath() + listName + ".json").exists();
    }

    /**
     *
     * @return the name of all the lists from their files.
     */
    public static List<String> getListOfNameOfList(){
        String[] filesName = new File(Path.LISTS_DIRECTORY.getPath()).list();
        List<String> names = new ArrayList<>();
        for (String name : filesName){
            if (name.endsWith(".json")) {
                names.add(name.replace(".json", ""));
            }
        }
        return names;

    }

}
