package tech.note.shell;

import tech.note.file.ListFile;
import tech.note.model.BambooTask;
import tech.note.tool.MessageBuilder;
import tech.note.model.Task;

import java.util.List;
import java.util.regex.Pattern;

public abstract class BambooShell {
    protected final MessageBuilder message = new MessageBuilder();
    protected int taskExpandedSize;
    protected ListFile listFile;
    protected boolean exit = false;




    /**
     *
     * @param index This is the index of the Task in the list of Tasks
     * @return the index to task with a margin.
     */
    protected String formatIndex(int index){
        StringBuilder whiteSpace = new StringBuilder();
        whiteSpace.append(index);
        while (whiteSpace.length() != Integer.toString(taskExpandedSize).length() + 1) {
            whiteSpace.append(" ");
        }
        return whiteSpace.toString();
    }

    /**
     * As the index will always be the second element
     * @param command This is the user input.
     * @return true if the index is valid.
     */
    protected boolean checkIndexValidity(String[] command){
        if (command.length == 1) {
            message.add("You need to write the selected subtask's index.");
            return false;
        } else if (!checkIndexInCommand(command[1])){
            message.add("The index is not valid.");
            return  false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param index as the field split that is supposed to be the index
     * @return true if index a numeric String.
     */
    protected boolean checkIndexInCommand(String index){
        return Pattern.compile("\\d+").matcher(index).matches() && Integer.parseInt(index) < taskExpandedSize;
    }

    /**
     * Because the user input is parsed using a String.split(" "),
     * we need to reassemble the sentences inputted by the user.
     * @param command This is the user input.
     * @param startIndex This is the index at which the method will start to concatenate.
     *                   1 for add command | 2 for a modify method.
     * @return the description of the task.
     */
    public String concatenateWordsFromArray(String[] command, int startIndex){
        StringBuilder bob = new StringBuilder();
        for (int i = startIndex; i < command.length; i++){
                bob.append(" ").append(command[i].replace("\"", ""));
        }
        return bob.toString().trim();
    }


    /**
     * Will add the help String variable to the Message Builder.
     */
    abstract void requestHelp();

    /**
     * Print the list of tasksExpanded and can be used at the super level.
     */
    abstract void printList();

    /**
     * Super method to call when modifying the Collections object of the expanded BambooTask children object.
     */
    abstract void actualizeExpandedFields();


}
