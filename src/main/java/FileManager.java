import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public void write(File file, String data, boolean append) throws IOException {
        FileWriter fw = new FileWriter(file, append);
        fw.write(data);
        fw.append("\n");
        fw.close();
    }

    public String read(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        FileReader fileReader = new FileReader(file);
        int contentChar;
        while ((contentChar = fileReader.read()) != -1) content.append((char) contentChar);
        fileReader.close();
        return content.toString();
    }
}
