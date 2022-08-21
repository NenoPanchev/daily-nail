package project.dailynail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyNailApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyNailApplication.class, args);
    }

}
