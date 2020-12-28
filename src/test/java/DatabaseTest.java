import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class DatabaseTest {

    @Before
    public void createFile() throws IOException {
        File file = new File("./Database.txt");
        if (file.exists()) file.delete();
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyInFile() throws IOException {
        assertTrue(new Database()
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}"));

        assertTrue(new Database()
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}"));
    }


    @Test
    public void shouldInsertJsonCorrespondingToKeyWhenFilePathIsGiven() throws IOException {
        assertTrue(new Database("/home/prayas/File-DB")
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}"));

        assertTrue(new Database("/home/prayas/File-DB")
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}"));

    }
}