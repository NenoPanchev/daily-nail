package project.dailynail.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AdminService {
    void exportData() throws IOException;
    void importData() throws FileNotFoundException;
}
