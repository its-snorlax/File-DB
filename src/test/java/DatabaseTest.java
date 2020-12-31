import exception.KeyAlreadyExistsException;
import exception.KeyNotFoundException;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DatabaseTest {

    private final String homePath = System.getenv("HOME");

    @After
    public void removeFiles() {
        File dbFile = new File("./Database.txt");
        File key1File = new File("./key1.txt");
        File key2File = new File("./key2.txt");
        File key3File = new File("./key3.txt");

        if (dbFile.exists()) dbFile.delete();
        if (key1File.exists()) key1File.delete();
        if (key2File.exists()) key2File.delete();
        if (key3File.exists()) key3File.delete();
    }

    @After
    public void removeFilesInHomeDirectory() {
        File dbFile = new File(homePath + "/File-DB/Database.txt");
        File key1File = new File(homePath + "/File-DB/key1.txt");
        File key2File = new File(homePath + "/File-DB/key2.txt");
        File key3File = new File(homePath + "/File-DB/key3.txt");
        if (dbFile.exists()) dbFile.delete();
        if (key1File.exists()) key1File.delete();
        if (key2File.exists()) key2File.delete();
        if (key3File.exists()) key3File.delete();
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyInFile() throws Exception {
        assertTrue(new Database()
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}", 60));

        assertTrue(new Database()
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}", 60));
    }

    @Test
    public void shouldInsertJsonCorrespondingToKeyWhenFilePathIsGiven() throws Exception {

        assertTrue(new Database(homePath + "/File-DB")
                .create("key1", "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}", 60));

        assertTrue(new Database(homePath + "/File-DB")
                .create("key2", "{\"name\":\"Prateek\",\"salary\":600000.0,\"age\":20}", 60));

    }

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFile() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data, 60);

        assertEquals(data, database.read("key3"));
    }

    @Test
    public void shouldReadTheExactDataWhichWeStoreInFileWhenFilePathIsGiven() throws Exception {
        Database database = new Database(homePath + "/File-DB");
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data, 60);

        assertEquals(data, database.read("key3"));
    }


    @Test(expected = KeyAlreadyExistsException.class)
    public void shouldRaiseKeyAlreadyExistExceptionIfKeyIsAlreadyExist() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        database.create("key3", data, 60);
        database.create("key3", data, 60);
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
        String key = "key3";
        database.create("key1", data, 60);
        database.create("key2", data, 60);
        database.create(key, data, 60);
        database.delete(key);
        database.read(key);
        assertFalse(new File(key + ".text").exists());
    }

    @Test(expected = KeyNotFoundException.class)
    public void shouldRaiseKeyNotFoundExceptionIfUserTriesToDeleteKeyWhichNeverInserted() throws Exception {
        String path = homePath + "/File-DB";
        Database database = new Database(path);
        String key = "key3";
        database.delete(key);
        assertFalse(new File(path + key + ".text").exists());
    }

    @Test()
    public void shouldDeleteKeyFileAfterDeletingTheKeyEntryFromDatabase() throws Exception {
        String path = homePath + "/File-DB";
        Database database = new Database(path);
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        String key = "key3";
        database.create(key, data, 60);
        database.delete(key);
        assertFalse(new File(path + key + ".text").exists());
    }

    @Test(expected = KeyNotFoundException.class)
    public void shouldDeleteKeyAndFileIfTtlIsExpired() throws Exception {
        Database database = new Database();
        String data = "{\"name\":\"Prayas\",\"salary\":600000.0,\"age\":20}";
        String key = "key3";
        database.create("key1", data, 5);
        database.create(key, data, 5);
        TimeUnit.SECONDS.sleep(5);
        database.read(key);
        assertFalse(new File(key + ".txt").exists());
    }

    @Test(expected = KeyNotFoundException.class)
    public void ShouldRaiseKeyNotFoundExceptionWhenKeyOneShouldBeDeletedAfterExpiryAndThenNewEntryWillBeInserted()
            throws Exception {
        Database database = new Database();

        database.create("key2", "{\"name\":\"Key1\",\"salary\":600000.0,\"age\":20}", 5);
        database.create("key1", "{\"name\":\"Key2\",\"salary\":600000.0,\"age\":20}", 60);
        TimeUnit.SECONDS.sleep(6);
        assertEquals("{\"name\":\"Key2\",\"salary\":600000.0,\"age\":20}", database.read("key1"));
        database.read("key2");
    }
}