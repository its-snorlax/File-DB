import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Database {

    private File file;

    public Database(File file) {
        this.file = file;
    }

    public boolean create(String key, String json) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.write(file, "{\"" + key + "\" : " + json + "}");

        String read = fileManager.read(file);
        String[] split = read.split("\n");

        JsonNode forInputKey = null;
        int pos = 0;
        while (forInputKey == null && pos < split.length) {
            JsonNode jsonNode = new ObjectMapper().readTree(split[pos]);
            if (jsonNode.get(key) != null) forInputKey = jsonNode.get(key);
            pos++;
        }
        return forInputKey != null;
    }
}
