package project.dailynail.global;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import project.dailynail.exceptions.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler({RuntimeException.class})
//    public String handleUncaughtErrors(RuntimeException r) {
//
//        return "redirect:/error";
//    }

    @ExceptionHandler({ObjectNotFoundException.class, NoHandlerFoundException.class})
    public String notFound(Exception e, HttpServletRequest httpServletRequest) {

        return "error-404";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String denied(AccessDeniedException deniedException) {
        System.out.println(deniedException.getMessage());

        return "error-denied";
    }
}
