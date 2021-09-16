package tech.note.shell;

import tech.note.file.ListFile;
import tech.note.model.MessageBuilderMessage;
import tech.note.model.Task;

import java.util.List;
import java.util.Scanner;


/**
 * Will ask for the index of the task that has the subtasks,
 * Will ask for all the tasks
 * Will extract the task from the list each time and return the list to the ListFile to save the overall list of tasks.
 */
public class SubTasksShell extends BambooShell{
    private final Integer index;
    private final Scanner scanner;
    private final List<Task> tasks;

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
        super.taskExpanded = tasks.get(index).getSubtasks();
        super.listFile = new ListFile(listName);
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
                case "help" -> requestHelp();

            }
            saveList();
        }
    }

    /**
     * Will print the Task with the ratio of done subtasks and the list of the subtasks.
     */
    private void printList() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        StringBuilder bob = new StringBuilder();
        Task task = tasks.get(index);
        if (taskExpanded.isEmpty()) {
            message.add(MessageBuilderMessage.NO_SUBTASKS_SAVED.getMessage());
        } else {
            bob.append("[").append(taskExpanded.stream().filter(Task::isDone).count()).append("/").append(taskExpanded.size()).append("]")
                    .append(" - ").append(task.getDescription()).append("\n");
            for (Task subtask : taskExpanded) {
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
                    new Task(
                            taskExpanded != null ? 0 : taskExpanded.size(),
                            concatenateWordsFromArray(command, 1),
                            false
                    )
            );
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
            taskExpanded.get(Integer.parseInt(command[1])).setDescription(concatenateWordsFromArray(command, 2));
        }
    }

    /**
     * Will ask for the index to delete the subtask.
     * @param command This is user input.
     */
    private void delete(String[] command){
        if (checkIndexValidity(command)) {
            taskExpanded.remove(Integer.parseInt(command[1]));
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
            taskExpanded.get(i).setDone(!taskExpanded.get(i).isDone());
            int doneCount = 0;
            for (Task subtask : taskExpanded){
                if (subtask.isDone()) { doneCount++;}
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
        for (Task subtask : taskExpanded){
            subtask.setDone(true);
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
        task.setSubtasks(taskExpanded);
        tasks.set(index, task);
        listFile.saveList(tasks);
    }

    /**
     * Add the Help information to the MessageBuilder.
     */
    @Override
    protected void requestHelp() {
        message.add("""
                new < description > .................... Will add a new subtask to the task.
                modify < index > < new description > ... Will modify the description of the selected subtask.
                delete < index > ....................... Will delete the subtask.
                done/undone < index > .................. Will permute the status of the subtask.
                close .................................. Will mark all Subtasks and the Task as done, and will exit the Subtask Shell.
                exit ................................... Will return to the shell of the Tasks.
                help ................................... Will print the help. Hello, there!
                """);
    }

}
