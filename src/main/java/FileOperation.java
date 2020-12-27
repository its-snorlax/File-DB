import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class FileOperation {
    private final File file;

    public FileOperation(File file) {
        this.file = file;
    }

    public void write(String jsonData) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(jsonData);
        fw.close();
    }
}
