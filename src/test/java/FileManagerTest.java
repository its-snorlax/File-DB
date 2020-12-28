import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    private File file;

    @Before
    public void createFile() throws IOException {
        file = new File("./testFile.txt");
        if (file.exists()) file.delete();

        new File("./testFile.txt").createNewFile();
    }

    @Test
    public void shouldAbleToWriteInFile() throws IOException {
        FileManager fileManager = new FileManager();
        String fileInput = "abcdefegh";
        fileManager.write(file,fileInput);

        String actualString = new BufferedReader(new FileReader(file)).readLine();
        assertEquals(fileInput, actualString);
    }

    @Test
    public void shouldAbleToReadFromFile() throws IOException {
        File file = new File("./testFile.txt");
        FileManager fileManager = new FileManager();
        String input = "abcd efegh";
        fileManager.write(file, input);

        String actualOutput = fileManager.read(file).replaceAll("\n","");

        assertEquals(input, actualOutput);
    }
}