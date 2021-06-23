package project.dailynail.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.dailynail.services.UserRoleService;
import project.dailynail.services.UserService;

@Component
public class DailyNailAppInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public DailyNailAppInit(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
    }
}
