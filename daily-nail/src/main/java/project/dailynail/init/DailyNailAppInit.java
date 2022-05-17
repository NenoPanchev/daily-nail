package project.dailynail.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.dailynail.services.*;

import java.io.FileNotFoundException;

@Component
public class DailyNailAppInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final CommentService commentService;


    public DailyNailAppInit(UserService userService, UserRoleService userRoleService, SubcategoryService subcategoryService, CategoryService categoryService, ArticleService articleService, CommentService commentService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws FileNotFoundException {
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.subcategoryService.seedSubcategories();
        if (!articleService.hasArticles()) {
            this.articleService.seedArticles();
            this.commentService.seedComments();
        }
            }
}
