package tech.note.file;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.note.model.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListFile {
    private String fileName;
    private List<Task> tasks;

    public ListFile() {
    }

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
            this.tasks = new ArrayList<>();
            if (jsonArray.size() > 0) {
                for (Map<String, String> map : jsonArray){
                    this.tasks.add(new Task(
                            this.tasks == null ? 0 : tasks.size(),
                            map.get("description"),
                            map.get("done").equals("true")
                    ));
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }


    /**
     * Method used by the Task Shell.
     * @return return the list of Tasks from the list's file.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Will save the list in its file and reload the tasks from it.
     * @param tasks List of task of a list to be saved.
     */
    public void saveList(List<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(Path.LIST_PATH.getPath() + fileName);
            writer.write(toJsonString(tasks));
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
    private String toJsonString(List<Task> tasks) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Task task : tasks){
            stringBuilder.append(
                    "{\"id\":\"" + task.getId() + "\"" +
                            ",\"description\":\"" + task.getDescription() + "\"" +
                            ",\"done\":\"" + task.isDone() + "\"}"
            );
        }
        return stringBuilder.append("]").toString();

    }
}
