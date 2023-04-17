package project.dailynail.services;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
    void createLog(HttpServletRequest request, String errorMessage);
}
