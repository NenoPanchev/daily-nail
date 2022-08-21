package project.dailynail.handlers;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.handlers.CustomAccessDeniedHandler;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public String handleUncaughtErrors(Exception e) {
        System.out.println(e.getMessage());
                        return "redirect:/error";
    }

    @ExceptionHandler({ObjectNotFoundException.class, NoHandlerFoundException.class})
    public String notFound(Exception e, HttpServletRequest httpServletRequest) {
        System.out.println(e.getMessage());
        return "404";
    }

    @ExceptionHandler({CustomAccessDeniedHandler.class})
    public String denied(AccessDeniedException deniedException) {
        System.out.println(deniedException.getMessage());

        return "redirect:/403";
    }

    @ExceptionHandler({AuthenticationException.class})
    public String auth(AuthenticationException authenticationException) {
        System.out.println(authenticationException.getMessage());

        return "redirect:/403";
    }
}
