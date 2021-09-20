package tech.note.file;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.note.model.BambooTask;
import tech.note.model.Subtask;
import tech.note.model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ListFile {
    private final String fileName;
    private List<Task> tasks;

    /**
     *
     * @param listName the fileName String variable is built upon the name of list with appending .json .
     * The tasks variable is loaded when using the method loadTask.
     */
    public ListFile(String listName) {
        this.fileName = listName + ".json";
        loadTask();
    }

    /**
     * Will load the list of tasks from the list's file.
     * This method is not used by getting the list of tasks and shall remain private.
     */
    private void loadTask(){
        try {
            FileReader file = new FileReader(Path.LIST_PATH.getPath() + fileName);
            List<Map<String, String>> jsonArray = (List<Map<String, String>>) new JSONParser().parse(file);
            List<Task> tasksReturned = new ArrayList<>();
            if (jsonArray.size() > 0) {
                for (Map<String, String> map : jsonArray){
                    tasksReturned.add(new Task(
                            map.get("tasksId"),
                            this.tasks == null ? 0 : tasks.size(),
                            map.get("description"),
                            map.get("done").equals("true"),
                            parseSubtasksList(map.get("subtasks"))
                    ));
                }
            }
            this.tasks = tasksReturned;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private List<Subtask> parseSubtasksList(String jsonString) throws ParseException {
        List<Map<String, String>> subtasksListInMap = (List<Map<String, String>>) new JSONParser().parse(jsonString.replace("||", "\""));
        List<Subtask> subtasks = new ArrayList<>();
        for (Map<String, String> subtaskMap : subtasksListInMap) {
            subtasks.add(
                    new Subtask(
                            Integer.parseInt(subtaskMap.get("order")),
                            subtaskMap.get("description"),
                            subtaskMap.get("done").equals("true")
                    )
            );
        }
        return subtasks;
    }

    /**
     * Method used by the Task Shell.
     * @return return the list of Tasks from the list's file.
     */
    public List<Task> reload() {
        loadTask();
        return tasks;
    }

    /**
     * Will save the list in its file and reload the tasks from it.
     * @param tasks List of task of a list to be saved.
     */
    public void saveList(List<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(Path.LIST_PATH.getPath() + fileName);
            writer.write(tasksToJsonString(tasks));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadTask();
    }

    /**
     * The tasks are saved in the form of a map.
     * @param tasks List of tasks
     * @return an Array of Map in the form of a String to be saved in the file.
     */
    private String tasksToJsonString(List<Task> tasks) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Task task : tasks){
            stringBuilder
                    .append(tasks.indexOf(task) != 0 ? "," : "")
                    .append(task.toMapString());
        }
        return stringBuilder.append("]").toString();
    }
}
