import org.junit.Assert;
import org.junit.Test;
import tech.note.file.FileController;
import tech.note.file.Path;

import java.io.File;

public class FileControllerTest {

    @Test
    public void creationDeletionListFileTest(){
        String listName = "xxxTest";
        File file = new File(Path.LIST_PATH.getPath() + listName + ".json");

        FileController.createListFile(listName);
        Assert.assertTrue(file.exists());

        FileController.deleteListFile(listName);
        Assert.assertFalse(file.exists());
    }
}
