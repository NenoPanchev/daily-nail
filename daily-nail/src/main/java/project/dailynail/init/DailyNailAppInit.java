package project.dailynail.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.dailynail.services.*;

@Component
public class DailyNailAppInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;


    public DailyNailAppInit(UserService userService, UserRoleService userRoleService, SubcategoryService subcategoryService, CategoryService categoryService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) {
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.subcategoryService.seedSubcategories();

    }
}
