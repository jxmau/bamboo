package tech.note;

import tech.note.file.ListFile;
import tech.note.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TasksShell {
    /**
     * Name of the list of the task.
     * Used to get access to the list file.
     */
    private final String listName;
    /**
     * Scanner passed as param.
     */
    private final Scanner scan;
    /**
     * Object allowing to fetch tasks and save them.
     */
    private final ListFile listFile;
    /**
     * If true, the while loop will end.
     */
    private boolean exit = false;
    /**
     * If true, hide done tasks.
     */
    private boolean hide = true;
    /**
     * At each iteration in the while loop, the message will be printed after the tasks.
     * message consists of an error message or other.
     */
    private String message = "";
    /**
     * Lists hosting the tasks.
     */
    private List<Task> tasks;




    public TasksShell(String listName, Scanner scan) {
        this.listName = listName;
        this.scan = scan;
        this.listFile = new ListFile(this.listName);
        this.tasks = listFile.getTasks();
    }

    public void start(){
        while (!exit) {
            printList();
            printMessage();
            System.out.print(" " + listName + " > ");
            String[] command = scan.nextLine().split(" ");
            switch (command[0]){
                case "add" -> addTask(command);
                case "delete" -> deleteTask(command);
                case "done", "undone" -> permuteTask(command);
                case "modify" -> modifyTask(command);
                case "exit" -> exit = true;
                case "hide", "show" -> hide = !hide;
                case "help" -> printHelp();
                case "clear" -> clear();
            }
            listFile.saveList(tasks);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

    }

    /**
     * Method to print the list of tasks. Two StringBuilder are used,
     * separating the done and undone tasks.
     */
    private void printList(){
        StringBuilder tasksDone = new StringBuilder();
        StringBuilder tasksNotDone = new StringBuilder();
            for (Task task : tasks) {
                StringBuilder line = new StringBuilder();
                if ((task.isDone() && !hide) || !task.isDone()) {
                    line.append(
                            tasks.indexOf(task) + " " +
                                    (task.isDone() ? "[X]" : "[ ]") +
                                    " - " + task.getDescription() + "\n"
                    );
                    if (task.isDone()) {
                        tasksDone.append(line);
                    } else {
                        tasksNotDone.append(line);
                    }

                }
            }
            if (tasksNotDone.equals("")) {
                System.out.println("You don't have unfinished tasks!");
            }
            System.out.println(tasksDone + " \n" + tasksNotDone);
    }

    /**
     * Will print the message if not empty and reset it once printed.
     */
    private void printMessage(){
        if (!message.equals("")) {
            System.out.println(message);
            message = "";
        }
    }

    /**
     * This method will add a task to the list.
     * @param command is the command line from the user.
     */
    private void addTask(String[] command){
        if (command[1] != null) {
            tasks.add(new Task(tasks.size(), concatenateWordsFromArray(command), false));
            listFile.saveList(tasks);
            tasks = listFile.getTasks();
        } else {
            message += "No task to be added were found in your command line.\n";
        }
    }

    /**
     * When modifying or permuting the status of a task, this method will
     * check if the input reserved for the index is a numeric String.
     * @param index String supposed to be the index.
     * @return if index is only numeric.
     */
    private boolean checkIndexInCommand(String index){
        return Pattern.compile("\\d+").matcher(index).matches() && Integer.parseInt(index) < tasks.size();
    }

    /**
     * Will delete the task from the list.
     * It will not save them as it done at each while loop iteration.
     * @param command This is the user input. The method will check if the index is valid or not.
     */
    private void deleteTask(String[] command){
        if (checkIndexInCommand(command[1])) {
            tasks.remove(Integer.parseInt(command[1]));
        } else {
            message += "Bamboo couldn't find the index to the task to be deleted.\n";
        }
    }

    /**
     * Will permute the boolean done of the task object.
     * @param command This is the user input. The method will check if the index is valid or not.
     */
    private void permuteTask(String[] command) {
        if (checkIndexInCommand(command[1])) {
            Task task = tasks.get(Integer.parseInt(command[1]));
            task.setDone(!task.isDone());
        } else {
            message += "Bamboo couldn't find the index to the task to permute.\n";
        }
    }

    /**
     * Will ask the new task's description.
     * @param command This is the user input. The method will check if the index is valid or not.
     */
    private void modifyTask(String[] command){
        String index = command[1];
        if (checkIndexInCommand(index)){
            if (command.length < 3 || command[2].equals("")) {
                message += "You cannot have a void task.\n";
            } else {
                tasks.get(Integer.parseInt(index)).setDescription(concatenateWordsFromArray(command));
            }
        } else {
            message += "Bamboo couldn't find the index to the task to permute.\n";
        }
    }

    /**
     * Print the help String/
     */
    private void printHelp(){
        System.out.println(
                """
                        add <description> ................. Create a new task.
                        delete <index> .................... Delete a task.
                        modify <index> <description> ...... Will ask for your new description of a task.
                        done/undone <index> ............... Will permute the status of the task.
                        show/hide ......................... Will hide or show the task already done.
                        clear ............................. Will delete every tasks.
                        help .............................. Will show help.
                        exit .............................. Will exit the task shell.
                        """
        );
    }

    /**
     * Will clear the list of tasks.
     */
    private void clear(){
        listFile.saveList(new ArrayList<Task>());
        tasks = listFile.getTasks();
        message += "All tasks have been erased.";
    }

    /**
     * Because the user input is parsed using a String.split(" "),
     * we need to reassemble the sentences inputted by the user.
     * @param command This is the user input.
     * @return the description of the task.
     */
    private String concatenateWordsFromArray(String[] command){
        if (command[0].equals("modify")) {
            command[1] = "";
        }
        command[0] = "";
        StringBuilder bob = new StringBuilder();
        for (String word : command){
            bob.append(" " + word.replace("\"", ""));
        }
        return bob.toString().trim();
    }



}
