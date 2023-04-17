package project.dailynail.services.impl;

import org.springframework.stereotype.Service;
import project.dailynail.models.entities.LogEntity;
import project.dailynail.repositories.LogRepository;
import project.dailynail.services.LogService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void createLog(HttpServletRequest request, String errorMessage) {
        LogEntity entity = new LogEntity()
                .setMethod(request.getMethod())
                .setRequestURI(request.getRequestURI())
                .setTime(LocalDateTime.now())
                .setUser(request.getRemoteUser())
                .setMessage(errorMessage);
        logRepository.save(entity);
    }
}
