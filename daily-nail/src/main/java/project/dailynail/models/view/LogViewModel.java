package project.dailynail.models.view;

import java.time.LocalDateTime;

public class LogViewModel {
    private String requestURI;
    private String method;
    private String user;
    private String time;
    private String message;

    public LogViewModel() {
    }

    public String getRequestURI() {
        return requestURI;
    }

    public LogViewModel setRequestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public LogViewModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUser() {
        return user;
    }

    public LogViewModel setUser(String user) {
        this.user = user;
        return this;
    }

    public String getTime() {
        return time;
    }

    public LogViewModel setTime(String time) {
        this.time = time;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogViewModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
