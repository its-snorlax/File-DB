import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class DatabaseTest {

    @Before
    public void createFile() throws IOException {
        File file = new File("./testFile.txt");
        if (file.exists()) file.delete();

        new File("./testFile.txt").createNewFile();
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyInFile() throws IOException {
        assertTrue(new Database(new File("./testFile.txt"))
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}"));

        assertTrue(new Database(new File("./testFile.txt"))
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}"));
    }
}