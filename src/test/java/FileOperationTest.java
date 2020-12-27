import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileOperationTest {

    @Before
    public void createFile() throws IOException {
        File file = new File("./testFile.txt");
        if (file.exists()) file.delete();

        new File("./testFile.txt").createNewFile();
    }

    @Test
    public void shouldAbleToWriteInFile() throws IOException {
        File file = new File("./testFile.txt");
        FileOperation fileOperation = new FileOperation(file);


        String fileInput = "abcdefegh";
        fileOperation.write(fileInput);

        String actualString = new BufferedReader(new FileReader(file)).readLine();

        assertEquals(fileInput, actualString);
    }
}