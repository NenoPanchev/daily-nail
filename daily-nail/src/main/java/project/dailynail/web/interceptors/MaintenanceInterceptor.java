package project.dailynail.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    public MaintenanceInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/") || requestURI.contains("/articles") || requestURI.contains("/users")) {
            LocalTime currentTime = LocalTime.now();
            boolean isMaintenanceTime = currentTime.isAfter(LocalTime.of(2, 59, 30)) && currentTime.isBefore(LocalTime.of(3, 1));
            if (isMaintenanceTime) {
                response.sendRedirect("/maintenance");
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
