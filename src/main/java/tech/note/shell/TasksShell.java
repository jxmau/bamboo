package tech.note.shell;

import tech.note.file.ListFile;
import tech.note.tool.MessageBuilder;
import tech.note.tool.MessageBuilderMessage;
import tech.note.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

final public class TasksShell extends BambooShell {
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
    private final MessageBuilder message = new MessageBuilder();
    private List<Task> taskExpanded;





    public TasksShell(String listName, Scanner scan) {
        this.listName = listName;
        this.scan = scan;
        this.listFile = new ListFile(this.listName);
        this.taskExpanded = new ArrayList<>(listFile.reload());
        super.taskExpandedSize = taskExpanded.size();
    }

    protected void actualizeExpandedFields(){
        this.taskExpanded = listFile.reload();
        super.taskExpandedSize = taskExpanded.size();
    }

    public void start(){
        while (!exit) {
            printList();
            message.oneTimePrint();
            System.out.print(" " + listName + " > ");
            String[] command = scan.nextLine().split(" ");
            switch (command[0]){
                case "new" -> addTask(command);
                case "delete" -> deleteTask(command);
                case "done", "undone" -> permuteTask(command);
                case "exp" -> enterSubtaskShell(command);
                case "modify" -> modifyTask(command);
                case "exit" -> exit = true;
                case "hide", "show" -> hide = !hide;
                case "help" -> requestHelp();
                case "clear" -> clear(command);
            }
            listFile.saveList(taskExpanded);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

    }

    /**
     * Method to print the list of tasks. Two StringBuilder are used,
     * separating the done and undone tasks.
     */
    @Override
    protected void printList(){
        StringBuilder tasksDone = new StringBuilder();
        StringBuilder tasksNotDone = new StringBuilder();
            for (Task task : taskExpanded) {
                StringBuilder line = new StringBuilder();
                if (!task.isDone() || (task.isDone() && !hide)) {
                    line.append(formatIndex(taskExpanded.indexOf(task)))
                            .append(" ")
                            .append(task.isDone() ? "[X]" : "[ ]")
                            .append(" - ")
                            .append(task.getDescription())
                            .append("\n");
                    if (task.isDone()) {
                        tasksDone.append(line);
                    } else {
                        tasksNotDone.append(line);
                    }

                }
            }
            // No need to print if hide is false.
            if (tasksNotDone.isEmpty() && hide) {
                System.out.println("You don't have unfinished tasks!");
            }
            System.out.println(tasksDone + " \n" + tasksNotDone);
    }


    /**
     * This method will add a task to the list.
     * @param command is the command line from the user.
     */
    private void addTask(String[] command){
        if (command[1] != null) {
            taskExpanded.add(new Task(taskExpanded.size(), concatenateWordsFromArray(command, 1), false));
            listFile.saveList(taskExpanded);
            actualizeExpandedFields();
        } else {
            message.add(MessageBuilderMessage.NO_INDEX_FOUND.getMessage());
        }
    }



    /**
     * Will delete the task from the list.
     * It will not save them as it done at each while loop iteration.
     * @param command This is the user input. The method will check if the index is valid or not.
     */
    private void deleteTask(String[] command){
        if (checkIndexInCommand(command[1])) {
            taskExpanded.remove(Integer.parseInt(command[1]));
            actualizeExpandedFields();
        } else {
            message.add(MessageBuilderMessage.NO_INDEX_FOUND.getMessage());
        }
    }

    /**
     * Will permute the boolean done of the task object.
     * @param command This is the user input. The method will check if the index is valid or not.
     */
    private void permuteTask(String[] command) {
        if (checkIndexInCommand(command[1])) {
            Task task = taskExpanded.get(Integer.parseInt(command[1]));
            task.setDone(!task.isDone());
        } else {
            message.add(MessageBuilderMessage.NO_INDEX_FOUND.getMessage());
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
                message.add("You cannot have a void task.");
            } else {
                taskExpanded.get(Integer.parseInt(index)).setDescription(concatenateWordsFromArray(command, 2));
            }
        } else {
            message.add(MessageBuilderMessage.NO_INDEX_FOUND.getMessage());
        }
    }

    private void enterSubtaskShell(String[] command) {
        if (command.length == 1) {
            message.add(MessageBuilderMessage.NO_INDEX_FOUND.getMessage());
        } else if (!checkIndexInCommand(command[1])){
            System.out.println("List size : " + taskExpanded.size());
            message.add(MessageBuilderMessage.INCORRECT_INDEX.getMessage());
        } else {
             SubTasksShell subtaskShell = new SubTasksShell(
                    Integer.parseInt(command[1]),
                    taskExpanded,
                    scan,
                    listName
            );
             subtaskShell.start();
            actualizeExpandedFields();
        }
    }
    /**
     * Doesn't print directly the help String, but will add it to the MessageBuilder.
     */
    protected void requestHelp(){
        message.add(
                """
                        add <description> ................. Create a new task.
                        delete <index> .................... Delete a task.
                        modify <index> <description> ...... Will ask for your new description of a task.
                        done/undone <index> ............... Will permute the status of the task.
                        show/hide ......................... Will hide or show the task already done.
                        clear < choice > .................. Will delete tasks (if empty, all - or done/undone).
                        help .............................. Will show help.
                        exit .............................. Will exit the task shell.
                        """
        );
    }

    /**
     * Will clear the list of tasks.
     * case "done" -> Will delete all done tasks.
     * case "undone" -> Will delete all undone tasks.
     * By default, all tasks will be deleted.
     */
    private void clear(String[] command){

        switch (command[1]) {
            case "done" -> {
                taskExpanded.removeIf(Task::isDone);
                message.add("All done tasks were deleted.");
            }
            case "undone" -> {
                taskExpanded.removeIf(Predicate.not(Task::isDone));
                message.add("All undone tasks were deleted.");
            }
            default -> {
                listFile.saveList(new ArrayList<>());
                taskExpanded = new ArrayList<>(listFile.reload());
                message.add("All tasks have been deleted.");
            }
        }
    }




}
