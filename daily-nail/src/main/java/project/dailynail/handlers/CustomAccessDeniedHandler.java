package project.dailynail.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import project.dailynail.services.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler extends Throwable implements AccessDeniedHandler {
    private final LogService logService;
    private static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    public CustomAccessDeniedHandler(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null) {
            LOG.info("User '" + authentication.getName()
            + "' attempted to access the URL: "
            + request.getRequestURI());
        }
        logService.createLog(request, e.getMessage());
        httpServletResponse.sendRedirect(request.getContextPath()+ "/access-denied");
    }
}
