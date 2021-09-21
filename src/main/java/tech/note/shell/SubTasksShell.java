package tech.note.shell;

import tech.note.file.ListFile;
import tech.note.model.BambooTask;
import tech.note.model.Subtask;
import tech.note.tool.MessageBuilderMessage;
import tech.note.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


/**
 * Will ask for the index of the task that has the subtasks,
 * Will ask for all the tasks
 * Will extract the task from the list each time and return the list to the ListFile to save the overall list of tasks.
 */
final public class SubTasksShell extends BambooShell{
    private final Integer index;
    private final Scanner scanner;
    private final List<Task> tasks;
    private List<Subtask> taskExpanded;

    /**
     * Constructor Method for the Task Shell.
     * @param index Index of the task expanded in the List of Task.
     * @param tasks List of Task.
     * @param scanner Scanner.
     * @param listName Name of the List for the ListFile to find the list file's name.
     */
    public SubTasksShell(int index, List<Task> tasks, Scanner scanner, String listName) {
        this.index = index;
        this.tasks = tasks;
        this.scanner = scanner;
        this.taskExpanded = new ArrayList<>(tasks.get(index).getSubtasks());
        super.taskExpandedSize = taskExpanded.size();
        super.listFile = new ListFile(listName);
    }

    protected void actualizeExpandedFields(){
        super.taskExpandedSize = taskExpanded.size();
    }

    /**
     * Start the shell.
     */
    public void start() {
        while (!exit){
            printList();
            message.oneTimePrint();
            System.out.print(" SubTask Shell > ");
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]){
                case "new" -> addSubtask(command);
                case "modify" -> modify(command);
                case "delete" -> delete(command);
                case "done", "undone" -> permute(command);
                case "close" -> close();
                case "exit" -> exit = true;
                case "multi" -> multiShell();
                case "help" -> requestHelp();

            }
            saveList();
        }
    }

    /**
     * Will print the Task with the ratio of done subtasks and the list of the subtasks.
     */
    @Override
    protected void printList() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        StringBuilder bob = new StringBuilder();
        Task task = tasks.get(index);
        if (taskExpanded.isEmpty()) {
            message.add(MessageBuilderMessage.NO_SUBTASKS_SAVED.getMessage());
        } else {
            List<Subtask> subtasks = new ArrayList<>();
            for (Object subtask: taskExpanded){
                subtasks.add((Subtask) subtask);
            }

            bob.append("[").append(subtasks.stream().filter(Subtask::isDone).count()).append("/").append(taskExpanded.size()).append("]")
                    .append(" - ").append(task.getDescription()).append("\n");
            for (Object subtaskInList : taskExpanded) {
                Subtask subtask = (Subtask) subtaskInList;
                bob.append("\n   ").append(formatIndex(taskExpanded.indexOf(subtask))).append(" ")
                        .append(subtask.isDone() ? "[X]" : "[ ]")
                        .append(" - ").append(subtask.getDescription());
            }
        }
        System.out.println(bob);
    }

    /**
     * Will add a subtask to the task.
     * @param command This is the user input.
     */
    private void addSubtask(String[] command) {
        if (command.length == 1) {
            message.add("You cannot add an empty subtask.");
        } else {

            taskExpanded.add(
                    new Subtask (
                            taskExpanded != null ? 0 : taskExpanded.size(),
                            concatenateWordsFromArray(command, 1),
                            false
                    ) {
                    }

            );
            actualizeExpandedFields();
        }
    }

    /**
     * Will ask for the index displayed and modify the subtask's description.
     * @param command This is the user input.
     */
    private void modify(String[] command) {
        if (command.length == 2) {
            message.add(MessageBuilderMessage.NO_DESCRIPTION_FOUND.getMessage());
        } else if (checkIndexValidity(command)) {
            ((Subtask) taskExpanded.get(Integer.parseInt(command[1]))).setDescription(concatenateWordsFromArray(command, 2));
        }
    }

    /**
     * Will ask for the index to delete the subtask.
     * @param command This is user input.
     */
    private void delete(String[] command){
        if (checkIndexValidity(command)) {
            taskExpanded.remove(Integer.parseInt(command[1]));
            actualizeExpandedFields();
        }
    }

    /**
     * Will ask for the index of the subtask for its status to be permuted.
     * Will show the message indicating it was the last undone subtask.
     * @param command This is user input.
     */
    private void permute(String[] command){
        if (checkIndexValidity(command)) {
            int i = Integer.parseInt(command[1]);
            ((Subtask) taskExpanded.get(i)).setDone(!((Subtask)taskExpanded.get(i)).isDone());
            int doneCount = 0;
            for (Object subtask : taskExpanded){
                if (((Subtask) subtask).isDone()) { doneCount++;}
            }
            if (taskExpanded.size() == doneCount) {
                message.add("This was your last subtask! Type close or exit to exist the subtask shell.");
            }
        }
    }

    /**
     * Will set all the subtasks and the task as done, closing the subtask shell.
     */
    private void close(){
        for (Object subtask : taskExpanded){
            ((Subtask) subtask).setDone(true);
        }
        tasks.get(index).setDone(true);
        exit = true;
    }

    /**
     * Will save the whole list of Task by extracting the Task,
     * replacing its subtasks, reinserting in the List of task
     * and giving it to the ListFile Object.
     */
    private void saveList(){
        Task task = tasks.get(index);
        List<Subtask> subtasks = new ArrayList<>();
        for (Object subtask : taskExpanded){
            subtasks.add((Subtask) subtask);
        }
        task.setSubtasks(subtasks);
        tasks.set(index, task);
        listFile.saveList(tasks);
    }

    /**
     * Allows the user to enter several Subtasks without entering the new keyword
     */
    //TODO change the name of this method
    private void multiShell(){
        boolean exitMulti = false;
        message.add("When done, write exit.");
        while (!exitMulti){
            printList();
            message.oneTimePrint();
            String description = scanner.nextLine();
            if (description.equals("exit")){
                exitMulti = true;
            } else if (description.equals("help")) {
              message.add("To exit the Multi, write exit.");
            } else {
                taskExpanded.add(new Subtask(taskExpandedSize == 0 ? 0 : taskExpandedSize, description, false));
            }
        }
    }

    /**
     * Add the Help information to the MessageBuilder.
     */
    @Override
    protected void requestHelp() {
        message.add("\n\n\n\n\n\n\n\n\n\n\n\n");
        message.add("""
                new < description > .................... Will add a new subtask to the task.
                multi .................................. Will allow you to add multiple subtask without the need to write the new keyword.
                modify < index > < new description > ... Will modify the description of the selected subtask.
                delete < index > ....................... Will delete the subtask.
                done/undone < index > .................. Will permute the status of the subtask.
                close .................................. Will mark all Subtasks and the Task as done, and will exit the Subtask Shell.
                exit ................................... Will return to the shell of the Tasks.
                help ................................... Will print the help. Hello, there!
                """);
    }

}
