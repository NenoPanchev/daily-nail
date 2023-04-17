package project.dailynail.handlers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.services.LogService;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final LogService logService;

    public GlobalExceptionHandler(LogService logService) {
        this.logService = logService;
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleUncaughtErrors(Exception e, HttpServletRequest request) {
        System.out.println(e.getMessage());
        logService.createLog(request, e.getMessage());
                        return "redirect:/error";
    }

    @ExceptionHandler({ObjectNotFoundException.class, NoHandlerFoundException.class})
    public String notFound(Exception e, HttpServletRequest request) {
        logService.createLog(request, e.getMessage());
        System.out.println(e.getMessage());
        return "redirect:/404";
    }

    @ExceptionHandler({CustomAccessDeniedHandler.class})
    public String denied(AccessDeniedException e, HttpServletRequest request) {
        System.out.println(e.getMessage());
        logService.createLog(request, e.getMessage());
        return "redirect:/403";
    }

    @ExceptionHandler({AuthenticationException.class})
    public String auth(AuthenticationException e, HttpServletRequest request) {
        System.out.println(e.getMessage());
        logService.createLog(request, e.getMessage());
        return "redirect:/403";
    }
}
