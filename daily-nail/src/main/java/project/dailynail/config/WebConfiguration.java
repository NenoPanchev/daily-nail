package project.dailynail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.dailynail.web.interceptors.MaintenanceInterceptor;
import project.dailynail.web.interceptors.StatsInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StatsInterceptor statsInterceptor;
    private final MaintenanceInterceptor maintenanceInterceptor;

    public WebConfiguration(StatsInterceptor statsInterceptor, MaintenanceInterceptor maintenanceInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.maintenanceInterceptor = maintenanceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(maintenanceInterceptor);
    }
}
