import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ExceptionMessage;
import exception.KeyAlreadyExistsException;
import exception.KeyNotFoundException;

import java.io.File;
import java.io.IOException;

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

    public boolean create(String key, String json) throws Exception {
        if (searchFor(key))
            throw new KeyAlreadyExistsException(ExceptionMessage.KEY_ALREADY_EXIST_EXCEPTION);

        FileManager fileManager = new FileManager();
        String resourceFileName = key + ".txt";
        fileManager.write(databaseRegistry, key + ":" + resourceFileName, true);
        File resourceFile = createResourceFile(resourceFileName);

        fileManager.write(resourceFile, "{\"" + key + "\" : " + json + "}", false);
        String read = fileManager.read(resourceFile);
        return new ObjectMapper().readTree(read).get(key) != null;
    }

    public String read(String key) throws Exception {
        if (!searchFor(key)) throw new KeyNotFoundException(ExceptionMessage.KEY_NOT_FOUND_EXCEPTION);
        FileManager fileManager = new FileManager();
        String read;
        if (path == null) read = fileManager.read(new File(key + ".txt"));
        else read = fileManager.read(new File(path + key + ".txt"));
        return new ObjectMapper().readTree(read).get(key).toString();
    }

    private File createResourceFile(String resourceFileName) throws IOException {
        File resourceFile;
        if (path != null) {
            resourceFile = new File(path + resourceFileName);
            resourceFile.createNewFile();
            return resourceFile;
        }
        return new File(resourceFileName);
    }

    private boolean searchFor(String key) throws IOException {
        FileManager fileManager = new FileManager();
        String read = fileManager.read(databaseRegistry);
        String[] entries = read.split("\n");
        for (String entry : entries) {
            String[] split = entry.split(":");
            if (split[0].equals(key)) return true;
        }
        return false;
    }
}
