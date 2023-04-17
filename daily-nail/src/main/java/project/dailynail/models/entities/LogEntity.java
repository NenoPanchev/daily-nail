package project.dailynail.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity {
    private String requestURI;
    private String method;
    private String user;
    private LocalDateTime time;
    private String message;

    public LogEntity() {
    }

    @Column
    public String getRequestURI() {
        return requestURI;
    }



    @Column
    public String getMethod() {
        return method;
    }


    @Column
    public String getUser() {
        return user;
    }


    @Column
    public LocalDateTime getTime() {
        return time;
    }


    @Column(columnDefinition = "TEXT")
    public String getMessage() {
        return message;
    }

    public LogEntity setRequestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public LogEntity setMethod(String method) {
        this.method = method;
        return this;
    }

    public LogEntity setUser(String user) {
        this.user = user;
        return this;
    }

    public LogEntity setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public LogEntity setMessage(String message) {
        this.message = message;
        return this;
    }

}
