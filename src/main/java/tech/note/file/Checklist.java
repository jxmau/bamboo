package tech.note.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This Class contains the static files to check Bamboo's directories.
 */
public class Checklist {

    public static void runStartUpCheckList(){
        checkDirectory(Path.MAIN_DIRECTORY);
        checkDirectory(Path.LISTS_DIRECTORY);
        System.out.println("Bamboo is running.");
    }

    private static void checkDirectory(Path PATH){
        File directory = new File(PATH.getPath());
        System.out.print("Checking " + PATH.getName() + " ...... ");
        if (!directory.exists()){
            directory.mkdir();
            System.out.println("CREATED");
        } else {
            System.out.println("DONE");
        }
    }


}
