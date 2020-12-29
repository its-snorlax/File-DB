import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

    @After
    public void createFile() {
        File dbFile = new File("./Database.txt");
        File key1File = new File("./key1.txt");
        File key2File = new File("./key2.txt");
        File key3File = new File("./key3.txt");

        if (dbFile.exists()) dbFile.delete();
        if (key1File.exists()) dbFile.delete();
        if (key2File.exists()) dbFile.delete();
        if (key3File.exists()) dbFile.delete();
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

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFile() throws IOException {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data);

        assertEquals(data, database.read("key3"));
    }

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFileWhenFilePathIsGiven() throws IOException {
        Database database = new Database("/home/prayas/File-DB");
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data);

        assertEquals(data, database.read("key3"));
    }
}