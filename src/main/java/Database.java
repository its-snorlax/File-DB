import com.fasterxml.jackson.databind.ObjectMapper;
import exception.KeyAlreadyExistsException;
import exception.KeyNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Database {

    private final File databaseRegistry;
    private final String DATABASE_FILE_NAME = "Database.txt";
    private String path = null;


    public Database() throws IOException {
        this.databaseRegistry = new File(DATABASE_FILE_NAME);
        databaseRegistry.createNewFile();
    }

    public Database(String path) throws IOException {
        this.path = path + File.separator;
        File folder = new File(this.path);
        folder.mkdirs();
        databaseRegistry = new File(folder, DATABASE_FILE_NAME);
        databaseRegistry.createNewFile();
    }

    public boolean create(String key, String json, int ttlInSeconds) throws Exception {
        if (exist(key)) throw new KeyAlreadyExistsException();

        String keyToBeWritten = new ObjectMapper().writeValueAsString(new Entry(key, ttlInSeconds));
        FileManager fileManager = new FileManager();
        String resourceFileName = concatFileNameWithExtension(key);
        fileManager.write(databaseRegistry, keyToBeWritten, true);
        File resourceFile;
        if (path != null) resourceFile = new File(path + resourceFileName);
        else resourceFile = new File(resourceFileName);
        resourceFile.createNewFile();

        fileManager.write(resourceFile, "{\"" + key + "\" : " + json + "}", false);
        String read = fileManager.read(resourceFile);
        return new ObjectMapper().readTree(read).get(key) != null;
    }

    public String read(String key) throws Exception {
        if (!exist(key)) throw new KeyNotFoundException();
        FileManager fileManager = new FileManager();
        String read;
        if (path == null) read = fileManager.read(new File(concatFileNameWithExtension(key)));
        else read = fileManager.read(new File(concatFileNameWithExtension(path + key)));
        return new ObjectMapper().readTree(read).get(key).toString();
    }

    public void delete(String key) throws Exception {
        if (!exist(key)) throw new KeyNotFoundException();
        deleteEntryAndResourceFile(key);
    }

    private void deleteEntryAndResourceFile(String key) throws IOException {
        FileManager fileManager = new FileManager();
        String read = fileManager.read(databaseRegistry);

        String[] entries = read.split("(?<=\n)");
        String updatedEntries = "";
        for (String entry : entries) {
            Entry currentEntry = new ObjectMapper().readValue(entry, Entry.class);
            if (!currentEntry.getKey().equals(key)) {
                updatedEntries += entry;
            }
        }

        if (path == null) new File(concatFileNameWithExtension(key)).delete();
        else new File(concatFileNameWithExtension(path + key)).delete();
        fileManager.write(databaseRegistry, updatedEntries, false);
    }


    private boolean exist(String key) throws Exception {
        FileManager fileManager = new FileManager();
        String read = fileManager.read(databaseRegistry);
        if (!read.isEmpty()) {
            String[] entries = read.split("\n");
            for (String entry : entries) {
                Entry currentEntry = new ObjectMapper().readValue(entry, Entry.class);
                if (currentEntry.getKey().equals(key)) {
                    if (((currentEntry.getCreatedAt().getTimeInMillis() + currentEntry.getTtl() * 1000L)
                            >= (Calendar.getInstance().getTimeInMillis()))) return true;
                    else {
                        deleteEntryAndResourceFile(key);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private String concatFileNameWithExtension(String fileName) {
        return fileName + ".txt";
    }
}
