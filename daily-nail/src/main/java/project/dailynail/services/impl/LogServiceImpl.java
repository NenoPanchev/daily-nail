package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.models.entities.LogEntity;
import project.dailynail.models.view.LogViewModel;
import project.dailynail.repositories.LogRepository;
import project.dailynail.services.LogService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<LogViewModel> getLogs() {
        return logRepository
                .findAllOrderByTimeDesc()
                .stream()
                .map(entity -> {
                    LogViewModel view = modelMapper.map(entity, LogViewModel.class);
                    view.setTime(getTimeAsStringForView(entity.getTime()));
                    return view;
                } )
                .collect(Collectors.toList());
    }

    private String getTimeAsStringForView(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm");
        return localDateTime.format(formatter);
    }
}
