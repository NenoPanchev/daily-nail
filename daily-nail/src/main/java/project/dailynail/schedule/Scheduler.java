package project.dailynail.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.dailynail.services.AdminService;

import java.io.IOException;

@Component
public class Scheduler {
    private final AdminService adminService;

    public Scheduler(AdminService adminService) {
        this.adminService = adminService;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void scheduledExport() throws IOException {
        adminService.exportData();
    }
}
