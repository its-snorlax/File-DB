import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperation {
    private final File file;

    public FileOperation(File file) {
        this.file = file;
    }

    public void write(String jsonData) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(jsonData);
        fw.append("\n");
        fw.close();
    }

    public String read() throws IOException {
        StringBuilder content = new StringBuilder();
        FileReader fileReader = new FileReader(file);
        int contentChar;
        while ((contentChar = fileReader.read()) != -1) content.append((char) contentChar);
        fileReader.close();
        return content.toString();
    }
}
