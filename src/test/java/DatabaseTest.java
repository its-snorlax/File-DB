import exception.KeyAlreadyExistsException;
import exception.KeyNotFoundException;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DatabaseTest {

    @After
    public void createFile() {
        File dbFile = new File("./Database.txt");
        File key1File = new File("./key1.txt");
        File key2File = new File("./key2.txt");
        File key3File = new File("./key3.txt");

        if (dbFile.exists()) dbFile.delete();
        if (key1File.exists()) key1File.delete();
        if (key2File.exists()) key2File.delete();
        if (key3File.exists()) key3File.delete();
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyInFile() throws Exception {
        assertTrue(new Database()
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}"));

        assertTrue(new Database()
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}"));
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyWhenFilePathIsGiven() throws Exception {
        assertTrue(new Database("/home/prayas/File-DB")
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}"));

        assertTrue(new Database("/home/prayas/File-DB")
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}"));

    }

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFile() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data);

        assertEquals(data, database.read("key3"));
    }

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFileWhenFilePathIsGiven() throws Exception {
        Database database = new Database("/home/prayas/File-DB");
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data);

        assertEquals(data, database.read("key3"));
    }


    @Test(expected = KeyAlreadyExistsException.class)
    public void shouldRaiseKeyAlreadyExistExceptionIfKeyIsAlreadyExist() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data);
        database.create("key3", data);
    }

    @Test(expected = KeyNotFoundException.class)
    public void shouldRaiseKeyNotFoundExceptionWhenYouTryToGetAKeyWhichIsNeverInserted() throws Exception {
        Database database = new Database();
        database.read("key1");
    }

    @Test(expected = KeyNotFoundException.class)
    public void shouldRaiseKeyNotFoundExceptionOnceTheKeyIsDeleted() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        String key = "key4";
        database.create("key1", data);
        database.create("key2", data);
        database.create(key, data);
        database.delete(key);
        database.read(key);
        assertFalse(new File(key + ".text").exists());
    }

    @Test(expected = KeyNotFoundException.class)
    public void shouldRaiseKeyNotFoundExceptionOnceTheKeyIsDeletedWhenDBPathIsGiven() throws Exception {
        String path = "/home/prayas/File-DB";
        Database database = new Database(path);
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        String key = "key4";
        database.create(key, data);
        database.delete(key);
        database.read(key);
        assertFalse(new File(path + key + ".text").exists());
    }
}