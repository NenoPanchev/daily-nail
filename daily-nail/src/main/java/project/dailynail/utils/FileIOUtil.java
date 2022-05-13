package project.dailynail.utils;

import java.io.IOException;

public interface FileIOUtil {
    String readFileContent(String filepath) throws IOException;

    void write(String content, String filePath) throws IOException;
}
