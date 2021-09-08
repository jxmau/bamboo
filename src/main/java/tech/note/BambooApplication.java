package tech.note;


import tech.note.file.Checklist;
import tech.note.file.FileController;

import java.util.Scanner;

public class BambooApplication {

    public static void main(String[] args) {
        System.out.println("""
                    ____                  __             \s
                   / __ )____ _____ ___  / /_  ____  ____\s
                  / __  / __ `/ __ `__ \\/ __ \\/ __ \\/ __ \\
                 / /_/ / /_/ / / / / / / /_/ / /_/ / /_/ /
                /_____/\\__,_/_/ /_/ /_/_.___/\\____/\\____/\s
                                                         \s
                """);
        Checklist.runStartUpCheckList();
        System.out.println("\n\n");
        boolean exit = false;
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.print( " Bamboo > ");
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "new" -> createList(command, scanner);
                case "cd" -> enterTasksShell(command, scanner);
                case "ls" -> FileController.getListOfNameOfList().forEach(System.out::println);
                case "delete" -> deleteList(command);
                case "exit" -> exit = true;
            }

            System.out.println("\n\n");
        }
    }

    /**
     *
     * @param command Need the user input to create a new list and to see if the name's not already used.
     *                Only the first word will be used to name the list (command[1]).
     * @param scanner The scanner will be passed as the variable when entering the Task Shell after creating a new list.
     */
    private static void createList(String[] command, Scanner scanner) {
        if (command[1] == null) {
            System.out.println("You need a name for your list.");
        } else if (FileController.doesListAlreadyExists(command[1])) {
            System.out.println("You already have a list named " + command[1]);
        } else {
            FileController.createListFile(command[1]);
            new TasksShell(command[1], scanner).start();
        }
    }

    /**
     *
     * @param command Need user input to check if the list does exit and to load the file.
     * @param scanner The scanner is required as parameter when entering the Task shell.
     */
    private static void enterTasksShell(String[] command, Scanner scanner){
        if (command[1] == null) {
            System.out.println("Void name.");
        } else if (FileController.doesListAlreadyExists(command[1])) {
            new TasksShell(command[1], scanner).start();
        } else {
            System.out.println("Bamboo couldn't find a list named " + command[1]);
        }
    }

    /**
     *
     * @param command user input required to check if list does exist and to delete it.
     */
    private static void deleteList(String[] command) {
        if (FileController.doesListAlreadyExists(command[1])) {
            FileController.deleteListFile(command[1]);
        } else {
            System.out.println("Bamboo couldn't find the list.");
        }
    }
}
